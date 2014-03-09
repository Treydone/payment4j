package fr.layer4.payment4j.gateways.balanced;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.joda.money.Money;

import com.balancedpayments.Balanced;
import com.balancedpayments.Card;
import com.balancedpayments.Credit;
import com.balancedpayments.Debit;
import com.balancedpayments.Hold;
import com.balancedpayments.errors.HTTPError;
import com.google.common.base.Throwables;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class BalancedTransactionGateway extends AbstractTransactionGateway {

	private String apiKey;

	private String apiSecret;

	public BalancedTransactionGateway(Gateway gateway, String apiKey,
			String apiSecret) {
		super(gateway);
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;

	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress, Map<String, Object> options) {
		Balanced.configure(apiKey);
		Credit credit = new Credit();
		credit.amount = money.getAmount().multiply(BigDecimal.valueOf(100))
				.intValue();
		credit.created_at = new Date();

		try {
			credit.save();
		} catch (HTTPError e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Result doCapture(Authorization authorization,
			Map<String, Object> options) {
		try {
			((Debit) authorization.getUnderlyingAuthorization()).save();
		} catch (HTTPError e) {
			throw Throwables.propagate(e);
		}
		return null;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order, Map<String, Object> options) {

		Hold hold = new Hold();
		hold.amount = money.getAmount().multiply(BigDecimal.valueOf(100))
				.intValue();

		Card card = new Card();
		card.card_number = creditcard.getNumber();
		card.expiration_month = creditcard.getMonth();
		card.expiration_year = creditcard.getYear();
		card.security_code = creditcard.getVerificationValue();
		hold.card = card;

		Authorization authorization;
		try {
			Debit debit = hold.capture();
			authorization = new Authorization();
			authorization.setTransactionId(debit.transaction_number);
			authorization.setUnderlyingAuthorization(debit);
		} catch (HTTPError e) {
			throw Throwables.propagate(e);
		}
		return authorization;
	}

	@Override
	protected Result doCancel(String transactionId, Map<String, Object> options) {
		Hold hold = new Hold();
		hold.transaction_number = transactionId;
		try {
			hold.delete();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
		return null;
	}

	@Override
	protected Result doRefund(Money money, String transactionId,
			Map<String, Object> options) {
		return null;
	}
}
