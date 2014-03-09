package fr.layer4.payment4j.gateways.authorizenet;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.authorize.Merchant;
import net.authorize.TransactionType;
import net.authorize.aim.Transaction;
import net.authorize.data.OrderItem;
import net.authorize.data.ShippingAddress;

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
			Address billingAddress, Map<String, Object> options) {

		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);

		// create transaction
		Transaction creditTransaction = merchant.createAIMTransaction(
				TransactionType.CREDIT, money.getAmount());
		creditTransaction.setCreditCard(AuthorizeNetUtils
				.convertCreditCard(creditcard));

		net.authorize.aim.Result<Transaction> authorizeNetResult = (net.authorize.aim.Result<Transaction>) merchant
				.postTransaction(creditTransaction);
		Result result = AuthorizeNetUtils.convertResult(authorizeNetResult);
		return result;
	}

	public Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order, Map<String, Object> options) {

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
					orderItem.setItemPrice(item.getPrice().getAmount());
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
				if (address.getCountry() != null)
					shippingAddress.setCountry(address.getCountry().getName());
				shippingAddress.setZipPostalCode(address.getPostalCode());
				shippingAddress.setCity(address.getCity());
				if (address.getState() != null)
					shippingAddress.setState(address.getState().getName());
				shippingAddress.setCompany(address.getCompany());
				authCaptureTransaction.setShippingAddress(shippingAddress);
			}
		}

		authCaptureTransaction.setCreditCard(AuthorizeNetUtils
				.convertCreditCard(creditcard));

		Authorization authorization = new Authorization();
		authorization.setTransactionId(authCaptureTransaction
				.getTransactionId());
		authorization.setAuthorizationCode(authCaptureTransaction
				.getAuthorizationCode());
		authorization.setUnderlyingAuthorization(authCaptureTransaction);

		return authorization;
	}

	public Result doCapture(Authorization authorization,
			Map<String, Object> options) {
		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);
		net.authorize.aim.Result<Transaction> authorizeNetResult = (net.authorize.aim.Result<Transaction>) merchant
				.postTransaction((net.authorize.Transaction) authorization
						.getUnderlyingAuthorization());
		Result result = AuthorizeNetUtils.convertResult(authorizeNetResult);
		return result;
	}

	public Result doCancel(String transactionId, Map<String, Object> options) {
		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);

		Transaction voidTransaction = merchant.createAIMTransaction(
				TransactionType.VOID, new BigDecimal(0));
		voidTransaction.setTransactionId(transactionId);

		net.authorize.aim.Result<Transaction> authorizeNetResult = (net.authorize.aim.Result<Transaction>) merchant
				.postTransaction(voidTransaction);

		Result result = AuthorizeNetUtils.convertResult(authorizeNetResult);
		return result;
	}

	public Result doRefund(Money money, String transactionId,
			Map<String, Object> options) {
		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);
		Transaction creditTransaction = merchant.createAIMTransaction(
				TransactionType.CREDIT, money.getAmount());
		creditTransaction.setCreditCard(null);
		net.authorize.aim.Result<Transaction> authorizeNetResult = (net.authorize.aim.Result<Transaction>) merchant
				.postTransaction(creditTransaction);

		Result result = AuthorizeNetUtils.convertResult(authorizeNetResult);
		return result;
	}

	public Set<CreditCardType> getSupportedCreditCardTypes() {
		return new HashSet<CreditCardType>(Arrays.asList(CreditCardType.VISA,
				CreditCardType.MASTERCARD, CreditCardType.AMERICAN_EXPRESS,
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
