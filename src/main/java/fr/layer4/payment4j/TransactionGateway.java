package fr.layer4.payment4j;

import org.joda.money.Money;

public interface TransactionGateway {

	Result credit(Money money, CreditCard creditcard);

	Result credit(Money money, CreditCard creditcard, Address billingAddress);

	Result purchase(Money money, CreditCard creditcard, Order order);

	Result purchase(Money money, CreditCard creditcard);

	Authorization authorize(Money money, CreditCard creditcard);

	Result capture(Authorization authorization);

	Result cancel(String transactionId);

	Result refund(Money money, String transactionId);
}
