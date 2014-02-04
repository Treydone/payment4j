package fr.layer4.payment4j.gateways.balanced;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;

import com.google.common.collect.Sets;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class BalancedGateway extends AbstractGateway implements
		TransactionCapable {

	private TransactionGateway transactionGateway;

	private BalancedGateway(boolean testingMode, String apiKey, String apiSecret) {
		transactionGateway = new BalancedTransactionGateway(this, apiKey,
				apiSecret);
		setExceptionResolver(new BalancedExceptionResolver());
	}

	public TransactionGateway transactionGateway() {
		return transactionGateway;
	}

	public static BalancedGateway build(boolean testingMode, String apiKey,
			String apiSecret) {
		return new BalancedGateway(testingMode, apiKey, apiSecret);
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTER, CreditCardType.AMERICAN_EXPRESS,
				CreditCardType.DISCOVER, CreditCardType.JCB));
	}

	public String getHomepageUrl() {
		return "https://www.balancedpayments.com/";
	}

	public String getDisplayName() {
		return "Balanced";
	}

	public CurrencyUnit getDefaultCurrency() {
		return CurrencyUnit.USD;
	}

	public Set<String> getSupportedCountries() {
		return Sets.newHashSet("US");
	}
}
