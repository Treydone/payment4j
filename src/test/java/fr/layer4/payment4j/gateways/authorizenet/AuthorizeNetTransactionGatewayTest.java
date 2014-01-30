package fr.layer4.payment4j.gateways.authorizenet;

import org.junit.Before;

import fr.layer4.payment4j.AbstractTransactionGatewayTest;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.gateways.Configuration;

public class AuthorizeNetTransactionGatewayTest extends
		AbstractTransactionGatewayTest {

	@Before
	public void init() {
		gateway = AuthorizeNetGateway.build(true,
				Configuration.get("authorizenet.apiLoginId"),
				Configuration.get("authorizenet.transactionKey"))
				.transactionGateway();

		invalidCredentialsGateway = AuthorizeNetGateway.build(true, "6", "64")
				.transactionGateway();

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