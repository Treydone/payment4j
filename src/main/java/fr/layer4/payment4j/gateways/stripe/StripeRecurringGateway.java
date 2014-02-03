package fr.layer4.payment4j.gateways.stripe;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.joda.money.Money;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.Schedule;
import fr.layer4.payment4j.gateways.AbstractRecurringGateway;

public class StripeRecurringGateway extends AbstractRecurringGateway {

	private String apiKey;

	protected StripeRecurringGateway(Gateway gateway, String apiKey) {
		super(gateway);
		this.apiKey = apiKey;
	}

	@Override
	protected Result doRecurring(Money money, CreditCard creditCard,
			Schedule schedule) {
		Date startDate = schedule.getStartDate();
		if (startDate == null) {
			startDate = new Date();
		}

		Subscription subscription = new Subscription();
		subscription.setStart(startDate.getTime());
		Plan plan = new Plan();
		plan.setAmount(money.getAmount().multiply(BigDecimal.valueOf(100))
				.intValue());
		plan.setCurrency(money.getCurrencyUnit().getCode().toLowerCase());
		switch (schedule.getEach()) {
		case DAY:
			throw new NotImplementedException(
					"Day plan subscription are not allowed on Stripe");
		case MONTH:
			plan.setInterval("month");
			break;
		case WEEK:
			plan.setInterval("week");
			break;
		}
		plan.setIntervalCount(Integer.valueOf(schedule.getInterval()));

		long endedAt = startDate.getTime();
		switch (schedule.getEach()) {
		case MONTH:
			endedAt += (1000 * 60 * 60 * 24 * 30)
					* schedule.getTotalOccurences();
			break;
		case WEEK:
			endedAt += (1000 * 60 * 60 * 24 * 7)
					* schedule.getTotalOccurences();
			break;
		case DAY:
			throw new NotImplementedException(
					"Day plan subscription are not allowed on Stripe");
		default:
			break;
		}

		subscription.setEndedAt(endedAt);
		subscription.setPlan(plan);
		String ref = "REF" + System.currentTimeMillis();
		subscription.setId(ref);

		try {
			Customer cu = Customer.retrieve("cus_3QZq87ZoS9yOwO");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("plan", "foo");
			cu.createSubscription(params, apiKey);
		} catch (StripeException e) {
			e.printStackTrace();
		}

		Result result2 = new Result();
		result2.setSuccess(true);
		result2.setRecurringRef(ref);
		return result2;
	}

	@Override
	protected Result doCancel(String recurringReference) {
		try {
			Customer cu = Customer.retrieve("cus_3QZq87ZoS9yOwO");
			cu.cancelSubscription(apiKey);
		} catch (StripeException e) {
			e.printStackTrace();
		}
		Result result2 = new Result();
		result2.setSuccess(true);
		return result2;
	}
}
