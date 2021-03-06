package fr.layer4.payment4j.gateways;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.apache.commons.lang.NotImplementedException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Throwables;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.ExpiredCreditCardException;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.IncorrectCreditCardNumberException;
import fr.layer4.payment4j.IncorrectVerificationCodeException;
import fr.layer4.payment4j.InvalidCreditCardNumberException;
import fr.layer4.payment4j.InvalidVerificationCodeException;
import fr.layer4.payment4j.RecurringGateway;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.Schedule;
import fr.layer4.payment4j.ScheduleUnit;

@RunWith(JUnitParamsRunner.class)
public abstract class AbstractRecurringGatewayTest extends AbstractTest {

	protected Gateway gateway;

	protected RecurringGateway recurringGateway;

	protected RecurringGateway invalidCredentialsRecurringGateway;

	protected CreditCard validCreditCard;

	protected CreditCard invalidNumberCreditCard;

	protected CreditCard invalidExpirationDateCreditCard;

	protected CreditCard incorrectNumberCreditCard;

	protected CreditCard incorrectVerificationCodeCreditCard;

	protected CreditCard invalidVerificationCodeCreditCard;

	protected CreditCard expiredCreditCard;

	protected Money money = Money.of(CurrencyUnit.EUR, 10);

	// Each month during three months
	protected Schedule schedule = new Schedule().setEach(ScheduleUnit.MONTH)
			.setTotalOccurences(3).setInterval(1);

	public void prepare() {
		assertNotNull("gateway is null", gateway);
		assertNotNull("transactionGateway is null", recurringGateway);
		assertNotNull("invalidCredentialsTransactionGateway is null",
				invalidCredentialsRecurringGateway);
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
		assertTrue(gateway.isRecurringCapable());
	}

	protected List<Object[]> parametersForSchedule() {
		return commons();
	}

	@Test
	@Parameters
	public void schedule(String name, CreditCard creditCard,
			Class<? extends Exception> expectedExceptionClass) {

		// Arrange
		// prepare();

		// Actions
		try {
			Result result = recurringGateway.recurring(money, creditCard,
					schedule);
			assertTrue(result.isSuccess());
			// Assert
			if (expectedExceptionClass != null) {
				fail("expected " + expectedExceptionClass.getCanonicalName());
			}

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
			Result result = recurringGateway.recurring(money, creditCard,
					schedule);
			assertTrue(result.isSuccess());
			recurringGateway.cancel(result.getRecurringRef());

			// Assert
			if (expectedExceptionClass != null) {
				fail("expected " + expectedExceptionClass.getCanonicalName());
			}

		} catch (Exception e) {
			catchException(expectedExceptionClass, e);
		}
	}

	public void catchException(
			Class<? extends Exception> expectedExceptionClass, Exception e) {
		if (e instanceof NotImplementedException) {
			throw (NotImplementedException) e;
		}
		if (expectedExceptionClass != null) {
			if (!e.getClass().getCanonicalName()
					.equals(expectedExceptionClass.getCanonicalName())) {
				fail("expected a " + expectedExceptionClass.getCanonicalName()
						+ " get a " + e.getClass().getCanonicalName() + ": "
						+ Throwables.getStackTraceAsString(e));
			} else {
				// Nice!
			}
		} else {
			fail("get a " + e.getClass().getCanonicalName() + ": "
					+ Throwables.getStackTraceAsString(e));
		}
	}

	private List<Object[]> commons() {
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
				"a credit card with an invalid verification code",
				invalidVerificationCodeCreditCard,
				InvalidVerificationCodeException.class });
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
