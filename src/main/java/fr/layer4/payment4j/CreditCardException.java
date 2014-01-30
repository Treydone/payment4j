package fr.layer4.payment4j;

public class CreditCardException extends PaymentException {

	protected CreditCard creditCard;

	public CreditCardException(CreditCard creditCard) {
		super("Credit card " + creditCard != null ? creditCard.toString() : ""
				+ " is invalid.");
		this.creditCard = creditCard;
	}

	public CreditCardException(CreditCard creditCard, String message) {
		super(message);
		this.creditCard = creditCard;
	}

	public CreditCardException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}
}
