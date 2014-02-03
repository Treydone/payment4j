package fr.layer4.payment4j.gateways.stripe;

import junitparams.Parameters;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.gateways.AbstractTransactionGatewayTest;
import fr.layer4.payment4j.gateways.Configuration;

public class StripeTransactionGatewayTest extends
		AbstractTransactionGatewayTest {

	@Before
	public void data() {
		// https://stripe.com/docs/testing

		validCreditCard = new CreditCard().setNumber("4242424242424242")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2015)
				.setVerificationValue("000");
		expiredCreditCard = new CreditCard().setNumber("4000000000000069")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2009)
				.setVerificationValue("000");
		invalidNumberCreditCard = new CreditCard().setNumber("400700027")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2015)
				.setVerificationValue("000");
		invalidVerificationCodeCreditCard = new CreditCard()
				.setNumber("4242424242424242").setType(CreditCardType.VISA)
				.setFirstName("John").setLastName("Doe").setMonth(12)
				.setYear(2015).setVerificationValue("99");
		incorrectVerificationCodeCreditCard = new CreditCard()
				.setNumber("4000000000000127").setType(CreditCardType.VISA)
				.setFirstName("John").setLastName("Doe").setMonth(12)
				.setYear(2015).setVerificationValue("000");
	}

	public void init() {
		StripeGateway gateway = StripeGateway.build(true,
				Configuration.get("stripe.apiKey"));
		this.gateway = gateway;
		transactionGateway = gateway.transactionGateway();

		invalidCredentialsTransactionGateway = StripeGateway.build(true,
				"invalidKey").transactionGateway();
	}

	@Override
	@Test(expected = NotImplementedException.class)
	@Parameters
	public void credit(String name, CreditCard creditCard,
			Class<? extends Exception> expectedExceptionClass) {
		super.credit(name, creditCard, expectedExceptionClass);
	}
}