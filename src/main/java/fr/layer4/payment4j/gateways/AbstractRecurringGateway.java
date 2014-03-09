package fr.layer4.payment4j.gateways;

import java.util.Date;
import java.util.Map;

import org.joda.money.Money;

import com.google.common.base.Preconditions;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.RecurringGateway;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.Schedule;

public abstract class AbstractRecurringGateway implements RecurringGateway {

	protected Gateway gateway;

	public AbstractRecurringGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	public Result recurring(Money money, CreditCard creditCard,
			Schedule schedule) {
		return recurring(money, creditCard, schedule, null);
	}

	public Result recurring(Money money, CreditCard creditCard,
			Schedule schedule, Map<String, Object> options) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The credit card can not be null",
				creditCard);
		Preconditions.checkNotNull("The schedule can not be null", schedule);
		creditCard.check();

		if (schedule.getStartDate() == null) {
			schedule.setStartDate(new Date());
		}

		try {
			Result result = doRecurring(money, creditCard, schedule, options);
			GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
					creditCard, null);
			return result;
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
	}

	protected abstract Result doRecurring(Money money, CreditCard creditCard,
			Schedule schedule, Map<String, Object> options);

	public Result cancel(String recurringReference) {
		return cancel(recurringReference, null);
	}

	public Result cancel(String recurringReference, Map<String, Object> options) {
		Preconditions.checkNotNull("The amount can not be null",
				recurringReference);

		try {
			Result result = doCancel(recurringReference, options);
			GatewayUtils.resoleCode(gateway.getResponseCodeResolver(), result,
					null, recurringReference);
			return result;
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
	}

	protected abstract Result doCancel(String recurringReference,
			Map<String, Object> options);

}
