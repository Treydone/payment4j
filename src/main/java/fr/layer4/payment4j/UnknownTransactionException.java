package fr.layer4.payment4j;

public class UnknownTransactionException extends PaymentException {

	public UnknownTransactionException(String transactionId) {
		super("Transaction " + transactionId + " is unknown.");
	}

	public UnknownTransactionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
