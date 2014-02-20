package fr.layer4.payment4j;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

	private Date submitted;

	private BigDecimal amount;

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
