package fr.layer4.payment4j.gateways.authorizenet;

import javax.annotation.Nullable;

import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.aim.Transaction;
import net.authorize.data.creditcard.CardType;
import net.authorize.xml.Message;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Result;

public abstract class AuthorizeNetUtils {

	public static Merchant getMerchant(Gateway gateway, String apiLoginId,
			String transactionKey) {
		Merchant merchant = Merchant.createMerchant(
				gateway.isTestingMode() ? Environment.SANDBOX
						: Environment.PRODUCTION, apiLoginId, transactionKey);
		return merchant;
	}

	public static net.authorize.data.creditcard.CreditCard convertCreditCard(
			CreditCard creditcard) {
		net.authorize.data.creditcard.CreditCard creditCard = net.authorize.data.creditcard.CreditCard
				.createCreditCard();
		creditCard.setCreditCardNumber(creditcard.getNumber());
		creditCard.setExpirationMonth(String.valueOf(creditcard.getMonth()));
		creditCard.setExpirationYear(String.valueOf(creditcard.getYear()));
		creditCard.setCardType(CardType
				.findByValue(creditcard.getType().name()));
		creditCard.setCardCode(creditcard.getVerificationValue());
		return creditCard;
	}

	public static Result convertResult(
			net.authorize.aim.Result<Transaction> authorizeNetResult) {
		Result result = new Result();
		result.setResponseCode(authorizeNetResult.getReasonResponseCode()
				.toString());
		if (authorizeNetResult.isApproved()) {
			result.setSuccess(true);
			result.setMessage("Approved! "
					+ authorizeNetResult.getReasonResponseCode() + " : "
					+ authorizeNetResult.getResponseText());
			result.setAuthorization(authorizeNetResult.getTarget()
					.getTransactionId());
		} else if (authorizeNetResult.isDeclined()) {
			result.setSuccess(false);
			result.setMessage("Declined! "
					+ authorizeNetResult.getReasonResponseCode() + " : "
					+ authorizeNetResult.getResponseText());
		} else {
			result.setSuccess(false);
			result.setMessage("Error! "
					+ authorizeNetResult.getReasonResponseCode() + " : "
					+ authorizeNetResult.getResponseText());
		}
		return result;
	}

	public static Result convertResult(
			net.authorize.arb.Result<Transaction> authorizeNetResult) {
		Result result = new Result();
		result.setResponseCode(authorizeNetResult.getResultCode().toString());

		if (authorizeNetResult.isOk()) {
			result.setSuccess(true);
			result.setMessage("Approved! " + authorizeNetResult.getResultCode()
					+ ":" + join(authorizeNetResult));
			result.setAuthorization(authorizeNetResult.getTarget()
					.getTransactionId());
		} else if (authorizeNetResult.isError()) {
			result.setSuccess(false);
			result.setMessage("Declined! " + authorizeNetResult.getResultCode()
					+ ":" + join(authorizeNetResult));
		} else {
			result.setSuccess(false);
			result.setMessage("Error! " + authorizeNetResult.getResultCode()
					+ ":" + join(authorizeNetResult));
		}
		return result;
	}

	private static String join(
			net.authorize.arb.Result<Transaction> authorizeNetResult) {
		return StringUtils.join(Collections2.transform(
				authorizeNetResult.getMessages(),
				new Function<Message, String>() {
					@Nullable
					public String apply(@Nullable Message input) {
						return input.getText();
					}
				}), ",");
	}
}
