package fr.layer4.payment4j.gateways;

import java.util.Map;

import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.HistoryGateway;
import fr.layer4.payment4j.Transactions;

public abstract class AbstractHistoryGateway implements HistoryGateway {

	protected Gateway gateway;

	public AbstractHistoryGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	public Transactions list() {
		return list(null);
	}

	public Transactions list(Map<String, Object> options) {
		Transactions transactions = null;
		try {
			transactions = doList(options);
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(),
				transactions.getResult(), null, null);
		return transactions;
	}

	protected abstract Transactions doList(Map<String, Object> options);

}
