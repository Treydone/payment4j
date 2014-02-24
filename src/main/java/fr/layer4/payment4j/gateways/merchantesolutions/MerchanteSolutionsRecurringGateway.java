package fr.layer4.payment4j.gateways.merchantesolutions;

import java.util.Date;

import org.apache.commons.lang.NotImplementedException;
import org.joda.money.Money;

import com.mes.sdk.gateway.GatewaySettings;
import com.mes.sdk.rbs.Rbs;
import com.mes.sdk.rbs.RbsRequest;
import com.mes.sdk.rbs.RbsRequest.PaymentFrequency;
import com.mes.sdk.rbs.RbsRequest.PaymentType;
import com.mes.sdk.rbs.RbsRequest.RequestType;
import com.mes.sdk.rbs.RbsResponse;
import com.mes.sdk.rbs.RbsSettings;

import fr.layer4.payment4j.CreditCard;
import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Result;
import fr.layer4.payment4j.Schedule;
import fr.layer4.payment4j.gateways.AbstractRecurringGateway;

public class MerchanteSolutionsRecurringGateway extends
		AbstractRecurringGateway {

	private RbsSettings settings;

	public MerchanteSolutionsRecurringGateway(Gateway gateway,
			String profileId, String profileKey) {
		super(gateway);
		settings = new RbsSettings()
				.credentials("testuser", "testpass", profileId)
				.hostUrl(
						gateway.isTestingMode() ? GatewaySettings.URL_TEST
								: GatewaySettings.URL_LIVE).verbose(true);
	}

	@Override
	protected Result doRecurring(Money money, CreditCard creditCard,
			Schedule schedule) {
		Rbs rbs = new Rbs(settings);

		RbsRequest cRequest = new RbsRequest(RequestType.CREATE);
		Date startDate = schedule.getStartDate();
		cRequest.setPaymentType(PaymentType.CC)
				.setCardData(creditCard.getNumber(),
						creditCard.getVerificationValue())
				.setStartDate(Integer.toString(startDate.getMonth()),
						Integer.toString(startDate.getDay()),
						Integer.toString(startDate.getYear()))
				.setAmount(money.getAmount().toString())
				.setPaymentCount(
						Integer.toString(schedule.getTotalOccurences()));

		switch (schedule.getEach()) {
		case MONTH:
			cRequest.setFrequency(PaymentFrequency.MONTHLY);
			break;
		default:
			throw new NotImplementedException();
		}

		RbsResponse cResponse = rbs.run(cRequest);
		if (cResponse.requestSuccessful()) {
			// Store Results
		}
		return null;
	}

	@Override
	protected Result doCancel(String recurringReference) {
		Rbs rbs = new Rbs(settings);

		RbsRequest cRequest = new RbsRequest(RequestType.DELETE);
		RbsResponse cResponse = rbs.run(cRequest);
		if (cResponse.requestSuccessful()) {
			// Store Results
		}
		return null;
	}

}
