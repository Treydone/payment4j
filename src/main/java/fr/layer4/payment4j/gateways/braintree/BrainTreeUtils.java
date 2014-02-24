package fr.layer4.payment4j.gateways.braintree;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

import fr.layer4.payment4j.Gateway;

public abstract class BrainTreeUtils {

	public static BraintreeGateway getGateway(Gateway gateway,
			String merchantId, String publicKey, String privateKey) {
		BraintreeGateway braintreeGateway = new BraintreeGateway(
				gateway.isTestingMode() ? Environment.SANDBOX
						: Environment.PRODUCTION, merchantId, publicKey,
				privateKey);
		return braintreeGateway;
	}
}
