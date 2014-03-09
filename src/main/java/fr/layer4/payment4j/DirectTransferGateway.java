package fr.layer4.payment4j;

import java.util.Map;

import org.iban4j.Iban;
import org.joda.money.Money;

public interface DirectTransferGateway {

	Result credit(Money money, Iban iban);

	Result credit(Money money, Iban iban, Map<String, Object> options);

	Result purchase(Money money, Iban iban);

	Result purchase(Money money, Iban iban, Map<String, Object> options);
}
