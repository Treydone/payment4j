package fr.layer4.payment4j.gateways.litle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litle.sdk.LitleOnlineException;

import fr.layer4.payment4j.ExceptionResolver;
import fr.layer4.payment4j.InvalidCreditCardNumberException;

public class LitleExceptionResolver implements ExceptionResolver {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LitleExceptionResolver.class);

	public Throwable resolve(Throwable throwable) {

		if (throwable instanceof LitleOnlineException) {
			if (throwable.getMessage().contains("ccAccountNumberType")) {
				throw new InvalidCreditCardNumberException(
						throwable.getMessage(), throwable);
			}
		}
		LOGGER.debug("Can not resolve message {}", throwable.getMessage());
		return null;
	}
}
