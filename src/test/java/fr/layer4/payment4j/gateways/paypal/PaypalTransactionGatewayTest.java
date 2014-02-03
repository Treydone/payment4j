package fr.layer4.payment4j.gateways.paypal;

import org.junit.Before;

import fr.layer4.payment4j.AbstractTransactionGatewayTest;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.gateways.Configuration;

public class PaypalTransactionGatewayTest extends
		AbstractTransactionGatewayTest {

	@Before
	public void init() {
		PaypalGateway gateway = PaypalGateway.build(true,
				Configuration.get("paypal.user"),
				Configuration.get("paypal.vendor"),
				Configuration.get("paypal.partner"),
				Configuration.get("paypal.password"));
		this.gateway = gateway;
		transactionGateway = gateway.transactionGateway();

		invalidCredentialsTransactionGateway = PaypalGateway.build(true, null,
				"vincent.devillers-facilitator_api1.layer4.fr", null, "222")
				.transactionGateway();

		validCreditCard = new CreditCard().setNumber("4963204821164667")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2019)
				.setVerificationValue("000");
		expiredCreditCard = new CreditCard().setNumber("4963204821164667")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2009)
				.setVerificationValue("000");
		invalidNumberCreditCard = new CreditCard().setNumber("400700027")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2015)
				.setVerificationValue("000");
	}
}
