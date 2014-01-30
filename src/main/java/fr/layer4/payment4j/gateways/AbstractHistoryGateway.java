package fr.layer4.payment4j.gateways;

import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.HistoryGateway;
import fr.layer4.payment4j.Transactions;

public abstract class AbstractHistoryGateway implements HistoryGateway {

	protected Gateway gateway;

	public AbstractHistoryGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	public Transactions list() {
		Transactions transactions = null;
		try {
			transactions = doList();
		} catch (Throwable throwable) {
			throw GatewayUtils.resolveException(gateway.getExceptionResolver(),
					throwable);
		}
		GatewayUtils.resoleCode(gateway.getResponseCodeResolver(),
				transactions.getResult(), null, null);
		return transactions;
	}

	protected abstract Transactions doList();

}
