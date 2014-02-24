package fr.layer4.payment4j.gateways.braintree;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.joda.money.Money;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionAddressRequest;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class BrainTreeTransactionGateway extends AbstractTransactionGateway {

	private String merchantId;
	private String publicKey;
	private String privateKey;

	protected BrainTreeTransactionGateway(Gateway gateway, String merchantId,
			String publicKey, String privateKey) {
		super(gateway);
		this.merchantId = merchantId;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress) {

		TransactionRequest request = new TransactionRequest()
				.amount(money.getAmount())
				// TODO
				// .deviceData(deviceData)
				.creditCard()
				.number(creditcard.getNumber())
				.expirationMonth(Integer.toString(creditcard.getMonth()))
				.expirationYear(Integer.toString(creditcard.getYear()))
				.cardholderName(
						creditcard.getFirstName() + " "
								+ creditcard.getLastName())
				.cvv(creditcard.getVerificationValue()).done();

		BraintreeGateway gateway = BrainTreeUtils.getGateway(this.gateway,
				merchantId, publicKey, privateKey);

		com.braintreegateway.Result<Transaction> credit = gateway.transaction()
				.credit(request);

		Result result = convert(credit);
		return result;
	}

	public Result doCapture(Authorization authorization) {
		BraintreeGateway gateway = BrainTreeUtils.getGateway(this.gateway,
				merchantId, publicKey, privateKey);
		com.braintreegateway.Result<Transaction> submitForSettlement = gateway
				.transaction().submitForSettlement(
						authorization.getTransactionId());
		return convert(submitForSettlement);
	}

	public Result doCancel(String transactionId) {
		BraintreeGateway gateway = BrainTreeUtils.getGateway(this.gateway,
				merchantId, publicKey, privateKey);
		com.braintreegateway.Result<Transaction> voidTransaction = gateway
				.transaction().voidTransaction(transactionId);
		return convert(voidTransaction);
	}

	public Result doRefund(Money money, String transactionId) {
		BraintreeGateway gateway = BrainTreeUtils.getGateway(this.gateway,
				merchantId, publicKey, privateKey);
		com.braintreegateway.Result<Transaction> refund = gateway.transaction()
				.refund(transactionId, money.getAmount());
		Result result = convert(refund);
		return result;
	}

	private Result convert(com.braintreegateway.Result<Transaction> refund) {
		Result result = new Result();
		result.setSuccess(false);
		if (refund.isSuccess()) {
			result.setSuccess(true);
		} else if (refund.getTransaction() != null) {
			Transaction transaction = refund.getTransaction();
			result.setMessage(refund.getMessage() + ". "
					+ transaction.getProcessorResponseText());
			result.setResponseCode(transaction.getProcessorResponseCode());
		} else {
			result.setMessage(refund.getMessage());
			result.setResponseCode(StringUtils.join(Collections2.transform(
					refund.getErrors().getAllDeepValidationErrors(),
					new Function<ValidationError, String>() {
						@Nullable
						public String apply(@Nullable ValidationError input) {
							return input.getCode().code;
						}
					})));
		}
		return result;
	}

	public Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {

		TransactionRequest request = new TransactionRequest()
				.amount(money.getAmount())
				// TODO
				// .deviceData(deviceData)
				.creditCard()
				.number(creditcard.getNumber())
				.expirationMonth(Integer.toString(creditcard.getMonth()))
				.expirationYear(Integer.toString(creditcard.getYear()))
				.cardholderName(
						creditcard.getFirstName() + " "
								+ creditcard.getLastName())
				.cvv(creditcard.getVerificationValue()).done();

		if (order != null) {
			if (order.getBillingAddress() != null) {
				setAddress(order.getBillingAddress(), request.billingAddress());
			}
			if (order.getShippingAddress() != null) {
				setAddress(order.getShippingAddress(),
						request.shippingAddress());
			}
		}

		BraintreeGateway gateway = BrainTreeUtils.getGateway(this.gateway,
				merchantId, publicKey, privateKey);

		com.braintreegateway.Result<Transaction> brainTreeResult = gateway
				.transaction().sale(request);

		Authorization authorization = new Authorization();
		if (brainTreeResult.isSuccess()) {
			Transaction transaction = brainTreeResult.getTarget();
			authorization.setTransactionId(transaction.getId());
		}
		authorization.setAuthorizationCode(StringUtils.join(Collections2
				.transform(brainTreeResult.getErrors()
						.getAllDeepValidationErrors(),
						new Function<ValidationError, String>() {
							@Nullable
							public String apply(@Nullable ValidationError input) {
								return input.getCode().code;
							}
						})));

		return authorization;
	}

	private void setAddress(Address address,
			TransactionAddressRequest addressRequest) {
		addressRequest.countryName(address.getCountry())
				.streetAddress(address.getStreetAddress())
				.firstName(address.getFirstName())
				.lastName(address.getLastName())
				.postalCode(address.getPostalCode()).done();
	}

}
