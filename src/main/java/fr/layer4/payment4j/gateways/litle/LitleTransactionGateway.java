package fr.layer4.payment4j.gateways.litle;

import java.util.Properties;

import org.joda.money.Money;

import com.litle.sdk.LitleOnline;
import com.litle.sdk.generate.AuthorizationResponse;
import com.litle.sdk.generate.CaptureResponse;
import com.litle.sdk.generate.CardType;
import com.litle.sdk.generate.Contact;
import com.litle.sdk.generate.Credit;
import com.litle.sdk.generate.CreditResponse;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;
import com.litle.sdk.generate.OrderSourceType;
import com.litle.sdk.generate.Void;
import com.litle.sdk.generate.VoidResponse;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class LitleTransactionGateway extends AbstractTransactionGateway {

	private Properties properties;

	public LitleTransactionGateway(Gateway gateway, Properties properties) {
		super(gateway);
		this.properties = properties;
	}

	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress) {

		Credit credit = new Credit();
		CardType card = convertCreditcard(creditcard);
		credit.setCard(card);

		credit.setAmount(money.getAmount().longValue());
		credit.setOrderSource(OrderSourceType.ECOMMERCE);

		CreditResponse response = getLitle().credit(credit);

		return null;
	}

	public Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {
		com.litle.sdk.generate.Authorization auth = new com.litle.sdk.generate.Authorization();
		auth.setOrderId("1");
		auth.setAmount(money.getAmount().longValue());
		auth.setOrderSource(OrderSourceType.ECOMMERCE);
		auth.setReportGroup("test");

		if (order != null) {
			if (order.getBillingAddress() != null) {
				auth.setBillToAddress(setAddress(auth,
						order.getBillingAddress()));
			}
			if (order.getShippingAddress() != null) {
				auth.setShipToAddress(setAddress(auth,
						order.getShippingAddress()));
			}
		}

		CardType card = convertCreditcard(creditcard);
		auth.setCard(card);

		AuthorizationResponse response = getLitle().authorize(auth);
		// Display Results
		System.out.println("Response: " + response.getResponse());
		System.out.println("Message: " + response.getMessage());
		System.out.println("Litle Transaction ID: " + response.getLitleTxnId());

		Authorization authorization = new Authorization();
		authorization
				.setTransactionId(String.valueOf(response.getLitleTxnId()));
		authorization.setUnderlyingAuthorization(response);

		Result result = new Result();
		result.setSuccess("Approved".equals(response.getMessage()));
		result.setMessage(response.getMessage());

		return authorization;
	}

	public Result doCapture(Authorization authorization) {
		com.litle.sdk.generate.Capture capture = new com.litle.sdk.generate.Capture();
		capture.setReportGroup("test");
		// litleTxnId contains the Litle Transaction Id returned on the
		// authorization
		capture.setLitleTxnId(Long.valueOf(authorization.getTransactionId()));
		CaptureResponse response = getLitle().capture(capture);

		// Display Results
		System.out.println("Response: " + response.getResponse());
		System.out.println("Message: " + response.getMessage());
		System.out.println("Litle Transaction ID: " + response.getLitleTxnId());

		Result result = new Result();
		result.setSuccess("Approved".equals(response.getMessage()));
		result.setMessage(response.getMessage());
		return result;
	}

	public Result doCancel(String transactionId) {
		Void dovoid = new Void();
		dovoid.setReportGroup("test");
		dovoid.setLitleTxnId(Long.valueOf(transactionId));
		VoidResponse response = getLitle().dovoid(dovoid);

		// Display Results
		System.out.println("Response: " + response.getResponse());
		System.out.println("Message: " + response.getMessage());
		System.out.println("Litle Transaction ID: " + response.getLitleTxnId());

		Result result = new Result();
		result.setSuccess("Approved".equals(response.getMessage()));
		result.setMessage(response.getMessage());
		return result;
	}

	public Result doRefund(Money money, String transactionId) {
		Credit credit = new Credit();
		credit.setReportGroup("test");
		// litleTxnId contains the Litle Transaction Id returned on the deposit
		credit.setLitleTxnId(Long.valueOf(transactionId));
		CreditResponse response = getLitle().credit(credit);
		// Display Results
		System.out.println("Response: " + response.getResponse());
		System.out.println("Message: " + response.getMessage());
		System.out.println("Litle Transaction ID: " + response.getLitleTxnId());

		Result result = new Result();
		result.setSuccess("Approved".equals(response.getMessage()));
		result.setMessage(response.getMessage());
		return result;
	}

	private CardType convertCreditcard(CreditCard creditcard) {
		CardType card = new CardType();
		card.setNumber(creditcard.getNumber());
		card.setExpDate(creditcard.getExpirationAsForCharacter());
		card.setCardValidationNum(creditcard.getVerificationValue());

		switch (creditcard.getType()) {
		case AMERICAN_EXPRESS:
			card.setType(MethodOfPaymentTypeEnum.AX);
			break;
		case DINERS_CLUB:
			card.setType(MethodOfPaymentTypeEnum.DI);
			break;
		case DISCOVER:
			card.setType(MethodOfPaymentTypeEnum.VI);
			break;
		case JCB:
			card.setType(MethodOfPaymentTypeEnum.JC);
			break;
		case MASTER:
			card.setType(MethodOfPaymentTypeEnum.MC);
			break;
		case SWITCH:
			card.setType(MethodOfPaymentTypeEnum.VI);
			break;
		case VISA:
		default:
			card.setType(MethodOfPaymentTypeEnum.VI);
		}
		return card;
	}

	private LitleOnline getLitle() {
		return new LitleOnline(properties);
	}

	private Contact setAddress(com.litle.sdk.generate.Authorization auth,
			Address billingAddress) {
		Contact billToAddress = new Contact();
		billToAddress.setName(billingAddress.getFullName());
		billToAddress.setAddressLine1(billingAddress.getStreetAddress());
		billToAddress.setCity(billingAddress.getCity());
		billToAddress.setState(billingAddress.getState());
		billToAddress.setAddressLine2(billingAddress.getCountry());
		// billToAddress.setCountry(CountryTypeEnum.valueOf(arg0)VU);
		billToAddress.setZip(billingAddress.getPostalCode());
		return billToAddress;
	}
}
