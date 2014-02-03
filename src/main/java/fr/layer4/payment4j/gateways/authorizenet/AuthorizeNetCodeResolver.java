package fr.layer4.payment4j.gateways.authorizenet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.layer4.payment4j.AuthenticationException;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.ExpiredCreditCardException;
import fr.layer4.payment4j.IncorrectCreditCardNumberException;
import fr.layer4.payment4j.InvalidCreditCardNumberException;
import fr.layer4.payment4j.InvalidVerificationCodeException;
import fr.layer4.payment4j.PaymentException;
import fr.layer4.payment4j.ResponseCodeResolver;
import fr.layer4.payment4j.TransactionException;
import fr.layer4.payment4j.UnknownTransactionException;

public class AuthorizeNetCodeResolver implements ResponseCodeResolver {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthorizeNetCodeResolver.class);

	public void resolve(String code, String message, CreditCard creditCard,
			String transactionId) {
		if ("RRC_2_37".equals(code)) {
			throw new InvalidCreditCardNumberException(creditCard);
		} else if ("RRC_2_44".equals(code)) {
			throw new InvalidVerificationCodeException(creditCard);
		} else if ("RRC_3_6".equals(code)) {
			throw new IncorrectCreditCardNumberException(creditCard);
		} else if ("RRC_3_8".equals(code)) {
			throw new ExpiredCreditCardException(creditCard);
		} else if ("RRC_3_13".equals(code)) {
			throw new AuthenticationException("Credentials invalid");
		} else if ("RRC_3_16".equals(code)) {
			throw new UnknownTransactionException(transactionId);
		} else if ("RRC_3_40".equals(code)) {
			throw new TransactionException(transactionId,
					"This transaction must be encrypted");
		} else if ("RRC_3_78".equals(code)) {
			throw new InvalidVerificationCodeException(creditCard);
		} else if (!"RRC_1_1".equals(code)) {
			LOGGER.debug("code {} ({})is unknown", code, message);
			throw new PaymentException(message);
		}
	}
}
