package fr.layer4.payment4j;

public class Result {

	private boolean success;

	private String message;

	private String authorization;

	private String responseCode;

	private String recurringRef;

	public String getRecurringRef() {
		return recurringRef;
	}

	public void setRecurringRef(String recurringRef) {
		this.recurringRef = recurringRef;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
