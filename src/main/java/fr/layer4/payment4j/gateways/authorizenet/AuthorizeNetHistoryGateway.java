package fr.layer4.payment4j.gateways.authorizenet;

import java.util.Map;

import net.authorize.Merchant;
import net.authorize.data.xml.reporting.TransactionDetails;
import net.authorize.reporting.Result;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Transaction;
import fr.layer4.payment4j.TransactionStatus;
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

	public Transactions doList(Map<String, Object> options) {
		Merchant merchant = AuthorizeNetUtils.getMerchant(gateway, apiLoginId,
				transactionKey);

		net.authorize.reporting.Transaction reportTransaction = merchant
				.createReportingTransaction(net.authorize.reporting.TransactionType.GET_SETTLED_BATCH_LIST);
		net.authorize.data.xml.reporting.ReportingDetails reportingDetails = net.authorize.data.xml.reporting.ReportingDetails
				.createReportingDetails();
		reportingDetails.setBatchIncludeStatistics(true);
		reportTransaction.setReportingDetails(reportingDetails);

		Result<Transaction> authorizeNetResult = (Result<Transaction>) merchant
				.postTransaction(reportTransaction);

		Transactions transactions = new Transactions();
		fr.layer4.payment4j.Result result = new fr.layer4.payment4j.Result();
		result.setResponseCode(authorizeNetResult.getResultCode());
		transactions.setResult(result);

		for (TransactionDetails details : authorizeNetResult
				.getReportingDetails().getTransactionDetailList()) {
			Transaction transaction = new Transaction();
			transaction.setSubmitted(details.getSubmitTimeUTC());
			// TODO Hummmm...
			// transaction.setAmount(details.getAuthAmount());
			transaction.setAmount(details.getSettleAmount());

			transaction.setReturnCode(details.getResponseCode().getCode() + "("
					+ details.getResponseCode().getDescription() + ")");
			try {
				gateway.getResponseCodeResolver().resolve(
						Integer.toString(details.getResponseCode().getCode()),
						null, null, null);
			} catch (Exception e) {
				transaction.setReturnException(e);
			}

			TransactionStatus status = null;
			switch (details.getTransactionStatus()) {
			case DECLINED:
				status = TransactionStatus.DECLINED;
				break;
			case VOIDED:
				status = TransactionStatus.CANCELLED;
				break;
			case SETTLED_SUCCESSFULLY:
				status = TransactionStatus.CAPTURED;
				break;
			case REFUND_SETTLED_SUCCESSFULLY:
				status = TransactionStatus.REFUNDED;
				break;
			case AUTHORIZED_PENDING_CAPTURE:
			case AUTHORIZED_PENDING_RELEASE:
				status = TransactionStatus.AUTHORIZED;
				break;
			case COMMUNICATION_ERROR:
			case GENERAL_ERROR:
			case SETTLEMENT_ERROR:
				status = TransactionStatus.ERROR;
			default:
				status = TransactionStatus.UNKNOWN;
				break;
			}
			transaction.setStatus(status);

			// details.getCustomerIP();
			// details.getPayment().getCreditCard();
			// details.getFirstName();
			// details.getLastName();
			transactions.add(transaction);
		}

		return transactions;
	}
}
