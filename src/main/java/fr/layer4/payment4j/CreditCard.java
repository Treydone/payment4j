package fr.layer4.payment4j;

import org.apache.commons.lang.StringUtils;

public class CreditCard {

	private String number;

	private int month;

	private int year;

	private String firstName;

	private String lastName;

	private String verificationValue;

	private CreditCardType type;

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getNumber() {
		return number;
	}

	public CreditCard setNumber(String number) {
		this.number = number;
		return this;
	}

	public String getExpirationAsForCharacter() {
		checkYear(year);
		checkMonth(month);
		return StringUtils.leftPad(String.valueOf(getMonth()), 2, "0")
				+ StringUtils.leftPad(String.valueOf(getYear()).substring(2),
						2, "0");
	}

	public int getMonth() {
		return month;
	}

	public CreditCard setMonth(int month) {
		checkMonth(month);
		this.month = month;
		return this;
	}

	public void checkMonth(int month) {
		if (month > 12 || month < 1) {
			throw new IllegalArgumentException("month invalid");
		}
	}

	public int getYear() {
		return year;
	}

	public CreditCard setYear(int year) {
		checkYear(year);
		this.year = year;
		return this;
	}

	public void checkYear(int year) {
		if (year > 2050 || year < 1990) {
			throw new IllegalArgumentException("year invalid");
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public CreditCard setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public CreditCard setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getVerificationValue() {
		return verificationValue;
	}

	public CreditCard setVerificationValue(String verificationValue) {
		this.verificationValue = verificationValue;
		return this;
	}

	public CreditCardType getType() {
		return type;
	}

	public CreditCard setType(CreditCardType type) {
		this.type = type;
		return this;
	}

}
