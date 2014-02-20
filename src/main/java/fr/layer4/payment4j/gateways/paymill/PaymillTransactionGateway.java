package fr.layer4.payment4j.gateways.paymill;

import org.joda.money.Money;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class PaymillTransactionGateway extends AbstractTransactionGateway {

	private String apiKey;

	public PaymillTransactionGateway(Gateway gateway, String apiKey) {
		super(gateway);
		this.apiKey = apiKey;
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
		return null;
	}

	@Override
	protected Result doCancel(String transactionId) {
		return null;
	}

	@Override
	protected Result doRefund(Money money, String transactionId) {
		return null;
	}
}
