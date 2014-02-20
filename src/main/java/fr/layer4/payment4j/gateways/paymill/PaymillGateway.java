package fr.layer4.payment4j.gateways.paymill;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;

import com.google.common.collect.Sets;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.DirectTransferCapable;
import fr.layer4.payment4j.DirectTransferGateway;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class PaymillGateway extends AbstractGateway implements
		TransactionCapable, DirectTransferCapable {

	private TransactionGateway transactionGateway;

	private DirectTransferGateway directTransferGateway;

	private PaymillGateway(boolean testingMode, String apiKey) {
		transactionGateway = new PaymillTransactionGateway(this, apiKey);
		directTransferGateway = new PaymillDirectTransferGateway(this, apiKey);
		setTestingMode(testingMode);

	}

	public DirectTransferGateway direct() {
		return directTransferGateway;
	}

	public TransactionGateway transaction() {
		return transactionGateway;
	}

	public static PaymillGateway build(boolean testingMode, String apiKey) {
		return new PaymillGateway(testingMode, apiKey);
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
