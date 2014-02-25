package fr.layer4.payment4j;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

	private Date submitted;

	private BigDecimal amount;

	private String returnCode;

	private Exception returnException;

	private TransactionStatus status;

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public Exception getReturnException() {
		return returnException;
	}

	public void setReturnException(Exception returnException) {
		this.returnException = returnException;
	}

	public Date getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
