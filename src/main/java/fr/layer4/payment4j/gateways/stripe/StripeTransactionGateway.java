package fr.layer4.payment4j.gateways.stripe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.joda.money.Money;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class StripeTransactionGateway extends AbstractTransactionGateway {

	private String apiKey;

	public StripeTransactionGateway(Gateway gateway, String apiKey) {
		super(gateway);
		this.apiKey = apiKey;
	}

	@Override
	protected Result doCapture(Authorization authorization) {
		Result result = new Result();
		try {
			Charge charge = (Charge) authorization.getUnderlyingAuthorization();
			charge.capture(apiKey);
			result.setMessage(charge.getFailureMessage());
			result.setResponseCode(charge.getFailureCode());
		} catch (StripeException e) {
			throw Throwables.propagate(e);
		}
		result.setSuccess(true);
		return result;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {
		Map<String, Object> defaultChargeParams = new HashMap<String, Object>();
		defaultChargeParams.put("amount",
				(money.getAmount().multiply(new BigDecimal(100))).longValue());
		defaultChargeParams.put("currency", money.getCurrencyUnit()
				.getCurrencyCode());

		Map<String, Object> defaultCardParams = new HashMap<String, Object>();
		defaultCardParams.put("number", creditcard.getNumber());
		defaultCardParams.put("exp_month", creditcard.getMonth());
		defaultCardParams.put("exp_year", creditcard.getYear());
		defaultCardParams.put("cvc", creditcard.getVerificationValue());

		if (order != null && order.getShippingAddress() != null) {
			Address address = order.getShippingAddress();
			defaultCardParams.put("name", address.getFullName());
			defaultCardParams.put("address_line1", address.getStreetAddress());
			defaultCardParams.put("address_city", address.getCity());
			defaultCardParams.put("address_zip", address.getPostalCode());
			defaultCardParams.put("address_state", address.getState());
			// TODO
			defaultCardParams.put("address_country", address.getCountry());
		}

		defaultChargeParams.put("card", defaultCardParams);
		defaultChargeParams.put("capture", false);

		Authorization authorization = new Authorization();
		try {
			Charge charge = Charge.create(defaultChargeParams, apiKey);
			authorization.setTransactionId(charge.getId());
			authorization.setUnderlyingAuthorization(charge);
		} catch (StripeException e) {
			throw Throwables.propagate(e);
		}
		return authorization;
	}

	@Override
	protected Result doCancel(String transactionId) {
		Result result = new Result();
		try {
			Charge charge = Charge.retrieve(transactionId, apiKey);
			charge.refund(apiKey);
		} catch (StripeException e) {
			throw Throwables.propagate(e);
		}
		result.setSuccess(true);
		return result;
	}

	@Override
	protected Result doRefund(Money money, String transactionId) {
		Result result = new Result();
		try {
			Charge charge = Charge.retrieve(transactionId, apiKey);
			Map<String, Object> params = Maps.newHashMap();
			params.put("amount", money.getAmount().doubleValue());
			charge.refund(params, apiKey);
		} catch (StripeException e) {
			throw Throwables.propagate(e);
		}
		result.setSuccess(true);
		return result;
	}
}
