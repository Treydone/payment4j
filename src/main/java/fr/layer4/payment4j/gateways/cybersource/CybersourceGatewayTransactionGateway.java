package fr.layer4.payment4j.gateways.cybersource;

import java.math.BigInteger;
import java.util.Map;

import org.joda.money.Money;

import com.cybersource.schemas.transaction_data.transactionprocessor.ITransactionProcessor;
import com.cybersource.schemas.transaction_data_1.BillTo;
import com.cybersource.schemas.transaction_data_1.Card;
import com.cybersource.schemas.transaction_data_1.ReplyMessage;
import com.cybersource.schemas.transaction_data_1.RequestMessage;
import com.cybersource.schemas.transaction_data_1.ShipTo;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class CybersourceGatewayTransactionGateway extends
		AbstractTransactionGateway {

	private String username;

	private String transactionKey;

	public CybersourceGatewayTransactionGateway(CybersourceGateway gateway,
			String username, String transactionKey) {
		super(gateway);
		this.username = username;
		this.transactionKey = transactionKey;
	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress, Map<String, Object> options) {
		return null;
	}

	@Override
	protected Result doCapture(Authorization authorization,
			Map<String, Object> options) {
		return null;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order, Map<String, Object> options) {

		ITransactionProcessor processor = CybersourceUtils.build(
				gateway.isTestingMode(), username, transactionKey);

		RequestMessage request = new RequestMessage();
		request.setMerchantID(username);
		if (creditcard != null) {
			request.setCard(convertCreditCard(creditcard));
		}
		if (order != null) {
			request.setBillTo(convertAddressBill(order.getBillingAddress()));
			request.setShipTo(convertAddressShip(order.getShippingAddress()));
		}

		ReplyMessage replyMessage = processor.runTransaction(request);
		Authorization authorization = new Authorization();

		// String message = replyMessage.getDecision();
		// if (!replyMessage.getMissingField().isEmpty()) {
		// message += (" | Missing Fields: " + replyMessage.getMissingField()
		// .toString());
		// }
		// if (!replyMessage.getInvalidField().isEmpty()) {
		// message += (" | Invalid Fields: " + replyMessage.getInvalidField()
		// .toString());
		// }
		// authorization.setMessage(message);
		// authorization.setCode(replyMessage.getReasonCode().intValue());
		authorization.setTransactionId(replyMessage.getRequestID());
		// authorization.setSuccessful(100 ==
		// replyMessage.getReasonCode().intValue());
		return authorization;
	}

	private ShipTo convertAddressShip(Address shippingAddress) {
		return null;
	}

	private BillTo convertAddressBill(Address billingAddress) {
		return null;
	}

	private Card convertCreditCard(CreditCard creditcard) {
		Card card = new Card();
		card.setCvNumber(creditcard.getNumber());
		card.setExpirationMonth(BigInteger.valueOf(creditcard.getMonth()));
		card.setExpirationYear(BigInteger.valueOf(creditcard.getYear()));
		card.setPin(creditcard.getVerificationValue());
		return card;
	}

	@Override
	protected Result doCancel(String transactionId, Map<String, Object> options) {
		return null;
	}

	@Override
	protected Result doRefund(Money money, String transactionId,
			Map<String, Object> options) {
		return null;
	}
}
