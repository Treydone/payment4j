package fr.layer4.payment4j;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CreditCardTest {

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
