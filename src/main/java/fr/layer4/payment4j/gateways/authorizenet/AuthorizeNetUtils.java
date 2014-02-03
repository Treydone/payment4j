package fr.layer4.payment4j.gateways.authorizenet;

import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.data.creditcard.CardType;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;

public abstract class AuthorizeNetUtils {

	public static Merchant getMerchant(Gateway gateway, String apiLoginId,
			String transactionKey) {
		Merchant merchant = Merchant.createMerchant(
				gateway.isTestingMode() ? Environment.SANDBOX
						: Environment.PRODUCTION, apiLoginId, transactionKey);
		return merchant;
	}

	public static net.authorize.data.creditcard.CreditCard convertCreditCard(
			CreditCard creditcard) {
		net.authorize.data.creditcard.CreditCard creditCard = net.authorize.data.creditcard.CreditCard
				.createCreditCard();
		creditCard.setCreditCardNumber(creditcard.getNumber());
		creditCard.setExpirationMonth(String.valueOf(creditcard.getMonth()));
		creditCard.setExpirationYear(String.valueOf(creditcard.getYear()));
		creditCard.setCardType(CardType
				.findByValue(creditcard.getType().name()));
		creditCard.setCardCode(creditcard.getVerificationValue());
		return creditCard;
	}
}
