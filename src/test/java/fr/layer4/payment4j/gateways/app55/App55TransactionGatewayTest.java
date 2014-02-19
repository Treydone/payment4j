package fr.layer4.payment4j.gateways.app55;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.gateways.AbstractTransactionGatewayTest;
import fr.layer4.payment4j.gateways.Configuration;

public class App55TransactionGatewayTest extends AbstractTransactionGatewayTest {

	@Override
	public void init() {
		App55Gateway gateway = App55Gateway.build(true,
				Configuration.get("app55.apiKey"),
				Configuration.get("app55.apiSecret"));
		this.gateway = gateway;
		transactionGateway = gateway.transaction();

		invalidCredentialsTransactionGateway = App55Gateway.build(true, "6",
				"64").transaction();

		address = new Address();
		address.setCity("vernon");
		address.setStreetAddress("rue de bizy");
		address.setPostalCode("27200");
		address.setCountry("GB");
		order = new Order();
		order.setBillingAddress(address);
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
		invalidExpirationDateCreditCard = new CreditCard()
				.setNumber("4007000000027").setType(CreditCardType.VISA)
				.setFirstName("John").setLastName("Doe").setMonth(12)
				.setYear(1990).setVerificationValue("000");
		incorrectNumberCreditCard = new CreditCard().setNumber("4007000000028")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2015)
				.setVerificationValue("000");
		incorrectVerificationCodeCreditCard = new CreditCard()
				.setNumber("4007000000027").setType(CreditCardType.VISA)
				.setFirstName("John").setLastName("Doe").setMonth(12)
				.setYear(2015).setVerificationValue("001");
		expiredCreditCard = new CreditCard().setNumber("4007000000027")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2009)
				.setVerificationValue("000");
		invalidNumberCreditCard = new CreditCard().setNumber("400700027")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2015)
				.setVerificationValue("000");
		invalidVerificationCodeCreditCard = new CreditCard()
				.setNumber("4007000000027").setType(CreditCardType.VISA)
				.setFirstName("John").setLastName("Doe").setMonth(12)
				.setYear(2015).setVerificationValue("00");
	}
}
