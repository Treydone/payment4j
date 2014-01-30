package fr.layer4.payment4j;

public class AuthenticationException extends PaymentException {

	public AuthenticationException(String message) {
		super(message);
	}
	
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
