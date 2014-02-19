package fr.layer4.payment4j.gateways.authorizenet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;

import com.google.common.collect.Sets;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.HistoryCapable;
import fr.layer4.payment4j.HistoryGateway;
import fr.layer4.payment4j.RecurringCapable;
import fr.layer4.payment4j.RecurringGateway;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class AuthorizeNetGateway extends AbstractGateway implements
		TransactionCapable, HistoryCapable, RecurringCapable {

	private TransactionGateway transactionGateway;

	private HistoryGateway historyGateway;

	private RecurringGateway recurringGateway;

	private AuthorizeNetGateway(boolean testingMode, String apiLoginId,
			String transactionKey) {
		transactionGateway = new AuthorizeNetTransactionGateway(this,
				apiLoginId, transactionKey);
		historyGateway = new AuthorizeNetHistoryGateway(this, apiLoginId,
				transactionKey);
		recurringGateway = new AuthorizeNetRecurringGateway(this, apiLoginId,
				transactionKey);
		setResponseCodeResolver(new AuthorizeNetCodeResolver());
		setTestingMode(testingMode);
	}

	public TransactionGateway transaction() {
		return transactionGateway;
	}

	public HistoryGateway history() {
		return historyGateway;
	}

	public RecurringGateway recurring() {
		return recurringGateway;
	}

	public static AuthorizeNetGateway build(boolean testingMode,
			String apiLoginId, String transactionKey) {
		return new AuthorizeNetGateway(testingMode, apiLoginId, transactionKey);
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTERCARD, CreditCardType.AMERICAN_EXPRESS,
				CreditCardType.DISCOVER, CreditCardType.DINERS_CLUB,
				CreditCardType.JCB));
	}

	public String getHomepageUrl() {
		return "http//www.authorize.net/";
	}

	public String getDisplayName() {
		return "Authorize.Net";
	}

	public CurrencyUnit getDefaultCurrency() {
		return CurrencyUnit.USD;
	}

	public Set<String> getSupportedCountries() {
		return Sets.newHashSet("US", "CA", "GB");
	}

}
