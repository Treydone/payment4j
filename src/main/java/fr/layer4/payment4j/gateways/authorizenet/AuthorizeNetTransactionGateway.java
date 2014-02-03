package fr.layer4.payment4j.gateways.authorizenet;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.authorize.Merchant;
import net.authorize.TransactionType;
import net.authorize.aim.Transaction;
import net.authorize.data.OrderItem;
import net.authorize.data.ShippingAddress;
import net.authorize.data.creditcard.CardType;

import org.apache.commons.collections.CollectionUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.google.common.collect.Sets;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.CreditCardType;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Item;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class AuthorizeNetTransactionGateway extends AbstractTransactionGateway {

	private String apiLoginId;
	private String transactionKey;

	protected AuthorizeNetTransactionGateway(Gateway gateway,
			String apiLoginId, String transactionKey) {
		super(gateway);
		this.apiLoginId = apiLoginId;
		this.transactionKey = transactionKey;
	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress) {

		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);

		// create transaction
		Transaction creditTransaction = merchant.createAIMTransaction(
				TransactionType.CREDIT, money.getAmount());
		creditTransaction.setCreditCard(convertCreditCard(creditcard));

		net.authorize.aim.Result authorizeNetResult = (net.authorize.aim.Result) merchant
				.postTransaction(creditTransaction);
		Result result = convertResult(authorizeNetResult);
		return result;
	}

	public Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {

		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);

		// create transaction
		Transaction authCaptureTransaction = merchant.createAIMTransaction(
				TransactionType.AUTH_CAPTURE, money.getAmount());

		if (order != null) {
			net.authorize.data.Order authorizeNetOrder = net.authorize.data.Order
					.createOrder();
			if (CollectionUtils.isNotEmpty(order.getItems())) {
				for (Item item : order.getItems()) {
					OrderItem orderItem = OrderItem.createOrderItem();
					orderItem.setItemDescription(item.getDescription());
					orderItem.setItemName(item.getName());
					orderItem.setItemPrice(item.getPrice());
					orderItem.setItemQuantity(item.getQuantity());
					orderItem.setItemId(item.getId());
					authorizeNetOrder.addOrderItem(orderItem);
				}
			}
			authCaptureTransaction.setOrder(authorizeNetOrder);

			Address address = order.getShippingAddress();
			if (address != null) {
				ShippingAddress shippingAddress = ShippingAddress
						.createShippingAddress();
				shippingAddress.setAddress(address.getStreetAddress());
				shippingAddress.setFirstName(address.getFirstName());
				shippingAddress.setLastName(address.getLastName());
				shippingAddress.setCountry(address.getCountry());
				shippingAddress.setZipPostalCode(address.getPostalCode());
				shippingAddress.setCity(address.getCity());
				shippingAddress.setState(address.getState());
				shippingAddress.setCompany(address.getCompany());
				authCaptureTransaction.setShippingAddress(shippingAddress);
			}
		}

		authCaptureTransaction.setCreditCard(convertCreditCard(creditcard));

		Authorization authorization = new Authorization();
		authorization.setTransactionId(authCaptureTransaction
				.getTransactionId());
		authorization.setAuthorizationCode(authCaptureTransaction
				.getAuthorizationCode());
		authorization.setUnderlyingAuthorization(authCaptureTransaction);

		return authorization;
	}

	public Result doCapture(Authorization authorization) {
		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);
		net.authorize.aim.Result authorizeNetResult = (net.authorize.aim.Result) merchant
				.postTransaction((net.authorize.Transaction) authorization
						.getUnderlyingAuthorization());
		Result result = convertResult(authorizeNetResult);
		return result;
	}

	public Result doCancel(String transactionId) {
		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);

		Transaction voidTransaction = merchant.createAIMTransaction(
				TransactionType.VOID, new BigDecimal(0));
		voidTransaction.setTransactionId(transactionId);

		net.authorize.aim.Result authorizeNetResult = (net.authorize.aim.Result) merchant
				.postTransaction(voidTransaction);

		Result result = convertResult(authorizeNetResult);
		return result;
	}

	public Result doRefund(Money money, String transactionId) {
		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);
		Transaction creditTransaction = merchant.createAIMTransaction(
				TransactionType.CREDIT, money.getAmount());
		creditTransaction.setCreditCard(null);
		net.authorize.aim.Result authorizeNetResult = (net.authorize.aim.Result) merchant
				.postTransaction(creditTransaction);

		Result result = convertResult(authorizeNetResult);
		return result;
	}

	private Result convertResult(
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

	private net.authorize.data.creditcard.CreditCard convertCreditCard(
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
