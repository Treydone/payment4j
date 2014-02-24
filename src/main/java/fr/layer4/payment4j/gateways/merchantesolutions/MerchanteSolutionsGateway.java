package fr.layer4.payment4j.gateways.merchantesolutions;

import java.util.Set;

import org.joda.money.CurrencyUnit;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.RecurringCapable;
import fr.layer4.payment4j.RecurringGateway;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class MerchanteSolutionsGateway extends AbstractGateway implements
		TransactionCapable, RecurringCapable {

	private TransactionGateway transactionGateway;

	private RecurringGateway recurringGateway;

	private MerchanteSolutionsGateway(boolean testingMode, String profileId,
			String profileKey) {
		transactionGateway = new MerchanteSolutionsTransactionGateway(this,
				profileId, profileKey);
		recurringGateway = new MerchanteSolutionsRecurringGateway(this,
				profileId, profileKey);
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
