package fr.layer4.payment4j.gateways.cybersource;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.cxf.frontend.ClientProxy;

import com.cybersource.schemas.transaction_data.transactionprocessor.ITransactionProcessor;
import com.cybersource.schemas.transaction_data.transactionprocessor.TransactionProcessor;
import com.google.common.annotations.VisibleForTesting;

public abstract class CybersourceUtils {

	public static final URL HOSTNAME = initUrl("https://ics2ws.ic3.com/commerce/1.x/transactionProcessor/CyberSourceTransaction_1.95.wsdl");
	public static final URL TEST_HOSTNAME = initUrl("https://ics2wstest.ic3.com/commerce/1.x/transactionProcessor/CyberSourceTransaction_1.95.wsdl");

	private static URL initUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@VisibleForTesting
	public static ITransactionProcessor build(boolean testingMode,
			String username, String transactionKey) {
		ITransactionProcessor processor = new TransactionProcessor(
				testingMode ? TEST_HOSTNAME : HOSTNAME).getPortXML();
		ClientProxy.getClient(processor).getEndpoint();
		return processor;
	}
}
