package fr.layer4.payment4j.gateways;

import com.google.common.base.Throwables;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.ExceptionResolver;
import fr.layer4.payment4j.ResponseCodeResolver;
import fr.layer4.payment4j.Result;

public abstract class GatewayUtils {

	public static RuntimeException resolveException(ExceptionResolver resolver,
			Throwable throwable) {

		Throwable toBeThrown = throwable;
		if (resolver != null) {
			Throwable resolved = resolver.resolve(throwable);
			if (resolved != null) {
				toBeThrown = resolved;
			}
		}
		if (toBeThrown instanceof RuntimeException) {
			throw (RuntimeException) toBeThrown;
		} else {
			throw Throwables.propagate(toBeThrown);
		}
	}

	public static void resoleCode(ResponseCodeResolver resolver, Result result,
			CreditCard creditcard, String transactionId) {
		if (resolver != null) {
			resolver.resolve(result.getResponseCode(), result.getMessage(),
					creditcard, transactionId);
		}
	}
}
