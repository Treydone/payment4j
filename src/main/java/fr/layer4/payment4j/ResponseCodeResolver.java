package fr.layer4.payment4j;

public interface ResponseCodeResolver {

	void resolve(String code, String message, CreditCard creditcard,
			String transactionId);
}
