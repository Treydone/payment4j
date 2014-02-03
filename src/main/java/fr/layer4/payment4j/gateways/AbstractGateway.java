package fr.layer4.payment4j.gateways;

import fr.layer4.payment4j.CreditCardStoreCapable;
import fr.layer4.payment4j.ExceptionResolver;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.HistoryCapable;
import fr.layer4.payment4j.RecurringCapable;
import fr.layer4.payment4j.ResponseCodeResolver;
import fr.layer4.payment4j.TransactionCapable;

public abstract class AbstractGateway implements Gateway {

	protected boolean testingMode = false;

	private ResponseCodeResolver responseCodeResolver;

	private ExceptionResolver exceptionResolver;

	public boolean isTransactionCapable() {
		return this instanceof TransactionCapable;
	}

	public boolean isRecurringCapable() {
		return this instanceof RecurringCapable;
	}

	public boolean isHistoryCapable() {
		return this instanceof HistoryCapable;
	}

	public boolean isCreditCardStoreCapable() {
		return this instanceof CreditCardStoreCapable;
	}

	public ExceptionResolver getExceptionResolver() {
		return exceptionResolver;
	}

	public void setExceptionResolver(ExceptionResolver exceptionResolver) {
		this.exceptionResolver = exceptionResolver;
	}

	public boolean isTestingMode() {
		return testingMode;
	}

	public void setTestingMode(boolean testingMode) {
		this.testingMode = testingMode;
	}

	public ResponseCodeResolver getResponseCodeResolver() {
		return responseCodeResolver;
	}

	public void setResponseCodeResolver(
			ResponseCodeResolver responseCodeResolver) {
		this.responseCodeResolver = responseCodeResolver;
	}
}
