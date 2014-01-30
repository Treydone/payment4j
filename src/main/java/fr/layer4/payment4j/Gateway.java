package fr.layer4.payment4j;

import java.util.Set;

import org.joda.money.CurrencyUnit;

public interface Gateway {

	boolean isTestingMode();

	ResponseCodeResolver getResponseCodeResolver();

	ExceptionResolver getExceptionResolver();

	Set<CreditCardType> getSupportedCreditCardTypes();

	String getHomepageUrl();

	String getDisplayName();

	CurrencyUnit getDefaultCurrency();

	Set<String> getSupportedCountries();

}
