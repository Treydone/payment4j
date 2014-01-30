package fr.layer4.payment4j;

import org.joda.money.Money;

public interface RecurringGateway {

	void recurring(Money money, CreditCard creditCard, Schedule schedule);
}
