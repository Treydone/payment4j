package fr.layer4.payment4j.gateways.stripe;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;

import fr.layer4.payment4j.AbstractTransactionGatewayTest;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.gateways.Configuration;

public class StripeTransactionGatewayTest extends
		AbstractTransactionGatewayTest {

	@Before
	public void init() {
		StripeGateway gateway = StripeGateway.build(true,
				Configuration.get("stripe.apiKey"));
		this.gateway = gateway;
		transactionGateway = gateway.transactionGateway();

		invalidCredentialsTransactionGateway = StripeGateway.build(true,
				"invalidKey").transactionGateway();

		validCreditCard = new CreditCard().setNumber("4242424242424242")
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

	@Test(expected = NotImplementedException.class)
	public void testSuccessCredit_validCard() {
		super.testSuccessCredit_validCard();
	}

	@Test(expected = NotImplementedException.class)
	public void testUnsuccessfCredit_invalidCard() {
		super.testUnsuccessfCredit_invalidCard();
	}

	@Test(expected = NotImplementedException.class)
	public void testUnsuccessCredit_expiredCard() {
		super.testUnsuccessCredit_expiredCard();
	}
}