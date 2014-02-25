package fr.layer4.payment4j.gateways.paymill;

import com.paymill.models.Payment;
import com.paymill.models.Payment.CardType;
import com.paymill.models.Payment.Type;

import fr.layer4.payment4j.CreditCard;

public abstract class PaymillUtils {

	public static Payment convertCreditCard(CreditCard creditcard) {
		Payment payment = new Payment();
		switch (creditcard.getType()) {
		case AMERICAN_EXPRESS:
			payment.setCardType(CardType.AMEX);
			break;
		case MASTERCARD:
			payment.setCardType(CardType.MASTERCARD);
			break;
		case DINERS_CLUB:
			payment.setCardType(CardType.DINERS);
			break;
		case JCB:
			payment.setCardType(CardType.JCB);
			break;
		case DISCOVER:
			payment.setCardType(CardType.DISCOVER);
			break;
		case MAESTRO:
			payment.setCardType(CardType.MASTRO);
			break;
		case VISA:
			payment.setCardType(CardType.VISA);
			break;
		default:
			payment.setCardType(CardType.UNKNOWN);
			break;
		}
		payment.setExpireMonth(creditcard.getMonth());
		payment.setExpireYear(creditcard.getYear());
		payment.setType(Type.CREDITCARD);
		return payment;
	}
}
