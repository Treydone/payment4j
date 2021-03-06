package fr.layer4.payment4j.gateways.webpay;

import java.math.BigDecimal;
import java.util.Map;

import jp.webpay.api.WebPayClient;
import jp.webpay.model.Charge;
import jp.webpay.request.CardRequest;
import jp.webpay.request.ChargeRequest;

import org.apache.commons.lang.NotImplementedException;
import org.joda.money.Money;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class WebPayTransactionGateway extends AbstractTransactionGateway {

	private String apiKey;

	public WebPayTransactionGateway(Gateway gateway, String apiKey) {
		super(gateway);
		this.apiKey = apiKey;
	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress, Map<String, Object> options) {
		throw new NotImplementedException();
	}

	@Override
	protected Result doCapture(Authorization authorization,
			Map<String, Object> options) {
		Result result = new Result();
		WebPayClient client = new WebPayClient(apiKey);
		Charge capture = client.charges.capture(authorization
				.getTransactionId());
		result.setMessage(capture.getFailureMessage());
		result.setSuccess(true);
		return result;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order, Map<String, Object> options) {
		CardRequest card = new CardRequest().number(creditcard.getNumber())
				.expMonth(creditcard.getMonth()).expYear(creditcard.getYear())
				.cvc(Integer.valueOf(creditcard.getVerificationValue()))
				.name(creditcard.getFullName());

		ChargeRequest request = new ChargeRequest()
				.amount(money.getAmount().multiply(new BigDecimal(100))
						.longValue())
				.currency(money.getCurrencyUnit().getCode().toLowerCase())
				.card(card).capture(false);

		WebPayClient client = new WebPayClient(apiKey);
		Charge charge = client.charges.create(request);

		Authorization authorization = new Authorization();
		authorization.setTransactionId(charge.getId());
		authorization.setUnderlyingAuthorization(charge);
		return authorization;
	}

	@Override
	protected Result doCancel(String transactionId, Map<String, Object> options) {
		Result result = new Result();
		WebPayClient client = new WebPayClient(apiKey);
		client.charges.retrieve(transactionId).refund();
		result.setSuccess(true);
		return result;
	}

	@Override
	protected Result doRefund(Money money, String transactionId,
			Map<String, Object> options) {
		Result result = new Result();
		WebPayClient client = new WebPayClient(apiKey);
		client.charges.refund(transactionId,
				money.getAmount().multiply(new BigDecimal(100)).longValue());
		result.setSuccess(true);
		return result;
	}
}
