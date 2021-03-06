package fr.layer4.payment4j.gateways;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.ExpiredCreditCardException;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.IncorrectCreditCardNumberException;
import fr.layer4.payment4j.IncorrectVerificationCodeException;
import fr.layer4.payment4j.InvalidCreditCardNumberException;
import fr.layer4.payment4j.InvalidExpirationDateException;
import fr.layer4.payment4j.InvalidVerificationCodeException;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.TransactionGateway;

@RunWith(JUnitParamsRunner.class)
public abstract class AbstractTransactionGatewayTest extends AbstractTest {

	protected Gateway gateway;

	protected TransactionGateway transactionGateway;

	protected TransactionGateway invalidCredentialsTransactionGateway;

	protected CreditCard validCreditCard;

	protected CreditCard invalidNumberCreditCard;

	protected CreditCard invalidExpirationDateCreditCard;

	protected CreditCard incorrectNumberCreditCard;

	protected CreditCard incorrectVerificationCodeCreditCard;

	protected CreditCard invalidVerificationCodeCreditCard;

	protected CreditCard expiredCreditCard;

	protected Order order;

	protected Address address;

	protected Money money = Money.of(CurrencyUnit.EUR, 10);

	public void prepare() {
		assertNotNull("gateway is null", gateway);
		assertNotNull("transactionGateway is null", transactionGateway);
		assertNotNull("invalidCredentialsTransactionGateway is null",
				invalidCredentialsTransactionGateway);
		assertNotNull("validCreditCard is null", validCreditCard);
		assertNotNull("invalidNumberCreditCard is null",
				invalidNumberCreditCard);
		assertNotNull("invalidExpirationDateCreditCard is null",
				invalidExpirationDateCreditCard);
		assertNotNull("incorrectNumberCreditCard is null",
				incorrectNumberCreditCard);
		assertNotNull("incorrectVerificationCodeCreditCard is null",
				incorrectVerificationCodeCreditCard);
		assertNotNull("invalidVerificationCodeCreditCard is null",
				invalidVerificationCodeCreditCard);
		assertNotNull("expiredCreditCard is null", expiredCreditCard);
		assertNotNull("money is null", money);
	}

	public abstract void data();

	public abstract void init();

	@Before
	public void before() {
		init();
	}

	@Test
	public void testTransactionCapable() {
		assertTrue(gateway.isTransactionCapable());
	}

	protected List<Object[]> parametersForPurchase() {
		return commons();
	}

	@Test
	@Parameters
	public void purchase(String name, CreditCard creditCard,
			Class<? extends Exception> expectedExceptionClass) {

		// Arrange
		// prepare();

		// Actions
		try {
			Result result = transactionGateway.purchase(money, creditCard,
					order);
			System.out.println(result.getMessage());

			// Assert
			if (expectedExceptionClass != null) {
				fail("expected " + expectedExceptionClass.getCanonicalName());
			}
			assertTrue(result.isSuccess());

		} catch (Exception e) {
			catchException(expectedExceptionClass, e);
		}
	}

	protected List<Object[]> parametersForCredit() {
		return commons();
	}

	@Test
	@Parameters
	public void credit(String name, CreditCard creditCard,
			Class<? extends Exception> expectedExceptionClass) {

		// Arrange
		// prepare();

		// Actions
		try {
			Result result = transactionGateway.credit(money, creditCard,
					address);
			System.out.println(result.getMessage());

			// Assert
			if (expectedExceptionClass != null) {
				fail("expected " + expectedExceptionClass.getCanonicalName());
			}
			assertTrue(result.isSuccess());

		} catch (Exception e) {
			catchException(expectedExceptionClass, e);
		}
	}

	protected List<Object[]> parametersForCancel() {
		return commons();
	}

	@Test
	@Parameters
	public void cancel(String name, CreditCard creditCard,
			Class<? extends Exception> expectedExceptionClass) {

		// Arrange
		// prepare();

		// Actions
		try {
			Result purchaseResult = transactionGateway.purchase(money,
					creditCard, order);
			Result result = transactionGateway.cancel(purchaseResult
					.getAuthorization());
			System.out.println(result.getMessage());

			// Assert
			if (expectedExceptionClass != null) {
				fail("expected " + expectedExceptionClass.getCanonicalName());
			}
			assertTrue(result.isSuccess());

		} catch (Exception e) {
			catchException(expectedExceptionClass, e);
		}
	}

	protected List<Object[]> parametersForRefund() {
		return commons();
	}

	@Test
	@Parameters
	public void refund(String name, CreditCard creditCard,
			Class<? extends Exception> expectedExceptionClass) {

		// Arrange
		// prepare();

		// Actions
		try {
			Result purchaseResult = transactionGateway.purchase(money,
					creditCard, order);
			Result result = transactionGateway.refund(money.minus(1),
					purchaseResult.getAuthorization());
			System.out.println(result.getMessage());

			// Assert
			if (expectedExceptionClass != null) {
				fail("expected " + expectedExceptionClass.getCanonicalName());
			}
			assertTrue(result.isSuccess());

		} catch (Exception e) {
			catchException(expectedExceptionClass, e);
		}
	}

	public List<Object[]> commons() {
		data();
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(new Object[] { "a valid card", validCreditCard, null });
		list.add(new Object[] { "an invalid number credit card",
				invalidNumberCreditCard, InvalidCreditCardNumberException.class });
		list.add(new Object[] { "an incorrect number credit card",
				incorrectNumberCreditCard,
				IncorrectCreditCardNumberException.class });
		list.add(new Object[] { "an expired credit card", expiredCreditCard,
				ExpiredCreditCardException.class });
		list.add(new Object[] {
				"a credit card with an invalid expiration date",
				invalidExpirationDateCreditCard,
				InvalidExpirationDateException.class });
		list.add(new Object[] {
				"a credit card with an incorrect verification code",
				incorrectVerificationCodeCreditCard,
				IncorrectVerificationCodeException.class });
		list.add(new Object[] {
				"a credit card with an invalid verification code",
				invalidVerificationCodeCreditCard,
				InvalidVerificationCodeException.class });
		return list;
	}

}
