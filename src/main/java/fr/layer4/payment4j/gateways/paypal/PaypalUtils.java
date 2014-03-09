package fr.layer4.payment4j.gateways.paypal;

import paypal.payflow.Response;
import paypal.payflow.TransactionResponse;
import fr.layer4.payment4j.Result;

public abstract class PaypalUtils {

	public static Result extractResult(Response resp) {
		Result result = new Result();

		// Get the Transaction Response parameters.
		TransactionResponse trxnResponse = resp.getTransactionResponse();

		if (trxnResponse != null) {
			result.setResponseCode(Integer.toString(trxnResponse.getResult()));
			result.setMessage(trxnResponse.getRespMsg());
			result.setAuthorization(trxnResponse.getPnref());
		}

		if (resp.getRecurringResponse() != null) {
			result.setRecurringRef(resp.getRecurringResponse().getRpRef());
		}

		return result;
	}
}
