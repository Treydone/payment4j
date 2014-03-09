package fr.layer4.payment4j.gateways.cybersource;

import java.util.Map;

import org.joda.money.Money;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.Schedule;
import fr.layer4.payment4j.gateways.AbstractRecurringGateway;

public class CybersourceGatewayRecurringGateway extends
		AbstractRecurringGateway {

	private String username;

	private String transactionKey;

	public CybersourceGatewayRecurringGateway(Gateway gateway, String username,
			String transactionKey) {
		super(gateway);
		this.username = username;
		this.transactionKey = transactionKey;
	}

	@Override
	protected Result doRecurring(Money money, CreditCard creditCard,
			Schedule schedule, Map<String, Object> options) {
		return null;
	}

	@Override
	protected Result doCancel(String recurringReference,
			Map<String, Object> options) {
		return null;
	}
}
