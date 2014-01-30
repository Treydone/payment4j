package fr.layer4.payment4j.gateways.paypal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;

import paypal.payflow.SDKProperties;

import com.google.common.collect.Sets;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class PaypalGateway extends AbstractGateway implements
		TransactionCapable {

	private TransactionGateway transactionGateway;

	private PaypalGateway(boolean testingMode, String user, String vendor,
			String partner, String password) {
		transactionGateway = new PaypalTransactionGateway(this, user, vendor,
				partner, password);
		setTestingMode(testingMode);

		SDKProperties
				.setHostAddress(isTestingMode() ? "pilot-payflowpro.paypal.com"
						: "payflowpro.paypal.com");
		SDKProperties.setHostPort(443);
		SDKProperties.setTimeOut(45);

		// Logging is by default off. To turn logging on uncomment the following
		// lines:
		// SDKProperties.setLogFileName("payflow_java.log");
		// SDKProperties.setLoggingLevel(PayflowConstants.SEVERITY_DEBUG);
		// SDKProperties.setMaxLogFileSize(1000000);

		// Uncomment the lines below and set the proxy address and port, if a
		// proxy has to be used.
		// SDKProperties.setProxyAddress("");
		// SDKProperties.setProxyPort(80);
		// SDKProperties.setProxyLogin("");
		// SDKProperties.setProxyPassword("");
	}

	public TransactionGateway transactionGateway() {
		return transactionGateway;
	}

	public static PaypalGateway build(boolean testingMode, String user,
			String vendor, String partner, String password) {
		return new PaypalGateway(testingMode, user, vendor, partner, password);
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTER, CreditCardType.AMERICAN_EXPRESS,
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
