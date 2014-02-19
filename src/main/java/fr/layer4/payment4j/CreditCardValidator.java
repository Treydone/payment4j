package fr.layer4.payment4j;

import com.google.common.base.Optional;

/**
 * CreditCardType implementations define how validation is performed for one
 * type/brand of credit card.
 */
public interface CreditCardValidator {

	/**
	 * Returns true if the card number matches this type of credit card. Note
	 * that this method is <strong>not</strong> responsible for analyzing the
	 * general form of the card number because <code>CreditCardValidator</code>
	 * performs those checks before calling this method. It is generally only
	 * required to valid the length and prefix of the number to determine if
	 * it's the correct type.
	 * 
	 * @param card
	 *            The card number, never null.
	 * @return true if the number matches.
	 */
	Optional<Boolean> matches(String card);

}
