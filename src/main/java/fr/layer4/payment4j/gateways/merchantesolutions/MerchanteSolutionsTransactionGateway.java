package fr.layer4.payment4j.gateways.merchantesolutions;

import org.joda.money.Money;

import com.mes.sdk.core.Settings;
import com.mes.sdk.gateway.GatewayRequest;
import com.mes.sdk.gateway.GatewayRequest.TransactionType;
import com.mes.sdk.gateway.GatewayResponse;
import com.mes.sdk.gateway.GatewaySettings;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class MerchanteSolutionsTransactionGateway extends
		AbstractTransactionGateway {

	private GatewaySettings settings;

	public MerchanteSolutionsTransactionGateway(Gateway gateway,
			String profileId, String profileKey) {
		super(gateway);
		settings = new GatewaySettings()
				.credentials(profileId, profileKey)
				.hostUrl(
						gateway.isTestingMode() ? GatewaySettings.URL_TEST
								: GatewaySettings.URL_LIVE)
				.method(Settings.Method.POST).timeout(10000).verbose(true);
	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress) {
		com.mes.sdk.gateway.Gateway mesGateway = new com.mes.sdk.gateway.Gateway(
				settings);
		GatewayRequest sRequest = new GatewayRequest(TransactionType.CREDIT)
				.cardData(MerchanteSolutionsUtils.convertCreditCard(creditcard));
		mesGateway.run(sRequest);
		return null;
	}

	@Override
	protected Result doCapture(Authorization authorization) {
		com.mes.sdk.gateway.Gateway mesGateway = new com.mes.sdk.gateway.Gateway(
				settings);
		GatewayRequest sRequest = new GatewayRequest(TransactionType.SETTLE)
				.setParameter("transaction_id",
						authorization.getTransactionId());
		mesGateway.run(sRequest);
		return null;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order) {

		com.mes.sdk.gateway.Gateway mesGateway = new com.mes.sdk.gateway.Gateway(
				settings);

		GatewayRequest sRequest = new GatewayRequest(TransactionType.PREAUTH)
				.cardData(MerchanteSolutionsUtils.convertCreditCard(creditcard))
				.amount(money.getAmount())
				.currency(money.getCurrencyUnit().getCode());

		if (order != null) {
			Address shippingAddress = order.getShippingAddress();
			if (shippingAddress != null) {
				sRequest.shippingName(shippingAddress.getFirstName(),
						shippingAddress.getLastName());
				sRequest.shippingCountry(shippingAddress.getCountry());
				sRequest.shippingAddress(shippingAddress.getStreetAddress(),
						shippingAddress.getPostalCode());
			}
		}

		GatewayResponse sResponse = mesGateway.run(sRequest);
		Authorization authorization = new Authorization();
		authorization.setTransactionId(sResponse.getTransactionId());
		return authorization;
	}

	@Override
	protected Result doCancel(String transactionId) {
		com.mes.sdk.gateway.Gateway mesGateway = new com.mes.sdk.gateway.Gateway(
				settings);
		GatewayRequest sRequest = new GatewayRequest(TransactionType.VOID)
				.setParameter("transaction_id", transactionId);
		mesGateway.run(sRequest);
		return null;
	}

	@Override
	protected Result doRefund(Money money, String transactionId) {
		com.mes.sdk.gateway.Gateway mesGateway = new com.mes.sdk.gateway.Gateway(
				settings);
		GatewayRequest sRequest = new GatewayRequest(TransactionType.REFUND)
				.amount(money.getAmount()).setParameter("transaction_id",
						transactionId);
		mesGateway.run(sRequest);
		return null;
	}
}