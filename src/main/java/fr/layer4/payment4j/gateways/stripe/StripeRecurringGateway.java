package fr.layer4.payment4j.gateways.stripe;

import org.joda.money.Money;

import com.stripe.model.Subscription;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Schedule;
import fr.layer4.payment4j.gateways.AbstractRecurringGateway;

public class StripeRecurringGateway extends AbstractRecurringGateway {

	private String apiKey;

	protected StripeRecurringGateway(Gateway gateway, String apiKey) {
		super(gateway);
		this.apiKey = apiKey;
	}

	@Override
	protected void doRecurring(Money money, CreditCard creditCard,
			Schedule schedule) {

		Subscription subscription = new Subscription();
	}
}
