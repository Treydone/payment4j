package fr.layer4.payment4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CreditCardTest {

	private static final String VALID_VISA = "4417123456789113";
	private static final String VALID_SHORT_VISA = "4222222222222";
	private static final String VALID_AMEX = "378282246310005";
	private static final String VALID_MASTERCARD = "5105105105105100";
	private static final String VALID_DISCOVER = "6011000990139424";
	private static final String VALID_DINERS = "30569309025904";

	@Test
	public void isValid() {
		assertFalse(CreditCard.checkNumberAndType(null, null));
		assertFalse(CreditCard.checkNumberAndType("", null));
		assertFalse(CreditCard.checkNumberAndType("123456789012", null)); // too
																			// short
		assertFalse(CreditCard.checkNumberAndType("12345678901234567890", null)); // too
																					// long
		assertFalse(CreditCard.checkNumberAndType("4417123456789112", null));
		assertFalse(CreditCard.checkNumberAndType("4417q23456w89113", null));
		assertTrue(CreditCard.checkNumberAndType(VALID_VISA,
				CreditCardType.VISA));
		assertTrue(CreditCard.checkNumberAndType(VALID_SHORT_VISA,
				CreditCardType.VISA));
		assertTrue(CreditCard.checkNumberAndType(VALID_AMEX,
				CreditCardType.AMERICAN_EXPRESS));
		assertTrue(CreditCard.checkNumberAndType(VALID_MASTERCARD,
				CreditCardType.MASTERCARD));
		assertTrue(CreditCard.checkNumberAndType(VALID_DINERS,
				CreditCardType.DINERS_CLUB));
		assertTrue(CreditCard.checkNumberAndType(VALID_DISCOVER,
				CreditCardType.DISCOVER));
	}

	@Test
	public void checkYear_lessThan1990() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setYear(20);

		// Asserts
		assertFalse(creditCard.checkYear());
	}

	@Test
	public void checkYear_moreThan2050() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setYear(3000);

		// Asserts
		assertFalse(creditCard.checkYear());
	}

	@Test
	public void checkYear() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setYear(2020);

		// Asserts
		assertTrue(creditCard.checkYear());
	}

	@Test
	public void checkMonth_lessThan1() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setMonth(0);

		// Asserts
		assertFalse(creditCard.checkMonth());
	}

	@Test
	public void checkMonth_moreThan12() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setMonth(13);

		// Asserts
		assertFalse(creditCard.checkMonth());
	}

	@Test
	public void checkMonth() {

		// Arrange
		CreditCard creditCard = new CreditCard();
		creditCard.setMonth(12);

		// Actions

		// Asserts
		assertTrue(creditCard.checkMonth());
	}

	@Test
	public void checkExpirationDate() {

		// Arrange
		CreditCard creditCard = new CreditCard();
		creditCard.setMonth(2);
		creditCard.setYear(2004);

		// Actions
		String exp = creditCard.getExpirationAsForCharacter();

		// Asserts
		assertEquals("0204", exp);
	}

	@Test(expected = InvalidExpirationDateException.class)
	public void checkExpirationDate_withoutYear() {

		// Arrange
		CreditCard creditCard = new CreditCard();
		creditCard.setMonth(2);

		// Actions
		creditCard.getExpirationAsForCharacter();

		// Asserts
	}
}
