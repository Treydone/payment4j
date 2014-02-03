package fr.layer4.payment4j;

import org.joda.money.Money;

public interface RecurringGateway {

	String recurring(Money money, CreditCard creditCard, Schedule schedule);
	
	void cancel(String recurringReference);
}
