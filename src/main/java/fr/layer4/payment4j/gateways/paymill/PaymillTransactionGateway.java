package fr.layer4.payment4j.gateways.paymill;

import java.math.BigDecimal;

import org.joda.money.Money;

import com.paymill.context.PaymillContext;
import com.paymill.models.Payment;
import com.paymill.models.Transaction;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class PaymillTransactionGateway extends AbstractTransactionGateway {

	private String apiKey;

	public PaymillTransactionGateway(Gateway gateway, String apiKey) {
		super(gateway);
		this.apiKey = apiKey;
	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress) {
		return null;
	}

	@Override
	protected Result doCapture(Authorization authorization) {
		PaymillContext paymillContext = new PaymillContext(apiKey);
		Transaction transaction = (Transaction) authorization
				.getUnderlyingAuthorization();
		paymillContext.getTransactionService().createWithPreauthorization(
				transaction.getPreauthorization().getId(),
				Integer.valueOf(transaction.getPreauthorization().getAmount()),
				transaction.getPreauthorization().getCurrency());
		Result result = new Result();
		result.setSuccess(true);
		return result;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {
		PaymillContext paymillContext = new PaymillContext(apiKey);
		Payment payment = PaymillUtils.convertCreditCard(creditcard);
		Transaction transaction = paymillContext.getPreauthorizationService()
				.createWithPayment(
						payment,
						money.getAmount().multiply(BigDecimal.valueOf(100))
								.intValue(),
						money.getCurrencyUnit().getCurrencyCode());

		Authorization authorization = new Authorization();
		authorization.setTransactionId(transaction.getId());
		authorization.setUnderlyingAuthorization(transaction);
		return authorization;
	}

	@Override
	protected Result doCancel(String transactionId) {
		PaymillContext paymillContext = new PaymillContext(apiKey);
		Transaction transaction = paymillContext.getTransactionService().get(
				transactionId);
		paymillContext.getRefundService().refundTransaction(transactionId,
				transaction.getAmount());
		Result result = new Result();
		result.setSuccess(true);
		return result;
	}

	@Override
	protected Result doRefund(Money money, String transactionId) {
		PaymillContext paymillContext = new PaymillContext(apiKey);
		paymillContext.getRefundService().refundTransaction(transactionId,
				money.getAmount().multiply(BigDecimal.valueOf(100)).intValue());
		Result result = new Result();
		result.setSuccess(true);
		return result;
	}
}
