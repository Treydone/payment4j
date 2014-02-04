package fr.layer4.payment4j.gateways.app55;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app55.error.ApiException;

import fr.layer4.payment4j.ExceptionResolver;
import fr.layer4.payment4j.ExpiredCreditCardException;
import fr.layer4.payment4j.InvalidCreditCardNumberException;
import fr.layer4.payment4j.InvalidExpirationDateException;
import fr.layer4.payment4j.InvalidVerificationCodeException;

public class App55ExceptionResolver implements ExceptionResolver {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(App55ExceptionResolver.class);

	public Throwable resolve(Throwable throwable) {

		// https://www.app55.com/docs/api/errors/codes
		if (throwable instanceof ApiException) {
			
			ApiException exception = (ApiException) throwable;

			if (exception.getCode() == 0x20006) {
				throw new ExpiredCreditCardException(exception.getMessage(),
						exception);
			} else if (exception.getCode() == 0x30203) {
				throw new InvalidCreditCardNumberException(
						exception.getMessage(), exception);
			} else if (exception.getCode() == 0x30204) {
				throw new InvalidVerificationCodeException(
						exception.getMessage(), exception);
			} else if (exception.getCode() == 0x30205) {
				throw new InvalidExpirationDateException(
						exception.getMessage(), exception);
			}

			LOGGER.debug("Unknown code {}", exception.getCode());

		}
		return null;
	}
}
