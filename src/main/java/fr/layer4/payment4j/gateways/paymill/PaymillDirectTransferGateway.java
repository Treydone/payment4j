package fr.layer4.payment4j.gateways.paymill;

import java.math.BigDecimal;
import java.util.Map;

import org.iban4j.Iban;
import org.joda.money.Money;

import com.paymill.context.PaymillContext;
import com.paymill.models.Payment;
import com.paymill.models.Payment.Type;

import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractDirectTransferGateway;

public class PaymillDirectTransferGateway extends AbstractDirectTransferGateway {

	private String apiKey;

	public PaymillDirectTransferGateway(Gateway gateway, String apiKey) {
		super(gateway);
		this.apiKey = apiKey;
	}

	@Override
	protected Result doCredit(Money money, Iban iban, Map<String, Object> options) {
		return null;
	}

	@Override
	protected Result doPurchase(Money money, Iban iban, Map<String, Object> options) {
		PaymillContext paymillContext = new PaymillContext(apiKey);
		Payment payment = new Payment();
		payment.setType(Type.DEBIT);
		paymillContext.getTransactionService().createWithPayment(payment,
				money.getAmount().multiply(BigDecimal.valueOf(100)).intValue(),
				money.getCurrencyUnit().getCurrencyCode());
		Result result = new Result();
		result.setSuccess(true);
		return result;
	}
}
