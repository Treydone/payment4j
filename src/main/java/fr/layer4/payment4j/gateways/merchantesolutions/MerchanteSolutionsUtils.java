package fr.layer4.payment4j.gateways.merchantesolutions;

import com.mes.sdk.gateway.CcData;

import fr.layer4.payment4j.CreditCard;

public abstract class MerchanteSolutionsUtils {

	public static CcData convertCreditCard(CreditCard creditcard) {
		return new CcData().setCcNum(creditcard.getNumber())
				.setExpDate(creditcard.getYear() + "/" + creditcard.getMonth())
				.setCvv(creditcard.getVerificationValue());
	}
}
