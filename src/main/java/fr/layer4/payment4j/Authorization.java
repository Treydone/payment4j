package fr.layer4.payment4j;

public class Authorization {

	private Object underlyingAuthorization;

	private String transactionId;

	private String authorizationCode;

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public Object getUnderlyingAuthorization() {
		return underlyingAuthorization;
	}

	public void setUnderlyingAuthorization(Object underlyingAuthorization) {
		this.underlyingAuthorization = underlyingAuthorization;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
