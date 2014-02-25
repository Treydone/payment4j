package fr.layer4.payment4j.gateways.paymill;

import java.math.BigDecimal;

import org.joda.money.Money;

import com.paymill.context.PaymillContext;
import com.paymill.models.Offer;
import com.paymill.models.Payment;
import com.paymill.models.Payment.CardType;
import com.paymill.models.Payment.Type;
import com.paymill.models.Subscription;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.Schedule;
import fr.layer4.payment4j.gateways.AbstractRecurringGateway;

public class PaymillRecurringGateway extends AbstractRecurringGateway {

	private String apiKey;

	public PaymillRecurringGateway(Gateway gateway, String apiKey) {
		super(gateway);
		this.apiKey = apiKey;
	}

	@Override
	protected Result doRecurring(Money money, CreditCard creditcard,
			Schedule schedule) {
		PaymillContext paymillContext = new PaymillContext(apiKey);

		Payment payment = PaymillUtils.convertCreditCard(creditcard);

		Offer offer = new Offer();
		switch (schedule.getEach()) {
		case DAY:
			offer.setInterval(Integer.toString(schedule.getInterval()) + " "
					+ Offer.Unit.DAY.getValue());
			break;
		case WEEK:
			offer.setInterval(Integer.toString(schedule.getInterval()) + " "
					+ Offer.Unit.WEEK.getValue());
			break;
		case MONTH:
			offer.setInterval(Integer.toString(schedule.getInterval()) + " "
					+ Offer.Unit.MONTH.getValue());
			break;
		case YEAR:
			offer.setInterval(Integer.toString(schedule.getInterval()) + " "
					+ Offer.Unit.YEAR.getValue());
			break;
		}
		offer.setCurrency(money.getCurrencyUnit().getCurrencyCode());
		offer.setAmount(money.getAmount().multiply(BigDecimal.valueOf(100))
				.intValue());
		Subscription response = paymillContext.getSubscriptionService()
				.createWithOfferAndPayment(offer, payment);
		Result result = new Result();
		result.setSuccess(true);
		result.setRecurringRef(response.getId());
		return result;
	}

	@Override
	protected Result doCancel(String recurringReference) {
		PaymillContext paymillContext = new PaymillContext(apiKey);
		paymillContext.getSubscriptionService().delete(recurringReference);
		Result result = new Result();
		result.setSuccess(true);
		return result;
	}
}
