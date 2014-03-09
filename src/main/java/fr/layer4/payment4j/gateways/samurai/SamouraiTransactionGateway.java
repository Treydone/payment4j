package fr.layer4.payment4j.gateways.samurai;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.NotImplementedException;
import org.joda.money.Money;

import com.feefighters.samurai.Processor;
import com.feefighters.samurai.SamuraiGateway;
import com.feefighters.samurai.Transaction;
import com.feefighters.samurai.util.TransactionOptions;

import fr.layer4.payment4j.Address;
import fr.layer4.payment4j.Authorization;
import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Order;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.gateways.AbstractTransactionGateway;

public class SamouraiTransactionGateway extends AbstractTransactionGateway {

	public SamouraiTransactionGateway(Gateway gateway, String merchantKey,
			String merchantPassword, String processorToken) {
		super(gateway);
		Properties properties = new Properties();
		properties.setProperty("merchantKey", merchantKey);
		properties.setProperty("merchantPassword", merchantPassword);
		properties.setProperty("processorToken", processorToken);
		SamuraiGateway.configure(properties);
	}

	@Override
	public Result doCredit(Money money, CreditCard creditcard,
			Address billingAddress, Map<String, Object> options) {
		throw new NotImplementedException();
	}

	@Override
	protected Result doCapture(Authorization authorization,
			Map<String, Object> options) {

		Transaction transaction = Transaction.find(authorization
				.getTransactionId());
		Transaction capture = transaction.capture();

		Result result = new Result();
		result.setSuccess(capture.success);
		result.setMessage(capture.errors.toString());

		return result;
	}

	@Override
	protected Authorization doAuthorize(Money money, CreditCard creditcard,
			Order order, Map<String, Object> options) {

		TransactionOptions tOptions = new TransactionOptions();
		tOptions.currencyCode = money.getCurrencyUnit().getNumeric3Code();
		Transaction transaction = Processor.theProcessor()
				.authorize(
						"",
						money.getAmount().multiply(BigDecimal.valueOf(100))
								.longValue(), tOptions);

		Authorization authorization = new Authorization();
		authorization.setTransactionId(transaction.referenceId);

		return authorization;
	}

	@Override
	protected Result doCancel(String transactionId, Map<String, Object> options) {
		Transaction authorization = Transaction.find(transactionId);
		Transaction voidTransaction = authorization.voidTransaction();
		Result result = new Result();
		result.setSuccess(voidTransaction.success);
		result.setMessage(voidTransaction.errors.toString());

		return result;
	}

	@Override
	protected Result doRefund(Money money, String transactionId,
			Map<String, Object> options) {
		Transaction authOrPurchase = Transaction.find(transactionId);
		Transaction reverse = authOrPurchase.reverse(money.getAmount()
				.multiply(BigDecimal.valueOf(100)));
		Result result = new Result();
		result.setSuccess(reverse.success);
		result.setMessage(reverse.errors.toString());

		return result;
	}
}
