package fr.layer4.payment4j;

import java.util.Map;

import org.joda.money.Money;

public interface RecurringGateway {

	Result recurring(Money money, CreditCard creditCard, Schedule schedule);

	Result recurring(Money money, CreditCard creditCard, Schedule schedule,
			Map<String, Object> options);

	Result cancel(String recurringReference);

	Result cancel(String recurringReference, Map<String, Object> options);
}
