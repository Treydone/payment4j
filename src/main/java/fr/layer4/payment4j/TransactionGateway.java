package fr.layer4.payment4j;

import java.util.Map;

import org.joda.money.Money;

/**
 * Interface that all <code>TransactionGateway</code>'s must implement. Defines
 * basic transaction operations:purchase, authorize, capture, cancel and credit
 */
public interface TransactionGateway {

	Result credit(Money money, CreditCard creditcard);

	Result credit(Money money, CreditCard creditcard, Address billingAddress);

	Result credit(Money money, CreditCard creditcard, Address billingAddress,
			Map<String, Object> options);

	Result purchase(Money money, CreditCard creditcard);

	Result purchase(Money money, CreditCard creditcard, Order order);

	Result purchase(Money money, CreditCard creditcard, Order order,
			Map<String, Object> options);

	Authorization authorize(Money money, CreditCard creditcard);

	Authorization authorize(Money money, CreditCard creditcard, Order order);

	Authorization authorize(Money money, CreditCard creditcard, Order order,
			Map<String, Object> options);

	Result capture(Authorization authorization);

	Result capture(Authorization authorization, Map<String, Object> options);

	Result cancel(String transactionId);

	Result cancel(String transactionId, Map<String, Object> options);

	Result refund(Money money, String transactionId);

	Result refund(Money money, String transactionId, Map<String, Object> options);
}
