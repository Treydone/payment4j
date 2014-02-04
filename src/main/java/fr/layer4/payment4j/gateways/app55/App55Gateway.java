package fr.layer4.payment4j.gateways.app55;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;

import com.google.common.collect.Sets;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class App55Gateway extends AbstractGateway implements TransactionCapable {

	private TransactionGateway transactionGateway;

	private App55Gateway(boolean testingMode, String apiKey, String apiSecret) {
		transactionGateway = new App55TransactionGateway(this, apiKey,
				apiSecret);
		setExceptionResolver(new App55ExceptionResolver());
	}

	public TransactionGateway transactionGateway() {
		return transactionGateway;
	}

	public static App55Gateway build(boolean testingMode, String apiKey,
			String apiSecret) {
		return new App55Gateway(testingMode, apiKey, apiSecret);
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTER, CreditCardType.AMERICAN_EXPRESS,
				CreditCardType.DISCOVER, CreditCardType.DINERS_CLUB,
				CreditCardType.JCB));
	}

	public String getHomepageUrl() {
		return "https://www.app55.com";
	}

	public String getDisplayName() {
		return "App55";
	}

	public CurrencyUnit getDefaultCurrency() {
		return CurrencyUnit.USD;
	}

	public Set<String> getSupportedCountries() {
		return Sets.newHashSet("US", "CA", "GB", "AU", "IE", "FR", "NL", "BE",
				"DE", "ES");
	}
}
