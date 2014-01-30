package fr.layer4.payment4j.gateways.authorizenet;

import java.math.BigDecimal;

import net.authorize.Merchant;
import net.authorize.aim.Transaction;
import net.authorize.data.arb.PaymentSchedule;
import net.authorize.data.arb.Subscription;
import net.authorize.data.arb.SubscriptionUnitType;
import net.authorize.data.xml.Payment;
import net.authorize.xml.Message;

import org.joda.money.Money;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
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
	protected void doRecurring(Money money, CreditCard creditCard,
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
		}
		paymentSchedule.setTotalOccurrences(schedule.getTotalOccurences());
		paymentSchedule.setTrialOccurrences(0);

		// Create a new credit card
		net.authorize.data.creditcard.CreditCard credit_card = net.authorize.data.creditcard.CreditCard
				.createCreditCard();
		credit_card.setCreditCardNumber(creditCard.getNumber());
		credit_card.setExpirationDate(creditCard.getExpirationAsForCharacter());

		// Create a customer and specify billing info
		// TODO
		net.authorize.data.xml.Customer customer = net.authorize.data.xml.Customer
				.createCustomer();

		// Create a subscription and specify payment, schedule and customer
		Subscription subscription = Subscription.createSubscription();
		subscription.setPayment(Payment.createPayment(credit_card));
		subscription.setSchedule(paymentSchedule);
		subscription.setCustomer(customer);
		subscription.setAmount(new BigDecimal(6.00));
		subscription.setTrialAmount(Transaction.ZERO_AMOUNT);
		subscription.setRefId("REF:" + System.currentTimeMillis());
		subscription.setName("Subscription " + System.currentTimeMillis());

		net.authorize.arb.Transaction transaction = merchant
				.createARBTransaction(
						net.authorize.arb.TransactionType.CREATE_SUBSCRIPTION,
						subscription);
		net.authorize.arb.Result<Transaction> result = (net.authorize.arb.Result<Transaction>) merchant
				.postTransaction(transaction);

		System.out.println("Result Code: "
				+ (result.getResultCode() != null ? result.getResultCode()
						: "No result code") + "<br/>");
		System.out.println("Result Subscription Id: "
				+ result.getResultSubscriptionId() + "<br/>");
		for (int i = 0; i < result.getMessages().size(); i++) {
			Message message = (Message) result.getMessages().get(i);
			System.out.println("Message code/text: " + message.getCode()
					+ " - " + message.getText() + "<br/>");
		}

	}
}
