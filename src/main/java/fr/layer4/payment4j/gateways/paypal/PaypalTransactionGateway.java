package fr.layer4.payment4j.gateways.paypal;

import org.joda.money.Money;

import paypal.payflow.AuthorizationTransaction;
import paypal.payflow.BillTo;
import paypal.payflow.CaptureTransaction;
import paypal.payflow.CardTender;
import paypal.payflow.CreditTransaction;
import paypal.payflow.Currency;
import paypal.payflow.Invoice;
import paypal.payflow.PayflowConnectionData;
import paypal.payflow.PayflowUtility;
import paypal.payflow.Response;
import paypal.payflow.ShipTo;
import paypal.payflow.UserInfo;
import paypal.payflow.VoidTransaction;
import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class PaypalTransactionGateway extends AbstractTransactionGateway {

	private String user;
	private String vendor;
	private String partner;
	private String password;

	public PaypalTransactionGateway(Gateway gateway, String user,
			String vendor, String partner, String password) {
		super(gateway);
		this.user = user;
		this.vendor = vendor;
		this.partner = partner;
		this.password = password;
	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress) {
		UserInfo info = new UserInfo(user, vendor, partner, password);
		PayflowConnectionData connection = new PayflowConnectionData();

		// Create a new Invoice data object with the Amount, Billing Address
		// etc. details.
		Invoice inv = new Invoice();

		// Set Amount.
		Currency amt = new Currency(money.getAmount().doubleValue());
		inv.setAmt(amt);
		inv.setPoNum("PO12345");
		inv.setInvNum("INV12345");

		// Set the Billing Address details.
		if (billingAddress != null) {
			BillTo bill = new BillTo();
			bill.setStreet(billingAddress.getStreetAddress());
			bill.setZip(billingAddress.getPostalCode());
			bill.setCity(billingAddress.getCity());
			bill.setCompanyName(billingAddress.getCompany());
			bill.setFirstName(billingAddress.getFirstName());
			bill.setLastName(billingAddress.getLastName());
			if (billingAddress.getCountry() != null) {
				bill.setBillToCountry(billingAddress.getCountry().getName());
			}
			if (billingAddress.getState() != null) {
				bill.setState(billingAddress.getState().getName());
			}
			inv.setBillTo(bill);
		}

		// Create a new Payment Device - Credit Card data object.
		// The input parameters are Credit Card No. and Expiry Date for the
		// Credit Card.
		paypal.payflow.CreditCard cc = new paypal.payflow.CreditCard(
				creditcard.getNumber(),
				creditcard.getExpirationAsForCharacter());
		cc.setCvv2(creditcard.getVerificationValue());

		// Create a new Tender - Card Tender data object.
		CardTender card = new CardTender(cc);
		// /////////////////////////////////////////////////////////////////

		// Create a new Credit Transaction.
		// Following is an example of a independent credit type of transaction.
		CreditTransaction trans = new CreditTransaction(info, connection, inv,
				card, PayflowUtility.getRequestId());

		// Submit the Transaction
		Response resp = trans.submitTransaction();

		Result result = PaypalUtils.extractResult(resp);
		return result;
	}

	@Override
	protected Result doCapture(Authorization authorization) {
		UserInfo info = new UserInfo(user, vendor, partner, password);
		PayflowConnectionData connection = new PayflowConnectionData();

		// Create a new Capture Transaction for the original amount of the
		// authorization. See above if you
		// need to change the amount.
		CaptureTransaction trans = new CaptureTransaction(
				authorization.getTransactionId(), info, connection,
				PayflowUtility.getRequestId());
		// Indicates if this Delayed Capture transaction is the last capture you
		// intend to make.
		// The values are: Y (default) / N
		// NOTE: If CAPTURECOMPLETE is Y, any remaining amount of the original
		// reauthorized transaction
		// is automatically voided. Also, this is only used for UK and US
		// accounts where PayPal is acting
		// as your bank.
		// trans.setcaptureComplete("Y");

		// Submit the Transaction
		Response resp = trans.submitTransaction();

		Result result = PaypalUtils.extractResult(resp);
		return result;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {
		UserInfo info = new UserInfo(user, vendor, partner, password);
		PayflowConnectionData connection = new PayflowConnectionData();

		Invoice inv = new Invoice();

		// Set Amount.
		Currency amt = new Currency(money.getAmount().doubleValue());
		inv.setAmt(amt);
		inv.setPoNum("PO12345");
		inv.setInvNum("INV12345");

		// Set the Billing Address details.
		if (order != null) {
			if (order.getBillingAddress() != null) {
				Address address = order.getBillingAddress();
				BillTo bill = new BillTo();
				bill.setStreet(address.getStreetAddress());
				bill.setZip(address.getPostalCode());
				bill.setCity(address.getCity());
				bill.setCompanyName(address.getCompany());
				bill.setFirstName(address.getFirstName());
				bill.setLastName(address.getLastName());
				if (address.getCountry() != null) {
					bill.setBillToCountry(address.getCountry().getName());
				}
				if (address.getState() != null) {
					bill.setState(address.getState().getName());
				}
				inv.setBillTo(bill);
			}
			if (order.getShippingAddress() != null) {
				ShipTo ship = new ShipTo();
				Address address = order.getBillingAddress();
				ship.setShipToStreet(address.getStreetAddress());
				ship.setShipToZip(address.getPostalCode());
				ship.setShipToCity(address.getCity());
				ship.setShipToFirstName(address.getFirstName());
				ship.setShipToLastName(address.getLastName());
				if (address.getCountry() != null) {
					ship.setShipToCountry(address.getCountry().getName());
				}
				if (address.getState() != null) {
					ship.setShipToState(address.getState().getName());
				}
				inv.setShipTo(ship);
			}
		}

		paypal.payflow.CreditCard cc = new paypal.payflow.CreditCard(
				creditcard.getNumber(),
				creditcard.getExpirationAsForCharacter());
		cc.setCvv2(creditcard.getVerificationValue());

		// Create a new Tender - Card Tender data object.
		CardTender card = new CardTender(cc);

		// Create a new Auth Transaction.
		AuthorizationTransaction trans = new AuthorizationTransaction(info,
				connection, inv, card, PayflowUtility.getRequestId());
		trans.setPartialAuth("Y");

		// Submit the Transaction
		Response resp = trans.submitTransaction();

		Authorization authorization = new Authorization();
		Result result = PaypalUtils.extractResult(resp);
		authorization.setTransactionId(result.getAuthorization());

		return authorization;
	}

	@Override
	protected Result doCancel(String transactionId) {
		UserInfo info = new UserInfo(user, vendor, partner, password);
		PayflowConnectionData connection = new PayflowConnectionData();
		// Create a new Void Transaction.
		// The ORIGID is the PNREF no. for a previous transaction.
		VoidTransaction trans = new VoidTransaction(transactionId, info,
				connection, PayflowUtility.getRequestId());

		// Submit the Transaction
		Response resp = trans.submitTransaction();

		Result result = PaypalUtils.extractResult(resp);
		return result;
	}

	@Override
	protected Result doRefund(Money money, String transactionId) {
		UserInfo info = new UserInfo(user, vendor, partner, password);
		PayflowConnectionData connection = new PayflowConnectionData();
		// If you want to change the amount being credited, you'll need to set
		// the Amount object.
		// Invoice inv = new Invoice();
		// Set the amount object if you want to change the amount from the
		// original transaction.
		// Currency Code USD is US ISO currency code. If no code passed, USD is
		// default.
		// See the Developer's Guide for the list of three-character currency
		// codes available.
		// Currency amt = new Currency(new Double(5.00));
		// inv.setAmt(amt);
		// CreditTransaction trans = new CreditTransaction("<ORIG_TRANS_ID>",
		// user, connection, inv, PayflowUtility.getRequestId());

		// Create a new Credit Transaction from the original transaction. See
		// above if you
		// need to change the amount.
		CreditTransaction trans = new CreditTransaction(transactionId, info,
				connection, PayflowUtility.getRequestId());

		// Submit the Transaction
		Response resp = trans.submitTransaction();

		Result result = PaypalUtils.extractResult(resp);
		return result;
	}

}
