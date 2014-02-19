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
		assertFalse(CreditCard.isValid(null));
		assertFalse(CreditCard.isValid(""));
		assertFalse(CreditCard.isValid("123456789012")); // too short
		assertFalse(CreditCard.isValid("12345678901234567890")); // too long
		assertFalse(CreditCard.isValid("4417123456789112"));
		assertFalse(CreditCard.isValid("4417q23456w89113"));
		assertTrue(CreditCard.isValid(VALID_VISA));
		assertTrue(CreditCard.isValid(VALID_SHORT_VISA));
		assertTrue(CreditCard.isValid(VALID_AMEX));
		assertTrue(CreditCard.isValid(VALID_MASTERCARD));
		assertTrue(CreditCard.isValid(VALID_DINERS));
		assertTrue(CreditCard.isValid(VALID_DISCOVER));
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkYear_moreThan1990() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setYear(20);

		// Asserts
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkYear_lessThan2050() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setYear(3000);

		// Asserts
	}

	@Test
	public void checkYear() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setYear(2020);

		// Asserts
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkMonth_moreThan1() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setMonth(0);

		// Asserts
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkMonth_lessThan12() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setMonth(13);

		// Asserts
	}

	@Test
	public void checkMonth() {

		// Arrange
		CreditCard creditCard = new CreditCard();

		// Actions
		creditCard.setMonth(12);

		// Asserts
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

	@Test(expected = IllegalArgumentException.class)
	public void checkExpirationDate_withoutYear() {

		// Arrange
		CreditCard creditCard = new CreditCard();
		creditCard.setMonth(2);

		// Actions
		creditCard.getExpirationAsForCharacter();

		// Asserts
	}
}
