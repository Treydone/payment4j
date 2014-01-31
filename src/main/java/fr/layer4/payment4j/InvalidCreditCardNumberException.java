package fr.layer4.payment4j;

public class InvalidCreditCardNumberException extends CreditCardException {

	public InvalidCreditCardNumberException(CreditCard creditCard) {
		super(creditCard, "Credit card number "
				+ (creditCard != null ? creditCard.toString() : "")
				+ " is invalid.");
	}

	public InvalidCreditCardNumberException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
