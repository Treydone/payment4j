package fr.layer4.payment4j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;

public class AbstractTransactionGatewayTest {

	protected TransactionGateway gateway;

	protected TransactionGateway invalidCredentialsGateway;

	protected CreditCard validCreditCard;

	protected CreditCard invalidNumberCreditCard;

	protected CreditCard expiredCreditCard;

	protected Money money = Money.of(CurrencyUnit.EUR, 10);

	public void prepare() {
		assertNotNull("gateway is null", gateway);
		assertNotNull("invalidCredentialsGateway is null",
				invalidCredentialsGateway);
		assertNotNull("validCreditCard is null", validCreditCard);
		assertNotNull("invalidCreditCard is null", invalidNumberCreditCard);
		assertNotNull("expiredCreditCard is null", expiredCreditCard);
		assertNotNull("money is null", money);
	}

	@Test
	public void testSuccessfullPurchase_validCard() {

		// Arrange
		prepare();

		// Actions
		Result result = gateway.purchase(money, validCreditCard);
		System.out.println(result.getMessage());

		// Assert
		assertTrue(result.isSuccess());

	}

	@Test(expected = IncorrectCreditCardNumberException.class)
	public void testUnsuccessfullPurchase_invalidCard() {

		// Arrange
		prepare();

		// Actions
		gateway.purchase(money, invalidNumberCreditCard);

		// Assert

	}

	@Test(expected = AuthenticationException.class)
	public void testUnsuccessfullPurchase_invalidCredentials() {

		// Arrange
		prepare();

		// Actions
		invalidCredentialsGateway.purchase(money, validCreditCard);

		// Assert

	}

	@Test(expected = ExpiredCreditCardException.class)
	public void testUnsuccessfullPurchase_expiredCard() {

		// Arrange
		prepare();

		// Actions
		gateway.purchase(money, expiredCreditCard);

		// Assert

	}

	@Test(expected = UnknownTransactionException.class)
	public void testCancel_unknownTransaction() {

		// Arrange
		prepare();

		// Actions
		gateway.cancel("00000");

		// Assert

	}

	@Test
	public void testCancel_validCard() {

		// Arrange
		prepare();

		// Actions
		Authorization authorization = gateway.authorize(money, validCreditCard);
		// gateway.capture(authorization);
		assertNotNull(authorization.getTransactionId());
		Result result = gateway.cancel(authorization.getTransactionId());
		System.out.println(result.getMessage());

		// Assert
		assertTrue(result.isSuccess());

	}
}
