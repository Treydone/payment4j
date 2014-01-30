package fr.layer4.payment4j.gateways.stripe;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;

import com.google.common.collect.Sets;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.RecurringCapable;
import fr.layer4.payment4j.RecurringGateway;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class StripeGateway extends AbstractGateway implements
		TransactionCapable, RecurringCapable {

	private TransactionGateway transactionGateway;

	private RecurringGateway recurringGateway;

	private StripeGateway(boolean testingMode, String apiKey) {
		transactionGateway = new StripeTransactionGateway(this, apiKey);
		recurringGateway = new StripeRecurringGateway(this, apiKey);
		setExceptionResolver(new StripeExceptionResolver());
	}

	public TransactionGateway transactionGateway() {
		return transactionGateway;
	}

	public RecurringGateway recurringGateway() {
		return recurringGateway;
	}

	public static StripeGateway build(boolean testingMode, String apiKey) {
		return new StripeGateway(testingMode, apiKey);
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTER, CreditCardType.AMERICAN_EXPRESS,
				CreditCardType.DISCOVER, CreditCardType.DINERS_CLUB,
				CreditCardType.JCB));
	}

	public String getHomepageUrl() {
		return "https://stripe.com/";
	}

	public String getDisplayName() {
		return "Stripe";
	}

	public CurrencyUnit getDefaultCurrency() {
		return CurrencyUnit.USD;
	}

	public Set<String> getSupportedCountries() {
		return Sets.newHashSet("US", "CA", "GB", "AU", "IE", "FR", "NL", "BE",
				"DE", "ES");
	}
}
