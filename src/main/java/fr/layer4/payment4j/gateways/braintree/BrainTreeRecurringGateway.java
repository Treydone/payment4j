package fr.layer4.payment4j.gateways.braintree;

import java.util.Map;

import org.joda.money.Money;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.SubscriptionRequest;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.Schedule;
import fr.layer4.payment4j.gateways.AbstractRecurringGateway;

public class BrainTreeRecurringGateway extends AbstractRecurringGateway {

	private String merchantId;
	private String publicKey;
	private String privateKey;

	protected BrainTreeRecurringGateway(Gateway gateway, String merchantId,
			String publicKey, String privateKey) {
		super(gateway);
		this.merchantId = merchantId;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	@Override
	protected Result doRecurring(Money money, CreditCard creditCard,
			Schedule schedule, Map<String, Object> options) {
		BraintreeGateway gateway = BrainTreeUtils.getGateway(this.gateway,
				merchantId, publicKey, privateKey);
		SubscriptionRequest request = new SubscriptionRequest();
		request.numberOfBillingCycles(schedule.getTotalOccurences());
		// TODO
		// request.firstBillingDate(schedule.getStartDate());
		request.price(money.getAmount());
		gateway.subscription().create(request);
		return null;
	}

	@Override
	protected Result doCancel(String recurringReference,
			Map<String, Object> options) {
		BraintreeGateway gateway = BrainTreeUtils.getGateway(this.gateway,
				merchantId, publicKey, privateKey);
		gateway.subscription().cancel(recurringReference);
		return null;
	}
}
