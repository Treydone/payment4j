package fr.layer4.payment4j;

public class IncorrectVerificationCodeException extends CreditCardException {

	public IncorrectVerificationCodeException(CreditCard creditCard) {
		super(creditCard, "Verification code for card "
				+ (creditCard != null ? creditCard.toString() : "")
				+ " is incorrect.");
	}

	public IncorrectVerificationCodeException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
