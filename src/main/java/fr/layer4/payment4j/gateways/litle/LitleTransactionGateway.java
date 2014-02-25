package fr.layer4.payment4j.gateways.litle;

import java.util.Properties;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.money.Money;

import com.litle.sdk.LitleOnline;
import com.litle.sdk.generate.AuthorizationResponse;
import com.litle.sdk.generate.CaptureResponse;
import com.litle.sdk.generate.CardType;
import com.litle.sdk.generate.Contact;
import com.litle.sdk.generate.CountryTypeEnum;
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

	private static final String REPORT_GROUP = "test";
	private Properties properties;

	public LitleTransactionGateway(Gateway gateway, Properties properties) {
		super(gateway);
		this.properties = properties;
	}

	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress) {

		Credit credit = new Credit();
		credit.setCard(convertCreditcard(creditcard));
		credit.setOrderId(String.valueOf(RandomUtils.nextLong()));
		// credit.setLitleTxnId(RandomUtils.nextLong());
		credit.setAmount(money.getAmount().longValue());
		credit.setOrderSource(OrderSourceType.ECOMMERCE);
		credit.setReportGroup(REPORT_GROUP);
		if (billingAddress != null) {
			credit.setBillToAddress(setAddress(billingAddress));
		}

		CreditResponse response = getLitle().credit(credit);

		Result result = new Result();
		result.setSuccess("Approved".equals(response.getMessage()));
		result.setMessage(response.getMessage());
		return result;
	}

	public Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {
		com.litle.sdk.generate.Authorization auth = new com.litle.sdk.generate.Authorization();
		auth.setOrderId("1");
		auth.setAmount(money.getAmount().longValue());
		auth.setOrderSource(OrderSourceType.ECOMMERCE);
		auth.setReportGroup(REPORT_GROUP);

		if (order != null) {
			if (order.getBillingAddress() != null) {
				auth.setBillToAddress(setAddress(order.getBillingAddress()));
			}
			if (order.getShippingAddress() != null) {
				auth.setShipToAddress(setAddress(order.getShippingAddress()));
			}
		}

		CardType card = convertCreditcard(creditcard);
		auth.setCard(card);

		AuthorizationResponse response = getLitle().authorize(auth);

		Authorization authorization = new Authorization();
		authorization
				.setTransactionId(String.valueOf(response.getLitleTxnId()));
		authorization.setUnderlyingAuthorization(response);

		return authorization;
	}

	public Result doCapture(Authorization authorization) {
		com.litle.sdk.generate.Capture capture = new com.litle.sdk.generate.Capture();
		capture.setReportGroup(REPORT_GROUP);
		// litleTxnId contains the Litle Transaction Id returned on the
		// authorization
		capture.setLitleTxnId(Long.valueOf(authorization.getTransactionId()));
		CaptureResponse response = getLitle().capture(capture);

		// Display Results
		System.out.println(ToStringBuilder.reflectionToString(response));

		Result result = new Result();
		result.setSuccess("Approved".equals(response.getMessage()));
		result.setMessage(response.getMessage());
		result.setAuthorization(String.valueOf(response.getLitleTxnId()));

		return result;
	}

	public Result doCancel(String transactionId) {
		Void dovoid = new Void();
		dovoid.setReportGroup(REPORT_GROUP);
		dovoid.setLitleTxnId(Long.valueOf(transactionId));
		VoidResponse response = getLitle().dovoid(dovoid);

		Result result = new Result();
		result.setSuccess("Approved".equals(response.getMessage()));
		result.setMessage(response.getMessage());
		return result;
	}

	public Result doRefund(Money money, String transactionId) {
		Credit credit = new Credit();
		credit.setReportGroup(REPORT_GROUP);
		// litleTxnId contains the Litle Transaction Id returned on the deposit
		credit.setLitleTxnId(Long.valueOf(transactionId));
		CreditResponse response = getLitle().credit(credit);

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
		case MASTERCARD:
			card.setType(MethodOfPaymentTypeEnum.MC);
			break;
		case SWITCH:
			card.setType(MethodOfPaymentTypeEnum.VI);
			break;
		case VISA:
			card.setType(MethodOfPaymentTypeEnum.VI);
			break;
		default:
			card.setType(MethodOfPaymentTypeEnum.BLANK);
		}
		return card;
	}

	private LitleOnline getLitle() {
		return new LitleOnline(properties);
	}

	private Contact setAddress(Address billingAddress) {
		Contact billToAddress = new Contact();
		billToAddress.setName(billingAddress.getFullName());
		billToAddress.setAddressLine1(billingAddress.getStreetAddress());
		billToAddress.setCity(billingAddress.getCity());
		if (billingAddress.getState() != null) {
			billToAddress.setState(billingAddress.getState().getName());
		}
		if (billingAddress.getCountry() != null) {
			billToAddress.setCountry(CountryTypeEnum.valueOf(billingAddress
					.getCountry().getIsoCode()));
		}
		billToAddress.setZip(billingAddress.getPostalCode());
		return billToAddress;
	}
}
