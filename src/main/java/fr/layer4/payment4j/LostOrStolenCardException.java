package fr.layer4.payment4j;

public class LostOrStolenCardException extends CreditCardException {

	public LostOrStolenCardException(CreditCard creditCard) {
		super(creditCard, "Credit card "
				+ (creditCard != null ? creditCard.toString() : "")
				+ " has been lost or stolen!");
	}

	public LostOrStolenCardException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
