package fr.layer4.payment4j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;

public class AbstractTransactionGatewayTest {

	protected Gateway gateway;

	protected TransactionGateway transactionGateway;

	protected TransactionGateway invalidCredentialsTransactionGateway;

	protected CreditCard validCreditCard;

	protected CreditCard invalidNumberCreditCard;

	protected CreditCard expiredCreditCard;

	protected Money money = Money.of(CurrencyUnit.EUR, 10);

	public void prepare() {
		assertNotNull("gateway is null", gateway);
		assertNotNull("transactionGateway is null", transactionGateway);
		assertNotNull("invalidCredentialsTransactionGateway is null",
				invalidCredentialsTransactionGateway);
		assertNotNull("validCreditCard is null", validCreditCard);
		assertNotNull("invalidCreditCard is null", invalidNumberCreditCard);
		assertNotNull("expiredCreditCard is null", expiredCreditCard);
		assertNotNull("money is null", money);
	}

	@Test
	public void testTransactionCapable() {
		assertTrue(gateway.isTransactionCapable());
	}

	@Test
	public void testSuccessfullPurchase_validCard() {

		// Arrange
		prepare();

		// Actions
		Result result = transactionGateway.purchase(money, validCreditCard);
		System.out.println(result.getMessage());

		// Assert
		assertTrue(result.isSuccess());

	}

	@Test(expected = IncorrectCreditCardNumberException.class)
	public void testUnsuccessFullPurchase_invalidCard() {

		// Arrange
		prepare();

		// Actions
		transactionGateway.purchase(money, invalidNumberCreditCard);

		// Assert

	}

	@Test(expected = AuthenticationException.class)
	public void testUnsuccessFullPurchase_invalidCredentials() {

		// Arrange
		prepare();

		// Actions
		invalidCredentialsTransactionGateway.purchase(money, validCreditCard);

		// Assert

	}

	@Test(expected = ExpiredCreditCardException.class)
	public void testUnsuccessfullPurchase_expiredCard() {

		// Arrange
		prepare();

		// Actions
		transactionGateway.purchase(money, expiredCreditCard);

		// Assert

	}

	@Test(expected = UnknownTransactionException.class)
	public void testUnsuccessCancel_unknownTransaction() {

		// Arrange
		prepare();

		// Actions
		transactionGateway.cancel("00000");

		// Assert

	}

	@Test(expected = ExpiredCreditCardException.class)
	public void testUnsuccessCancel_expiredCard() {

		// Arrange
		prepare();

		// Actions
		transactionGateway.authorize(money, expiredCreditCard);

		// Assert

	}

	@Test
	public void testSuccessCancel_validCard() {

		// Arrange
		prepare();

		// Actions
		Authorization authorization = transactionGateway.authorize(money,
				validCreditCard);
		// gateway.capture(authorization);
		assertNotNull(authorization.getTransactionId());
		Result result = transactionGateway.cancel(authorization
				.getTransactionId());
		System.out.println(result.getMessage());

		// Assert
		assertTrue(result.isSuccess());

	}

	@Test
	public void testSuccessCredit_validCard() {

		// Arrange
		prepare();

		// Actions
		Result result = transactionGateway.credit(money, validCreditCard);
		System.out.println(result.getMessage());

		// Assert
		assertTrue(result.isSuccess());

	}

	@Test(expected = IncorrectCreditCardNumberException.class)
	public void testUnsuccessfCredit_invalidCard() {

		// Arrange
		prepare();

		// Actions
		transactionGateway.credit(money, invalidNumberCreditCard);

		// Assert

	}

	@Test(expected = ExpiredCreditCardException.class)
	public void testUnsuccessCredit_expiredCard() {

		// Arrange
		prepare();

		// Actions
		transactionGateway.credit(money, expiredCreditCard);

		// Assert

	}
}
