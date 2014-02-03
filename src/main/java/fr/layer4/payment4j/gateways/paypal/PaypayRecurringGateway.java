package fr.layer4.payment4j.gateways.paypal;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.NotImplementedException;
import org.joda.money.Money;

import paypal.payflow.CardTender;
import paypal.payflow.Currency;
import paypal.payflow.Invoice;
import paypal.payflow.PayflowConnectionData;
import paypal.payflow.PayflowUtility;
import paypal.payflow.RecurringAddTransaction;
import paypal.payflow.RecurringCancelTransaction;
import paypal.payflow.RecurringInfo;
import paypal.payflow.Response;
import paypal.payflow.UserInfo;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Schedule;
import fr.layer4.payment4j.gateways.AbstractRecurringGateway;

public class PaypayRecurringGateway extends AbstractRecurringGateway {

	private String user;
	private String vendor;
	private String partner;
	private String password;

	public PaypayRecurringGateway(Gateway gateway, String user, String vendor,
			String partner, String password) {
		super(gateway);
		this.user = user;
		this.vendor = vendor;
		this.partner = partner;
		this.password = password;
	}

	@Override
	protected String doRecurring(Money money, CreditCard creditcard,
			Schedule schedule) {

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

		RecurringInfo recurInfo = new RecurringInfo();
		// The date that the first payment will be processed.
		// This will be of the format mmddyyyy.

		Date startDate = schedule.getStartDate();
		if (startDate == null) {
			startDate = new Date();
		}

		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");

		recurInfo.setStart(format.format(startDate));
		// recurInfo.setProfileName("Test_Profile_1"); // User provided Profile
		// Name.
		// Specifies how often the payment occurs. All PAYPERIOD values must use
		// capital letters and can be any of WEEK / BIWK / SMMO / FRWK / MONT /
		// QTER / SMYR / YEAR

		switch (schedule.getEach()) {
		case DAY:
			throw new NotImplementedException(
					"Poor of you, days scheduling is not implementing...");
		case WEEK:
			recurInfo.setPayPeriod("WEEK");
			break;
		case MONTH:
			recurInfo.setPayPeriod("MONT");
			break;
		}

		recurInfo.setTerm(schedule.getTotalOccurences()); // Number of payments

		// // Peform an Optional Transaction.
		// recurInfo.setOptionalTrx("S"); // S = Sale, A = Authorization
		// // Set the amount if doing a "Sale" for the Optional Transaction.
		// Currency oTrxAmt = new Currency(new Double(25.75 + 9.95), "USD");
		// recurInfo.setOptionalTrxAmt(oTrxAmt);

		// Create a new Recurring Add Transaction.
		RecurringAddTransaction trans = new RecurringAddTransaction(info,
				connection, inv, card, recurInfo, PayflowUtility.getRequestId());

		// Use ORIGID to create a profile based on the details of another
		// transaction. See Reference Transaction.
		// trans.setOrigId("<ORIGINAL_PNREF>");

		// Submit the Transaction
		Response resp = trans.submitTransaction();
		PaypalUtils.extractResult(resp);
		return resp.getRecurringResponse().getRpRef();
	}

	@Override
	protected void doCancel(String recurringReference) {

		UserInfo info = new UserInfo(user, vendor, partner, password);
		PayflowConnectionData connection = new PayflowConnectionData();

		RecurringInfo recurInfo = new RecurringInfo();
		recurInfo.setOrigProfileId(recurringReference);
		// /////////////////////////////////////////////////////////////////

		// Create a new Recurring Cancel Transaction.
		RecurringCancelTransaction trans = new RecurringCancelTransaction(info,
				connection, recurInfo, PayflowUtility.getRequestId());

		// Submit the Transaction
		Response resp = trans.submitTransaction();
	}
}
