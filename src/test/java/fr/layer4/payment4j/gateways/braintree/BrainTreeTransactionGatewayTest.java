package fr.layer4.payment4j.gateways.braintree;

import org.junit.Before;

import fr.layer4.payment4j.AbstractTransactionGatewayTest;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.gateways.Configuration;
import fr.layer4.payment4j.gateways.authorizenet.AuthorizeNetGateway;

public class BrainTreeTransactionGatewayTest extends
		AbstractTransactionGatewayTest {

	@Override
	public void init() {
		BrainTreeGateway gateway = BrainTreeGateway.build(true,
				Configuration.get("braintree.merchantId"),
				Configuration.get("braintree.publicKey"),
				Configuration.get("braintree.privateKey"));
		this.gateway = gateway;
		transactionGateway = gateway.transactionGateway();

		invalidCredentialsTransactionGateway = BrainTreeGateway.build(true,
				"6z", "64w", "").transactionGateway();
	}

	@Override
	public void data() {

		// The expiration date must be set to the present date or later:
		// American Express Test Card 370000000000002
		// Discover Test Card 6011000000000012
		// Visa Test Card 4007000000027
		// Second Visa Test Card 4012888818888
		// JCB 3088000000000017
		// Diners Club/ Carte Blanche 38000000000006

		validCreditCard = new CreditCard().setNumber("4007000000027")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2015)
				.setVerificationValue("000");
		expiredCreditCard = new CreditCard().setNumber("4007000000027")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2009)
				.setVerificationValue("000");
		invalidNumberCreditCard = new CreditCard().setNumber("400700027")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2015)
				.setVerificationValue("000");
	}
}
