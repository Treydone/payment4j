package fr.layer4.payment4j.gateways;

import java.util.Date;

import org.joda.money.Money;

import com.google.common.base.Preconditions;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.RecurringGateway;
import fr.layer4.payment4j.Schedule;

public abstract class AbstractRecurringGateway implements RecurringGateway {

	protected Gateway gateway;

	public AbstractRecurringGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	public void recurring(Money money, CreditCard creditCard, Schedule schedule) {
		Preconditions.checkNotNull("The amount can not be null", money);
		Preconditions.checkNotNull("The credit card can not be null",
				creditCard);
		Preconditions.checkNotNull("The schedule can not be null", schedule);

		if (schedule.getStartDate() == null) {
			schedule.setStartDate(new Date());
		}

		try {
			doRecurring(money, creditCard, schedule);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		// GatewayUtils.resoleCode(gateway.getResponseCodeResolver(),
		// transactions.getResult(), null, null);
	}

	protected abstract void doRecurring(Money money, CreditCard creditCard,
			Schedule schedule);

}
