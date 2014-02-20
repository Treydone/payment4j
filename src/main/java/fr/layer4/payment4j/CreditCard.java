package fr.layer4.payment4j;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Optional;

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
		return StringUtils.leftPad(String.valueOf(getMonth()), 2, "0")
				+ StringUtils.leftPad(String.valueOf(getYear()).substring(2),
						2, "0");
	}

	public int getMonth() {
		return month;
	}

	public CreditCard setMonth(int month) {
		this.month = month;
		return this;
	}

	public int getYear() {
		return year;
	}

	public CreditCard setYear(int year) {
		this.year = year;
		return this;
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

	public void check() {
		if (!checkNumberAndType()) {
			throw new InvalidCreditCardNumberException(this);
		}
		if (!checkMonth() || !checkYear()) {
			throw new InvalidExpirationDateException(this);
		}
		if (checkVV()) {
			throw new InvalidVerificationCodeException(this);
		}
	}

	public boolean checkVV() {
		return StringUtils.isBlank(verificationValue);
	}

	public boolean checkNumberAndType() {
		return checkNumberAndType(number, type);
	}

	public static boolean checkNumberAndType(String number,
			CreditCardType optionnalType) {
		if ((number == null) || (number.length() < 13)
				|| (number.length() > 19)) {
			return false;
		}

		if (!luhnCheck(number)) {
			return false;
		}

		if (optionnalType != null) {
			Optional<Boolean> match = optionnalType.matches(number);
			return match.isPresent() && match.get().booleanValue();
		} else {
			// Try to guess the type
			CreditCardType[] types = CreditCardType.values();
			for (CreditCardType type : types) {
				Optional<Boolean> optional = type.matches(number);
				if (!optional.isPresent()) {
					continue;
				}
				if (optional.get()) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean checkMonth() {
		return checkMonth(month);
	}

	public static boolean checkMonth(int month) {
		return (month > 12 || month < 1);
	}

	public boolean checkYear() {
		return checkYear(year);
	}

	public static boolean checkYear(int year) {
		return (year > 2050 || year < 1990);
	}

	/**
	 * Checks for a valid credit card number.
	 * 
	 * @param cardNumber
	 *            Credit Card Number.
	 * @return Whether the card number passes the luhnCheck.
	 */
	protected static boolean luhnCheck(String cardNumber) {
		// number must be validated as 0..9 numeric first!!
		int digits = cardNumber.length();
		int oddOrEven = digits & 1;
		long sum = 0;
		for (int count = 0; count < digits; count++) {
			int digit = 0;
			try {
				digit = Integer.parseInt(cardNumber.charAt(count) + "");
			} catch (NumberFormatException e) {
				return false;
			}

			if (((count & 1) ^ oddOrEven) == 0) { // not
				digit *= 2;
				if (digit > 9) {
					digit -= 9;
				}
			}
			sum += digit;
		}

		return (sum == 0) ? false : (sum % 10 == 0);
	}
}
