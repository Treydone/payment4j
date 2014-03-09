package fr.layer4.payment4j.gateways;

import java.util.Map;

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
		return credit(money, creditcard, null, null);
	}

	public Result credit(Money money, CreditCard creditcard) {
		return credit(money, creditcard, null, null);
	}

	public Result credit(Money money, CreditCard creditcard,
			Address billingAddress, Map<String, Object> options) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The credit card can not be null",
				creditcard);
		creditcard.check();
		Result result = null;
		try {
			result = doCredit(money, creditcard, billingAddress, options);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				creditcard, null);
		return result;
	}

	public abstract Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress, Map<String, Object> options);

	public Result purchase(Money money, CreditCard creditcard) {
		return purchase(money, creditcard, null, null);
	}

	public Result purchase(Money money, CreditCard creditcard, Order order) {
		return purchase(money, creditcard, order, null);
	}

	public Result purchase(Money money, CreditCard creditcard, Order order,
			Map<String, Object> options) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The credit card can not be null",
				creditcard);
		creditcard.check();
		Result result = null;
		try {
			result = doPurchase(money, creditcard, order, options);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				creditcard, null);
		return result;
	}

	protected Result doPurchase(Money money, CreditCard creditcard,
			Order order, Map<String, Object> options) {
		Authorization authorization = authorize(money, creditcard, order,
				options);
		return capture(authorization, options);
	}

	public Result capture(Authorization authorization) {
		return capture(authorization, null);
	}

	public Result capture(Authorization authorization,
			Map<String, Object> options) {
		Preconditions.checkNotNull("Authorization can not be null",
				authorization);
		Result result = null;
		try {
			result = doCapture(authorization, options);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				null, authorization.getTransactionId());
		return result;
	}

	protected abstract Result doCapture(Authorization authorization,
			Map<String, Object> options);

	public Authorization authorize(Money money, CreditCard creditcard) {
		return authorize(money, creditcard, null, null);
	}

	public Authorization authorize(Money money, CreditCard creditcard,
			Order order) {
		return authorize(money, creditcard, order, null);
	}

	public Authorization authorize(Money money, CreditCard creditcard,
			Order order, Map<String, Object> options) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The credit card can not be null",
				creditcard);
		creditcard.check();
		Authorization auth = null;
		try {
			auth = doAuthorize(money, creditcard, order, options);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		return auth;
	}

	protected abstract Authorization doAuthorize(Money money,
			CreditCard creditcard, Order order, Map<String, Object> options);

	public Result cancel(String transactionId) {
		return cancel(transactionId, null);
	}

	public Result cancel(String transactionId, Map<String, Object> options) {
		Preconditions.checkNotNull("Transaction id can not be null",
				transactionId);
		Result result = null;
		try {
			result = doCancel(transactionId, options);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				null, transactionId);
		return result;
	}

	protected abstract Result doCancel(String transactionId,
			Map<String, Object> options);

	public Result refund(Money money, String transactionId) {
		return refund(money, transactionId, null);
	}

	public Result refund(Money money, String transactionId,
			Map<String, Object> options) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("Transaction id can not be null",
				transactionId);
		Result result = null;
		try {
			result = doRefund(money, transactionId, options);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				null, transactionId);
		return result;
	}

	protected abstract Result doRefund(Money money, String transactionId,
			Map<String, Object> options);

}
