package fr.layer4.payment4j.gateways.braintree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionAddressRequest;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.google.common.collect.Sets;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.CreditCardType;
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

		BraintreeGateway gateway = getGateway();

		com.braintreegateway.Result<Transaction> result = gateway.transaction()
				.sale(request);

		if (result.isSuccess()) {
			Transaction transaction = result.getTarget();
			System.out.println("Success!: " + transaction.getId());
		} else if (result.getTransaction() != null) {
			System.out.println("Message: " + result.getMessage());
			Transaction transaction = result.getTransaction();
			System.out.println("Error processing transaction:");
			System.out.println("  Status: " + transaction.getStatus());
			System.out.println("  Code: "
					+ transaction.getProcessorResponseCode());
			System.out.println("  Text: "
					+ transaction.getProcessorResponseText());
		} else {
			System.out.println("Message: " + result.getMessage());
			for (ValidationError error : result.getErrors()
					.getAllDeepValidationErrors()) {
				System.out.println("Attribute: " + error.getAttribute());
				System.out.println("  Code: " + error.getCode());
				System.out.println("  Message: " + error.getMessage());
			}
		}

		Authorization authorization = new Authorization();
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

	private BraintreeGateway getGateway() {
		BraintreeGateway gateway = new BraintreeGateway(
				this.gateway.isTestingMode() ? Environment.SANDBOX
						: Environment.PRODUCTION, merchantId, publicKey,
				privateKey);
		return gateway;
	}

	public Result doCapture(Authorization authorization) {
		return null;
	}

	public Result doCancel(String transactionId) {
		return null;
	}

	public Result doRefund(Money money, String transactionId) {
		return null;
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTER, CreditCardType.AMERICAN_EXPRESS,
				CreditCardType.DISCOVER, CreditCardType.DINERS_CLUB,
				CreditCardType.JCB));
	}

	public String getHomepageUrl() {
		return "http//www.authorize.net/";
	}

	public String getDisplayName() {
		return "Authorize.Net";
	}

	public CurrencyUnit getDefaultCurrency() {
		return CurrencyUnit.USD;
	}

	public Set<String> getSupportedCountries() {
		return Sets.newHashSet("US", "CA", "GB");
	}

}
