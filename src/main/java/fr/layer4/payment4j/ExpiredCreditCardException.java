package fr.layer4.payment4j;

public class ExpiredCreditCardException extends CreditCardException {

	public ExpiredCreditCardException(CreditCard creditCard) {
		super(creditCard, "Credit card "
				+ (creditCard != null ? creditCard.toString() : "")
				+ " has expired.");
	}
	
	public ExpiredCreditCardException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
