package fr.layer4.payment4j.gateways.stripe;

import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;

import fr.layer4.payment4j.CreditCardException;
import fr.layer4.payment4j.ExceptionResolver;
import fr.layer4.payment4j.ExpiredCreditCardException;
import fr.layer4.payment4j.IncorrectCreditCardNumberException;
import fr.layer4.payment4j.IncorrectVerificationCodeException;
import fr.layer4.payment4j.InvalidApiUsageException;
import fr.layer4.payment4j.UnknownTransactionException;

public class StripeExceptionResolver implements ExceptionResolver {

	public Throwable resolve(Throwable throwable) {
		// Because all stripe exceptions are wrapped in a runtime exception:
		throwable = throwable.getCause();

		if (throwable instanceof AuthenticationException) {
			throw new fr.layer4.payment4j.AuthenticationException(
					throwable.getMessage(), throwable);
		} else if (throwable instanceof CardException) {
			CardException cardException = (CardException) throwable;
			String code = cardException.getCode();
			if ("invalid_number".equals(code)
					|| "incorrect_number".equals(code)) {
				throw new IncorrectCreditCardNumberException(
						cardException.getMessage(), cardException);
			} else if ("expired_card".equals(code)
					|| "invalid_expiry_month".equals(code)
					|| "invalid_expiry_year".equals(code)) {
				throw new ExpiredCreditCardException(
						cardException.getMessage(), cardException);
			} else if ("incorrect_cvc".equals(code)) {
				throw new IncorrectVerificationCodeException(
						cardException.getMessage(), cardException);
			}
			throw new CreditCardException(cardException.getMessage(),
					cardException);
		} else if (throwable instanceof InvalidRequestException) {
			InvalidRequestException invalidRequestException = (InvalidRequestException) throwable;
			if (invalidRequestException.getMessage().contains("No such charge")) {
				throw new UnknownTransactionException(
						invalidRequestException.getMessage(),
						invalidRequestException);
			}
			throw new InvalidApiUsageException(
					invalidRequestException.getMessage(),
					invalidRequestException);
		} else if (throwable instanceof APIException) {
			throw new InvalidApiUsageException(throwable.getMessage(),
					throwable);
		}
		return null;
	}
}