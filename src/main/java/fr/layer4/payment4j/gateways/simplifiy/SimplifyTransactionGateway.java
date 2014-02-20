package fr.layer4.payment4j.gateways.simplifiy;

import java.math.BigDecimal;

import org.joda.money.Money;

import com.simplify.payments.PaymentsMap;
import com.simplify.payments.domain.Payment;
import com.simplify.payments.domain.Refund;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class SimplifyTransactionGateway extends AbstractTransactionGateway {

	private String privateKey;

	private String publicKey;

	public SimplifyTransactionGateway(Gateway gateway) {
		super(gateway);
	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress) {
		return null;
	}

	@Override
	protected Result doCapture(Authorization authorization) {
		return null;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {

		try {
			Payment payment = Payment.create(
					publicKey,
					privateKey,
					new PaymentsMap()
							.set("currency",
									money.getCurrencyUnit().getNumeric3Code())
							.set("card.cvc", creditcard.getVerificationValue())
							.set("card.expMonth", creditcard.getMonth())
							.set("card.expYear", creditcard.getYear())
							.set("card.number", "5555555555554444")
							.set("amount",
									money.getAmount().multiply(
											BigDecimal.valueOf(100)))
			// .set("description", "prod description")
					);
			if ("APPROVED".equals(payment.get("paymentStatus"))) {
				System.out.println("Payment approved");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected Result doCancel(String transactionId) {
		return null;
	}

	@Override
	protected Result doRefund(Money money, String transactionId) {

		try {
			Refund refund = Refund.create(new PaymentsMap().set("amount", 100L)
					.set("payment", transactionId)
					.set("reason", "Refund Description")
					.set("reference", "76398734634"));

			if ("APPROVED".equals(refund.get("paymentStatus"))) {
				System.out.println("Payment approved");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
