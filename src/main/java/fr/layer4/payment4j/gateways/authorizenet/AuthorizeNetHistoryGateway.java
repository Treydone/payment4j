package fr.layer4.payment4j.gateways.authorizenet;

import net.authorize.Merchant;
import net.authorize.data.xml.reporting.TransactionDetails;
import net.authorize.reporting.Result;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Transaction;
import fr.layer4.payment4j.Transactions;
import fr.layer4.payment4j.gateways.AbstractHistoryGateway;

public class AuthorizeNetHistoryGateway extends AbstractHistoryGateway {

	private String apiLoginId;
	private String transactionKey;

	protected AuthorizeNetHistoryGateway(Gateway gateway, String apiLoginId,
			String transactionKey) {
		super(gateway);
		this.apiLoginId = apiLoginId;
		this.transactionKey = transactionKey;
	}

	public Transactions doList() {
		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);

		net.authorize.reporting.Transaction transaction = merchant
				.createReportingTransaction(net.authorize.reporting.TransactionType.GET_SETTLED_BATCH_LIST);
		net.authorize.data.xml.reporting.ReportingDetails reportingDetails = net.authorize.data.xml.reporting.ReportingDetails
				.createReportingDetails();
		reportingDetails.setBatchIncludeStatistics(true);
		transaction.setReportingDetails(reportingDetails);

		Result<Transaction> authorizeNetResult = (Result<Transaction>) merchant
				.postTransaction(transaction);

		Transactions transactions = new Transactions();
		fr.layer4.payment4j.Result result = new fr.layer4.payment4j.Result();
		result.setResponseCode(authorizeNetResult.getResultCode());
		transactions.setResult(result);

		for (TransactionDetails details : authorizeNetResult
				.getReportingDetails().getTransactionDetailList()) {

			details.getSubmitTimeUTC();
			details.getCustomerIP();
			details.getResponseCode();
			details.getPayment().getCreditCard();
			details.getFirstName();
			details.getLastName();
		}

		for (net.authorize.data.xml.reporting.BatchDetails batchDetail : authorizeNetResult
				.getReportingDetails().getBatchDetailsList()) {
			System.out.println("<hr/>");
			System.out.println("* id: " + batchDetail.getBatchId() + "<br>");
			System.out.println("* settlementState: "
					+ batchDetail.getSettlementState().value() + "<br>");
			System.out.println("* local settlementTime: "
					+ batchDetail.getSettlementTimeLocal().toString() + "<br>");
			System.out.println("* UTC settlementTime: "
					+ batchDetail.getSettlementTimeUTC().toString() + "<br>");
			System.out.println("* paymentMethod: "
					+ batchDetail.getPaymentMethod() + "<br>");
			for (net.authorize.data.xml.reporting.BatchStatistics batchStat : batchDetail
					.getBatchStatisticsList()) {
				System.out.println("<table>");
				System.out.println("<tr><td>accountType</td><td>"
						+ batchStat.getAccountType() + "</td></tr>");
				System.out.println("<tr><td>chargeAmount</td><td>"
						+ batchStat.getChargeAmount() + "</td></tr>");
				System.out.println("<tr><td>chargebackAmount</td><td>"
						+ batchStat.getChargebackAmount() + "</td></tr>");
				System.out.println("<tr><td>chargebackCount</td><td>"
						+ batchStat.getChargebackCount() + "</td></tr>");
				System.out.println("<tr><td>chargeChargebackAmount</td><td>"
						+ batchStat.getChargeChargebackAmount() + "</td></tr>");
				System.out.println("<tr><td>chargeChargebackCount</td><td>"
						+ batchStat.getChargeChargebackCount() + "</td></tr>");
				System.out.println("<tr><td>chargeCount</td><td>"
						+ batchStat.getChargeCount() + "</td></tr>");
				System.out.println("<tr><td>chargeReturnedItemsAmount</td><td>"
						+ batchStat.getChargeReturnedItemsAmount()
						+ "</td></tr>");
				System.out.println("<tr><td>chargeReturnedItemsCount</td><td>"
						+ batchStat.getChargeReturnedItemsCount()
						+ "</td></tr>");
				System.out.println("<tr><td>correctionNoticeCount</td><td>"
						+ batchStat.getCorrectionNoticeCount() + "</td></tr>");
				System.out.println("<tr><td>declineCount</td><td>"
						+ batchStat.getDeclineCount() + "</td></tr>");
				System.out.println("<tr><td>errorCount</td><td>"
						+ batchStat.getErrorCount() + "</td></tr>");
				System.out.println("<tr><td>refundAmount</td><td>"
						+ batchStat.getRefundAmount() + "</td></tr>");
				System.out.println("<tr><td>refundCount</td><td>"
						+ batchStat.getRefundCount() + "</td></tr>");
				System.out.println("<tr><td>refundChargebackAmount</td><td>"
						+ batchStat.getRefundChargebackAmount() + "</td></tr>");
				System.out.println("<tr><td>refundReturnedItemsAmount</td><td>"
						+ batchStat.getRefundReturnedItemsAmount()
						+ "</td></tr>");
				System.out.println("<tr><td>refundReturnedItemsCount</td><td>"
						+ batchStat.getRefundReturnedItemsCount()
						+ "</td></tr>");
				System.out.println("<tr><td>returnedItemAmount</td><td>"
						+ batchStat.getReturnedItemAmount() + "</td></tr>");
				System.out.println("<tr><td>returnedItemCount</td><td>"
						+ batchStat.getReturnedItemCount() + "</td></tr>");
				System.out.println("<tr><td>voidCount</td><td>"
						+ batchStat.getVoidCount() + "</td></tr>");
				System.out.println("</table>");
			}
		}

		return transactions;
	}
}
