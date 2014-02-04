package fr.layer4.payment4j.gateways.balanced;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.layer4.payment4j.ExceptionResolver;

public class BalancedExceptionResolver implements ExceptionResolver {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BalancedExceptionResolver.class);

	public Throwable resolve(Throwable throwable) {

		return null;
	}
}
