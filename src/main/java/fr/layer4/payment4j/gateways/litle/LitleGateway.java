package fr.layer4.payment4j.gateways.litle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.money.CurrencyUnit;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.TransactionCapable;
import fr.layer4.payment4j.TransactionGateway;
import fr.layer4.payment4j.gateways.AbstractGateway;

public class LitleGateway extends AbstractGateway implements TransactionCapable {

	private TransactionGateway transactionGateway;

	/*
	 * password=pw merchantId=merchantId username=test
	 * url=https\://www.testlitle.com/sandbox/communicator/online
	 * maxAllowedTransactionsPerFile=500000 maxTransactionsPerBatch=100000
	 * timeout=65 batchRequestFolder= batchTcpTimeout=7200000
	 * sftpTimeout=7200000 batchPort=15000 batchUseSSL=true reportGroup=Default
	 * Report Group batchHost=https\://www.testlitle.com/sandbox proxyPort=
	 * sftpPassword= batchResponseFolder= printxml=false proxyHost=
	 * sftpUsername=
	 */
	public LitleGateway(boolean testingMode, Properties properties) {
		Preconditions.checkArgument(
				StringUtils.isNotBlank(properties.getProperty("password")),
				"password is null");
		Preconditions.checkArgument(
				StringUtils.isNotBlank(properties.getProperty("merchantId")),
				"merchantId is null");
		Preconditions.checkArgument(
				StringUtils.isNotBlank(properties.getProperty("username")),
				"username is null");

		if (testingMode) {
			properties.put("url",
					"https://www.testlitle.com/sandbox/communicator/online");
			properties.put("batchHost", "https://www.testlitle.com/sandbox");
		} else {
			properties.put("url",
					"https://payments.litle.com/vap/communicator/online");
			properties.put("batchHost", "payments.litle.com");
		}
		transactionGateway = new LitleTransactionGateway(this, properties);
		setTestingMode(testingMode);
	}

	public static LitleGateway build(boolean testingMode, String username,
			String password, String merchantId) {
		Properties properties = new Properties();
		properties.put("password", password);
		properties.put("merchantId", merchantId);
		properties.put("username", username);
		return new LitleGateway(testingMode, properties);
	}

	public TransactionGateway transactionGateway() {
		return transactionGateway;
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTER, CreditCardType.AMERICAN_EXPRESS,
				CreditCardType.DISCOVER, CreditCardType.DINERS_CLUB,
				CreditCardType.JCB));
	}

	public String getHomepageUrl() {
		return "http://www.litle.com/";
	}

	public String getDisplayName() {
		return "Litle & Co.";
	}

	public CurrencyUnit getDefaultCurrency() {
		return CurrencyUnit.USD;
	}

	public Set<String> getSupportedCountries() {
		return Sets.newHashSet("FR");
	}

}
