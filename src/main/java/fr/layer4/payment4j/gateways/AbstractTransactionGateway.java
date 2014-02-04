package fr.layer4.payment4j.gateways;

import org.joda.money.Money;

import com.google.common.base.Preconditions;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.TransactionGateway;

public abstract class AbstractTransactionGateway implements TransactionGateway {

	protected Gateway gateway;

	public AbstractTransactionGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	public Result credit(Money money, CreditCard creditcard,
			Address billingAddress) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The credit card can not be null",
				creditcard);
		Result result = null;
		try {
			result = doCredit(money, creditcard, billingAddress);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				creditcard, null);
		return result;
	}

	public Result credit(Money money, CreditCard creditcard) {
		return credit(money, creditcard, null);
	}

	public abstract Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress);

	public Result purchase(Money money, CreditCard creditcard) {
		return purchase(money, creditcard, null);
	}

	public Result purchase(Money money, CreditCard creditcard, Order order) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The credit card can not be null",
				creditcard);
		Result result = null;
		try {
			result = doPurchase(money, creditcard, order);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				creditcard, null);
		return result;
	}

	protected Result doPurchase(Money money, CreditCard creditcard, Order order) {
		Authorization authorization = authorize(money, creditcard, order);
		return capture(authorization);
	}

	public Result capture(Authorization authorization) {
		Preconditions.checkNotNull("Authorization can not be null",
				authorization);
		Result result = null;
		try {
			result = doCapture(authorization);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				null, authorization.getTransactionId());
		return result;
	}

	protected abstract Result doCapture(Authorization authorization);

	public Authorization authorize(Money money, CreditCard creditcard) {
		return authorize(money, creditcard, null);
	}

	public Authorization authorize(Money money, CreditCard creditcard,
			Order order) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The credit card can not be null",
				creditcard);
		Authorization auth = null;
		try {
			auth = doAuthorize(money, creditcard, order);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		return auth;
	}

	protected abstract Authorization doAuthorize(Money money,
			CreditCard creditcard, Order order);

	public Result cancel(String transactionId) {
		Preconditions.checkNotNull("Transaction id can not be null",
				transactionId);
		Result result = null;
		try {
			result = doCancel(transactionId);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				null, transactionId);
		return result;
	}

	protected abstract Result doCancel(String transactionId);

	public Result refund(Money money, String transactionId) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("Transaction id can not be null",
				transactionId);
		Result result = null;
		try {
			result = doRefund(money, transactionId);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				null, transactionId);
		return result;
	}

	protected abstract Result doRefund(Money money, String transactionId);

}
