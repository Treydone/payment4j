package fr.layer4.payment4j;

import java.util.ArrayList;

public class Transactions extends ArrayList<Transaction> {

	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
