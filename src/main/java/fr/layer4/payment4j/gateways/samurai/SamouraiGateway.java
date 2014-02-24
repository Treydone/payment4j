package fr.layer4.payment4j.gateways.samurai;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;

import com.google.common.collect.Sets;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class SamouraiGateway extends AbstractGateway implements
		TransactionCapable {

	private TransactionGateway transactionGateway;

	private SamouraiGateway(boolean testingMode, String merchantKey,
			String merchantPassword, String processorToken) {
		transactionGateway = new SamouraiTransactionGateway(this, merchantKey,
				merchantPassword, processorToken);
		setTestingMode(testingMode);

	}

	public TransactionGateway transaction() {
		return transactionGateway;
	}

	public static SamouraiGateway build(boolean testingMode,
			String merchantKey, String merchantPassword, String processorToken) {
		return new SamouraiGateway(testingMode, merchantKey, merchantPassword,
				processorToken);
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
