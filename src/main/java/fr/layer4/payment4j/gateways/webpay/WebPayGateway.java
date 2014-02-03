package fr.layer4.payment4j.gateways.webpay;

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

public class WebPayGateway extends AbstractGateway implements
		TransactionCapable, RecurringCapable {

	private TransactionGateway transactionGateway;

	private RecurringGateway recurringGateway;

	private WebPayGateway(boolean testingMode, String apiKey) {
		transactionGateway = new WebPayTransactionGateway(this, apiKey);
		recurringGateway = new WebPayRecurringGateway(this, apiKey);
		setExceptionResolver(new WebPayExceptionResolver());
	}

	public TransactionGateway transactionGateway() {
		return transactionGateway;
	}

	public RecurringGateway recurringGateway() {
		return recurringGateway;
	}

	public static WebPayGateway build(boolean testingMode, String apiKey) {
		return new WebPayGateway(testingMode, apiKey);
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTER, CreditCardType.AMERICAN_EXPRESS,
				CreditCardType.DINERS_CLUB, CreditCardType.JCB));
	}

	public String getHomepageUrl() {
		return "https://webpay.jp/";
	}

	public String getDisplayName() {
		return "WebPay";
	}

	public CurrencyUnit getDefaultCurrency() {
		return CurrencyUnit.JPY;
	}

	public Set<String> getSupportedCountries() {
		return Sets.newHashSet("JP");
	}
}
