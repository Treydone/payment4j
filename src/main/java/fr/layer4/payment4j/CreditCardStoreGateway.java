package fr.layer4.payment4j;

public interface CreditCardStoreGateway {

	String store(CreditCard creditCard);

	void unstore(String creditCardId);
}
