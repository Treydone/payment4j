package fr.layer4.payment4j.gateways.paymill;

import org.iban4j.Iban;
import org.joda.money.Money;

import com.paymill.context.PaymillContext;
import com.paymill.models.Payment;

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
	protected Result doCredit(Money money, Iban iban) {
		return null;
	}

	@Override
	protected Result doPurchase(Money money, Iban iban) {
		PaymillContext paymillContext = new PaymillContext(apiKey);

		// paymillContext.getClientService().tPaymentService()SubscriptionService().PreauthorizationService().TransactionService().

		// Payment payment =
		// paymillContext.getPaymentService().createWithToken("");
		// payment.
		return null;
	}
}
