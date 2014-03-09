package fr.layer4.payment4j.gateways;

import java.util.Map;

import org.iban4j.Iban;
import org.joda.money.Money;

import com.google.common.base.Preconditions;

import fr.layer4.payment4j.DirectTransferGateway;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Result;

public abstract class AbstractDirectTransferGateway implements
		DirectTransferGateway {

	protected Gateway gateway;

	public AbstractDirectTransferGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	public Result credit(Money money, Iban iban) {
		return credit(money, iban, null);
	}

	public Result credit(Money money, Iban iban, Map<String, Object> options) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The iban can not be null", iban);
		Result result = null;
		try {
			result = doCredit(money, iban, options);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				null, null);
		return result;
	}

	protected abstract Result doCredit(Money money, Iban iban,
			Map<String, Object> options);

	public Result purchase(Money money, Iban iban) {
		return purchase(money, iban, null);
	}

	public Result purchase(Money money, Iban iban, Map<String, Object> options) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The iban can not be null", iban);
		Result result = null;
		try {
			result = doPurchase(money, iban, options);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
				null, null);
		return result;
	}

	protected abstract Result doPurchase(Money money, Iban iban,
			Map<String, Object> options);

}
