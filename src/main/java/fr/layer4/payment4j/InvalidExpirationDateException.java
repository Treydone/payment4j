package fr.layer4.payment4j;

public class InvalidExpirationDateException extends CreditCardException {

	public InvalidExpirationDateException(CreditCard creditCard) {
		super(creditCard, "Expiration date for card "
				+ (creditCard != null ? creditCard.toString() : "")
				+ " is invalid.");
	}

	public InvalidExpirationDateException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
