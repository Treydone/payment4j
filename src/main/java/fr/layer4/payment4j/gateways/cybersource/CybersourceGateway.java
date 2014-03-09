package fr.layer4.payment4j.gateways.cybersource;

import java.util.Set;

import org.joda.money.CurrencyUnit;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.RecurringCapable;
import fr.layer4.payment4j.RecurringGateway;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

/**
 * http://apps.cybersource.com/library/documentation/dev_guides/CC_Svcs_SO_API/html/
 */
public class CybersourceGateway extends AbstractGateway implements
		TransactionCapable, RecurringCapable {

	private TransactionGateway transactionGateway;

	private RecurringGateway recurringGateway;

	private CybersourceGateway(boolean testingMode, String username,
			String transactionKey) {
		transactionGateway = new CybersourceGatewayTransactionGateway(this,
				username, transactionKey);
		recurringGateway = new CybersourceGatewayRecurringGateway(this,
				username, transactionKey);
		setTestingMode(testingMode);
	}

	public TransactionGateway transaction() {
		return transactionGateway;
	}

	public RecurringGateway recurring() {
		return recurringGateway;
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return null;
	}

	public String getHomepageUrl() {
		return null;
	}

	public String getDisplayName() {
		return null;
	}

	public CurrencyUnit getDefaultCurrency() {
		return null;
	}

	public Set<String> getSupportedCountries() {
		return null;
	}
}
