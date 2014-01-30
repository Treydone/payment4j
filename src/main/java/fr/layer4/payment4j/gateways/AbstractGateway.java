package fr.layer4.payment4j.gateways;

import fr.layer4.payment4j.ExceptionResolver;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.ResponseCodeResolver;

public abstract class AbstractGateway implements Gateway {

	protected boolean testingMode = false;

	private ResponseCodeResolver responseCodeResolver;

	private ExceptionResolver exceptionResolver;

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
