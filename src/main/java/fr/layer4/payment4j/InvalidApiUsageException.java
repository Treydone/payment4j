package fr.layer4.payment4j;

public class InvalidApiUsageException extends PaymentException {

	public InvalidApiUsageException(String message) {
		super(message);
	}

	public InvalidApiUsageException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
