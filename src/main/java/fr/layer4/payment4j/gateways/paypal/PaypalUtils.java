package fr.layer4.payment4j.gateways.paypal;

import paypal.payflow.Context;
import paypal.payflow.FraudResponse;
import paypal.payflow.PayflowUtility;
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
			System.out.println("RESULT = " + trxnResponse.getResult());
			System.out.println("PNREF = " + trxnResponse.getPnref());
			System.out.println("RESPMSG = " + trxnResponse.getRespMsg());
			System.out.println("AUTHCODE = " + trxnResponse.getAuthCode());
			System.out.println("AVSADDR = " + trxnResponse.getAvsAddr());
			System.out.println("AVSZIP = " + trxnResponse.getAvsZip());
			System.out.println("IAVS = " + trxnResponse.getIavs());
			// If value is true, then the Request ID has not been changed
			// and the original response
			// of the original transction is returned.
			System.out.println("DUPLICATE = " + trxnResponse.getDuplicate());
		}

		// Get the Fraud Response parameters.
		FraudResponse fraudResp = resp.getFraudResponse();
		if (fraudResp != null) {
			System.out.println("PREFPSMSG = " + fraudResp.getPreFpsMsg());
			System.out.println("POSTFPSMSG = " + fraudResp.getPostFpsMsg());
		}

		// Display the response.
		System.out.println("\n" + PayflowUtility.getStatus(resp));

		// Get the Transaction Context and check for any contained SDK
		// specific errors (optional code).
		Context transCtx = resp.getContext();
		if (transCtx != null && transCtx.getErrorCount() > 0) {
			System.out.println("\nTransaction Errors = " + transCtx.toString());
		}
		return result;
	}
}
