package fr.layer4.payment4j.gateways.merchantesolutions;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Throwables;
import com.mes.sdk.gateway.GatewaySettings;
import com.mes.sdk.reporting.Reporting;
import com.mes.sdk.reporting.ReportingRequest;
import com.mes.sdk.reporting.ReportingRequest.ReportMode;
import com.mes.sdk.reporting.ReportingRequest.ReportType;
import com.mes.sdk.reporting.ReportingRequest.ResponseFormat;
import com.mes.sdk.reporting.ReportingResponse;
import com.mes.sdk.reporting.ReportingSettings;

import fr.layer4.payment4j.Gateway;
import fr.layer4.payment4j.Transaction;
import fr.layer4.payment4j.Transactions;
import fr.layer4.payment4j.gateways.AbstractHistoryGateway;

public class MerchanteSolutionsHistoryGateway extends AbstractHistoryGateway {

	private ReportingSettings settings;

	public MerchanteSolutionsHistoryGateway(Gateway gateway, String profileId,
			String profileKey) {
		super(gateway);
		settings = new ReportingSettings()
				.credentials("testuser", "testpass")
				.hostUrl(
						gateway.isTestingMode() ? GatewaySettings.URL_TEST
								: GatewaySettings.URL_LIVE).verbose(true);
	}

	@Override
	protected Transactions doList(Map<String, Object> options) {

		ReportingRequest request = new ReportingRequest(ReportType.BATCH,
				ReportMode.DETAIL);
		request.setNode("941000xxxxxx").setStartDate("11", "01", "2012")
				.setEndDate("11", "01", "2012")
				.setResponseFormat(ResponseFormat.CSV);
		Reporting reporting = new Reporting(settings);
		ReportingResponse response = reporting.run(request);
		Transactions transactions = new Transactions();

		if (reporting.wasSuccessful()) {

			CSVReader reader = new CSVReader(new StringReader(
					response.getRawResponse()));

			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

			// "Merchant Id","DBA Name","Term Num","Batch Num","Batch Date","Tran Date","Card Type","Card Number","Reference","Purchase Id","Auth Code","Entry Mode","Tran Amount"
			String[] line;
			int i = 0;
			try {
				while ((line = reader.readNext()) != null) {
					if (i != 0) {
						Transaction transaction = new Transaction();
						transaction.setAmount(new BigDecimal(line[12]));
						try {
							transaction.setSubmitted(format.parse(line[5]));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						transactions.add(transaction);
					}
				}
			} catch (IOException e) {
				throw Throwables.propagate(e);
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					// Dude...
				}
			}
		}

		return transactions;
	}
}
