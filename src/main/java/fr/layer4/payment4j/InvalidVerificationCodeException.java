package fr.layer4.payment4j;

public class InvalidVerificationCodeException extends CreditCardException {

	public InvalidVerificationCodeException(CreditCard creditCard) {
		super(creditCard, "Verification code for card "
				+ (creditCard != null ? creditCard.toString() : "")
				+ " is invalid.");
	}

	public InvalidVerificationCodeException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
