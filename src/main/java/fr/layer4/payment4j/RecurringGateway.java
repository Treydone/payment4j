package fr.layer4.payment4j;

import org.joda.money.Money;

public interface RecurringGateway {

	Result recurring(Money money, CreditCard creditCard, Schedule schedule);

	Result cancel(String recurringReference);
}
