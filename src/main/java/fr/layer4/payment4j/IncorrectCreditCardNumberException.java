package fr.layer4.payment4j;

public class IncorrectCreditCardNumberException extends CreditCardException {

	public IncorrectCreditCardNumberException(CreditCard creditCard) {
		super(creditCard, "Credit card number "
				+ (creditCard != null ? creditCard.toString() : "")
				+ " is invalid.");
	}

	public IncorrectCreditCardNumberException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
