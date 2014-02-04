package fr.layer4.payment4j.gateways.app55;

import org.joda.money.Money;

import com.app55.Environment;
import com.app55.domain.Card;
import com.app55.domain.Transaction;
import com.app55.message.TransactionCreateRequest;
import com.app55.message.TransactionCreateResponse;
import com.google.common.base.Preconditions;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class App55TransactionGateway extends AbstractTransactionGateway {

	private String apiKey;

	private String apiSecret;

	public App55TransactionGateway(Gateway gateway, String apiKey,
			String apiSecret) {
		super(gateway);
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress) {

		Preconditions.checkNotNull(billingAddress,
				"Billing address can not be null");

		com.app55.Gateway gateway = getGateway();

		Transaction transaction = new Transaction();
		transaction.setAmount(money.getAmount().toEngineeringString());
		transaction.setCurrency(money.getCurrencyUnit().getCurrencyCode());
		transaction.setCommit(true);
		transaction.setType("sale");
		transaction.setDescription("Credit "
				+ money.getAmount().toEngineeringString()
				+ money.getCurrencyUnit().getCurrencyCode());

		Card creditCard = convertCreditCard(creditcard, billingAddress);

		TransactionCreateRequest request = gateway.createTransaction(
				creditCard, transaction);
		TransactionCreateResponse response = request.send();
		response.getTransaction().getId();

		Result result = new Result();
		result.setSuccess(true);
		return result;
	}

	@Override
	protected Result doCapture(Authorization authorization) {

		TransactionCreateResponse response = ((TransactionCreateRequest) authorization
				.getUnderlyingAuthorization()).send();
		response.getTransaction().getId();

		Result result = new Result();
		result.setSuccess(true);
		return result;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {

		Preconditions.checkNotNull(order, "Order can not be null");
		Preconditions.checkNotNull(order.getBillingAddress(),
				"Billing address can not be null");

		Transaction transaction = new Transaction();
		transaction.setAmount(money.getAmount().toEngineeringString());
		transaction.setCurrency(money.getCurrencyUnit().getCurrencyCode());
		transaction.setCommit(false);
		transaction.setType("auth");
		transaction.setDescription("Authorize "
				+ money.getAmount().toEngineeringString()
				+ money.getCurrencyUnit().getCurrencyCode());

		TransactionCreateRequest request = getGateway().createTransaction(
				convertCreditCard(creditcard, order.getBillingAddress()),
				transaction);

		Authorization authorization = new Authorization();
		authorization.setUnderlyingAuthorization(request);
		return authorization;
	}

	@Override
	protected Result doCancel(String transactionId) {
		Transaction transaction = new Transaction();
		transaction.setId(transactionId);
		getGateway().cancelTransaction(null, transaction);
		Result result = new Result();
		result.setSuccess(true);
		return result;
	}

	@Override
	protected Result doRefund(Money money, String transactionId) {
		// TODO
		Result result = new Result();
		result.setSuccess(true);
		return result;
	}

	public com.app55.Gateway getGateway() {
		com.app55.Gateway gateway = new com.app55.Gateway(Environment.SANDBOX,
				apiKey, apiSecret);
		return gateway;
	}

	public Card convertCreditCard(CreditCard creditcard, Address billingAddress) {
		Card card = new Card();
		card.setNumber(creditcard.getNumber());
		card.setSecurityCode(creditcard.getVerificationValue());
		card.setHolderName(creditcard.getFullName());
		card.setExpiry(creditcard.getMonth() + "-" + creditcard.getYear());

		com.app55.domain.Address address = new com.app55.domain.Address();
		address.setCity(billingAddress.getCity());
		address.setCountry(billingAddress.getCountry());
		address.setPostalCode(billingAddress.getPostalCode());
		address.setStreet(billingAddress.getStreetAddress());
		card.setAddress(address);
		return card;
	}
}
