package fr.layer4.payment4j.gateways.authorizenet;

import net.authorize.Environment;
import net.authorize.Merchant;
import fr.layer4.payment4j.Gateway;

public abstract class AuthorizeNetUtils {

	public static Merchant getMerchant(Gateway gateway, String apiLoginId,
			String transactionKey) {
		Merchant merchant = Merchant.createMerchant(
				gateway.isTestingMode() ? Environment.SANDBOX
						: Environment.PRODUCTION, apiLoginId, transactionKey);
		return merchant;
	}
}
