package fr.layer4.payment4j;

import java.util.Map;

public interface HistoryGateway {

	Transactions list();

	Transactions list(Map<String, Object> options);
}
