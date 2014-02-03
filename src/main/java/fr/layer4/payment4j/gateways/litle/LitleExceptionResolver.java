package fr.layer4.payment4j.gateways.litle;

import com.litle.sdk.LitleOnlineException;

import fr.layer4.payment4j.ExceptionResolver;
import fr.layer4.payment4j.InvalidCreditCardNumberException;

public class LitleExceptionResolver implements ExceptionResolver {

	public Throwable resolve(Throwable throwable) {

		if (throwable instanceof LitleOnlineException) {
			if (throwable.getMessage().contains("ccAccountNumberType")) {
				throw new InvalidCreditCardNumberException(
						throwable.getMessage(), throwable);
			}
		}
		return null;
	}
}
