package fr.layer4.payment4j.gateways.braintree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;

import com.google.common.collect.Sets;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class BrainTreeGateway extends AbstractGateway implements
		TransactionCapable {

	private BrainTreeTransactionGateway gateway;

	private BrainTreeGateway(boolean testingMode, String merchantId,
			String publicKey, String privateKey) {
		gateway = new BrainTreeTransactionGateway(this, merchantId, publicKey,
				privateKey);
		setTestingMode(testingMode);
	}

	public TransactionGateway transaction() {
		return gateway;
	}

	public static BrainTreeGateway build(boolean testingMode,
			String merchantId, String publicKey, String privateKey) {
		return new BrainTreeGateway(testingMode, merchantId, publicKey,
				privateKey);
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTERCARD, CreditCardType.AMERICAN_EXPRESS,
				CreditCardType.DISCOVER, CreditCardType.DINERS_CLUB,
				CreditCardType.JCB));
	}

	public String getHomepageUrl() {
		return "http://www.braintreepaymentsolutions.com";
	}

	public String getDisplayName() {
		return "Braintree";
	}

	public CurrencyUnit getDefaultCurrency() {
		return CurrencyUnit.USD;
	}

	public Set<String> getSupportedCountries() {
		return Sets.newHashSet("US", "CA", "AU", "AD", "AT", "BE", "BG", "CY",
				"CZ", "DK", "EE", "FI", "FR", "GI", "DE", "GR", "HU", "IS",
				"IM", "IE", "IT", "LV", "LI", "LT", "LU", "MT", "MC", "NL",
				"NO", "PL", "PT", "RO", "SM", "SK", "SI", "ES", "SE", "CH",
				"TR", "GB");
	}
}
