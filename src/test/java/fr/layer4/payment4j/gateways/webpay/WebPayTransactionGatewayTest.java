package fr.layer4.payment4j.gateways.webpay;

import java.math.BigDecimal;

import org.apache.commons.lang.NotImplementedException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import fr.layer4.payment4j.AbstractTransactionGatewayTest;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.gateways.Configuration;

public class WebPayTransactionGatewayTest extends
		AbstractTransactionGatewayTest {

	@Before
	public void init() {

		money = Money.of(CurrencyUnit.JPY, BigDecimal.valueOf(10000));

		WebPayGateway gateway = WebPayGateway.build(true,
				Configuration.get("webpay.apiKey"));
		this.gateway = gateway;
		transactionGateway = gateway.transactionGateway();

		invalidCredentialsTransactionGateway = WebPayGateway.build(true, "6z")
				.transactionGateway();

		// The expiration date must be set to the present date or later:
		// American Express Test Card 370000000000002
		// Discover Test Card 6011000000000012
		// Visa Test Card 4007000000027
		// Second Visa Test Card 4012888818888
		// JCB 3088000000000017
		// Diners Club/ Carte Blanche 38000000000006

		validCreditCard = new CreditCard().setNumber("4242-4242-4242-4242")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(11).setYear(2014)
				.setVerificationValue("123");
		expiredCreditCard = new CreditCard().setNumber("4242424242424242")
				.setType(CreditCardType.VISA).setFirstName("John")
				.setLastName("Doe").setMonth(12).setYear(2009)
				.setVerificationValue("123");
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
