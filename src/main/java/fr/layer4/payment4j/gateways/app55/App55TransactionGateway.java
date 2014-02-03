package fr.layer4.payment4j.gateways.app55;

import org.joda.money.Money;

import com.app55.Environment;
import com.app55.domain.Card;
import com.app55.domain.Transaction;
import com.app55.message.TransactionCreateRequest;
import com.app55.message.TransactionCreateResponse;

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

		com.app55.Gateway gateway = getGateway();

		Transaction transaction = new Transaction();
		transaction.setAmount(money.getAmount().toEngineeringString());
		transaction.setCommit(true);
		transaction.setType("sale");
		transaction
				.setCurrency(money.getCurrencyUnit().getCode().toLowerCase());

		TransactionCreateRequest request = gateway.createTransaction(
				convertCreditCard(creditcard), transaction);
		TransactionCreateResponse response = request.send();
		response.getTransaction().getId();

		return new Result();
	}

	@Override
	protected Result doCapture(Authorization authorization) {

		TransactionCreateResponse response = ((TransactionCreateRequest) authorization
				.getUnderlyingAuthorization()).send();
		response.getTransaction().getId();

		return new Result();
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {

		Transaction transaction = new Transaction();
		transaction.setAmount(money.getAmount().toEngineeringString());
		transaction.setCommit(false);
		transaction.setType("auth");
		transaction
				.setCurrency(money.getCurrencyUnit().getCode().toLowerCase());

		TransactionCreateRequest request = getGateway().createTransaction(
				convertCreditCard(creditcard), transaction);

		Authorization authorization = new Authorization();
		authorization.setUnderlyingAuthorization(request);
		return authorization;
	}

	@Override
	protected Result doCancel(String transactionId) {
		Transaction transaction = new Transaction();
		transaction.setId(transactionId);
		getGateway().cancelTransaction(null, transaction);
		return new Result();
	}

	@Override
	protected Result doRefund(Money money, String transactionId) {
		return new Result();
	}

	public com.app55.Gateway getGateway() {
		com.app55.Gateway gateway = new com.app55.Gateway(
				Environment.DEVELOPMENT, apiKey, apiSecret);
		return gateway;
	}

	public Card convertCreditCard(CreditCard creditcard) {
		Card card = new Card();
		card.setNumber(creditcard.getNumber());
		card.setSecurityCode(creditcard.getVerificationValue());
		card.setHolderName(creditcard.getFullName());
		// TODO
		// card.setExpiry(expiry)
		return card;
	}
}
