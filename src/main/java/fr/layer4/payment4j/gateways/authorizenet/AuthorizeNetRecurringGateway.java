package fr.layer4.payment4j.gateways.authorizenet;

import java.math.BigDecimal;

import net.authorize.Merchant;
import net.authorize.aim.Transaction;
import net.authorize.data.arb.PaymentSchedule;
import net.authorize.data.arb.Subscription;
import net.authorize.data.arb.SubscriptionUnitType;
import net.authorize.data.xml.Payment;

import org.joda.money.Money;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.Schedule;
import fr.layer4.payment4j.gateways.AbstractRecurringGateway;

public class AuthorizeNetRecurringGateway extends AbstractRecurringGateway {

	private String apiLoginId;
	private String transactionKey;

	protected AuthorizeNetRecurringGateway(Gateway gateway, String apiLoginId,
			String transactionKey) {
		super(gateway);
		this.apiLoginId = apiLoginId;
		this.transactionKey = transactionKey;
	}

	@Override
	protected Result doRecurring(Money money, CreditCard creditCard,
			Schedule schedule) {
		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);

		PaymentSchedule paymentSchedule = PaymentSchedule
				.createPaymentSchedule();
		paymentSchedule.setStartDate(schedule.getStartDate());

		switch (schedule.getEach()) {
		case DAY:
			paymentSchedule.setIntervalLength(schedule.getInterval());
			paymentSchedule.setSubscriptionUnit(SubscriptionUnitType.DAYS);
			break;
		case WEEK:
			paymentSchedule.setIntervalLength(schedule.getInterval() * 7);
			paymentSchedule.setSubscriptionUnit(SubscriptionUnitType.DAYS);
			break;
		case MONTH:
			paymentSchedule.setIntervalLength(schedule.getInterval());
			paymentSchedule.setSubscriptionUnit(SubscriptionUnitType.MONTHS);
			break;
		case YEAR:
			paymentSchedule.setIntervalLength(schedule.getInterval() * 12);
			paymentSchedule.setSubscriptionUnit(SubscriptionUnitType.MONTHS);
			break;
		}
		paymentSchedule.setTotalOccurrences(schedule.getTotalOccurences());
		paymentSchedule.setTrialOccurrences(0);

		// Create a customer and specify billing info
		// TODO
		net.authorize.data.xml.Customer customer = net.authorize.data.xml.Customer
				.createCustomer();

		// Create a subscription and specify payment, schedule and customer
		Subscription subscription = Subscription.createSubscription();
		subscription.setPayment(Payment.createPayment(AuthorizeNetUtils
				.convertCreditCard(creditCard)));
		subscription.setSchedule(paymentSchedule);
		subscription.setCustomer(customer);
		subscription.setAmount(new BigDecimal(6.00));
		subscription.setTrialAmount(Transaction.ZERO_AMOUNT);
		String ref = "REF" + System.currentTimeMillis();
		subscription.setRefId(ref);
		subscription.setName("Subscription " + System.currentTimeMillis());

		net.authorize.arb.Transaction transaction = merchant
				.createARBTransaction(
						net.authorize.arb.TransactionType.CREATE_SUBSCRIPTION,
						subscription);
		net.authorize.arb.Result<Transaction> result = (net.authorize.arb.Result<Transaction>) merchant
				.postTransaction(transaction);
		return AuthorizeNetUtils.convertResult(result);
	}

	@Override
	protected Result doCancel(String recurringReference) {

		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);

		Subscription subscription = Subscription.createSubscription();
		subscription.setRefId(recurringReference);

		net.authorize.arb.Transaction transaction = merchant
				.createARBTransaction(
						net.authorize.arb.TransactionType.CANCEL_SUBSCRIPTION,
						subscription);
		net.authorize.arb.Result<Transaction> result = (net.authorize.arb.Result<Transaction>) merchant
				.postTransaction(transaction);
		return AuthorizeNetUtils.convertResult(result);
	}
}
