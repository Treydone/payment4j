package fr.layer4.payment4j;

public class TransactionException extends PaymentException {

	private String transactionId;

	private String extraText;

	public TransactionException(String transactionId) {
		super("Transaction " + transactionId + " can not be completed.");
		this.transactionId = transactionId;
	}

	public TransactionException(String transactionId, String extraText) {
		super("Transaction " + transactionId + " can not be completed."
				+ extraText);
		this.transactionId = transactionId;
		this.extraText = extraText;
	}

}
