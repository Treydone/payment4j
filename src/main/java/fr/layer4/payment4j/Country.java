/*
 * Copyright 2004-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.layer4.payment4j;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;

public class Country implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Country AFGHANISTAN = new Country("AF", "Afghanistan");
	public static final Country ALBANIA = new Country("AL", "Albania");
	public static final Country ALGERIA = new Country("DZ", "Algeria");
	public static final Country AMERICAN_SAMOA = new Country("AS",
			"American Samoa");
	public static final Country ANDORRA = new Country("AD", "Andorra");
	public static final Country ANGOLA = new Country("AO", "Angola");
	public static final Country ANGUILLA = new Country("AI", "Anguilla");
	public static final Country ANTARCTICA = new Country("AQ", "Antarctica");
	public static final Country ANTIGUA_AND_BARBUDA = new Country("AG",
			"Antigua and Barbuda");
	public static final Country ARGENTINA = new Country("AR", "Argentina");
	public static final Country ARMENIA = new Country("AM", "Armenia");
	public static final Country ARUBA = new Country("AW", "Aruba");
	public static final Country AUSTRALIA = new Country("AU", "Australia");
	public static final Country AUSTRIA = new Country("AT", "Austria");
	public static final Country AZERBAIJAN = new Country("AZ", "Azerbaijan");
	public static final Country BAHAMAS = new Country("BS", "Bahamas");
	public static final Country BAHRAIN = new Country("BH", "Bahrain");
	public static final Country BANGLADESH = new Country("BD", "Bangladesh");
	public static final Country BARBADOS = new Country("BB", "Barbados");
	public static final Country BELARUS = new Country("BY", "Belarus");
	public static final Country BELGIUM = new Country("BE", "Belgium");
	public static final Country BELIZE = new Country("BZ", "Belize");
	public static final Country BENIN = new Country("BJ", "Benin");
	public static final Country BERMUDA = new Country("BM", "Bermuda");
	public static final Country BHUTAN = new Country("BT", "Bhutan");
	public static final Country BOLIVIA = new Country("BO", "Bolivia");
	public static final Country BOSNIA_AND_HERZEGOVINA = new Country("BA",
			"Bosnia and Herzegovina");
	public static final Country BOTSWANA = new Country("BW", "Botswana");
	public static final Country BOUVET_ISLAND = new Country("BV",
			"Bouvet Island");
	public static final Country BRAZIL = new Country("BR", "Brazil");
	public static final Country BRITISH_INDIAN_OCEAN_TERRITORY = new Country(
			"IO", "British Indian Ocean Territory");
	public static final Country BRUNEI_DARUSSALAM = new Country("BN",
			"Brunei Darussalam");
	public static final Country BULGARIA = new Country("BG", "Bulgaria");
	public static final Country BURKINA_FASO = new Country("BF", "Burkina Faso");
	public static final Country BURUNDI = new Country("BI", "Burundi");
	public static final Country CAMBODIA = new Country("KH", "Cambodia");
	public static final Country CAMEROON = new Country("CM", "Cameroon");
	public static final Country CANADA = new Country("CA", "Canada");
	public static final Country CAPE_VERDE = new Country("CV", "Cape Verde");
	public static final Country CAYMAN_ISLANDS = new Country("KY",
			"Cayman Islands");
	public static final Country CENTRAL_AFRICAN_REPUBLIC = new Country("CF",
			"Central African Republic");
	public static final Country CHAD = new Country("TD", "Chad");
	public static final Country CHILE = new Country("CL", "Chile");
	public static final Country CHINA = new Country("CN", "China");
	public static final Country CHRISTMAS_ISLAND = new Country("CX",
			"Christmas Island");
	public static final Country COCOS_KEELING_ISLANDS = new Country("CC",
			"Cocos (Keeling) Islands");
	public static final Country COLOMBIA = new Country("CO", "Colombia");
	public static final Country COMOROS = new Country("KM", "Comoros");
	public static final Country CONGO = new Country("CG", "Congo");
	public static final Country CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE = new Country(
			"CD", "Congo, The Democratic Republic of the");
	public static final Country COOK_ISLANDS = new Country("CK", "Cook Islands");
	public static final Country COSTA_RICA = new Country("CR", "Costa Rica");
	public static final Country COTE_DIVOIRE = new Country("CI",
			"Cote D'Ivoire");
	public static final Country CROATIA = new Country("HR", "Croatia");
	public static final Country CUBA = new Country("CU", "Cuba");
	public static final Country CYPRUS = new Country("CY", "Cyprus");
	public static final Country CZECH_REPUBLIC = new Country("CZ",
			"Czech Republic");
	public static final Country DENMARK = new Country("DK", "Denmark");
	public static final Country DJIBOUTI = new Country("DJ", "Djibouti");
	public static final Country DOMINICA = new Country("DM", "Dominica");
	public static final Country DOMINICAN_REPUBLIC = new Country("DO",
			"Dominican Republic");
	public static final Country EAST_TIMOR = new Country("TP", "East Timor");
	public static final Country ECUADOR = new Country("EC", "Ecuador");
	public static final Country EGYPT = new Country("EG", "Egypt");
	public static final Country EL_SALVADOR = new Country("SV", "El Salvador");
	public static final Country EQUATORIAL_GUINEA = new Country("GQ",
			"Equatorial Guinea");
	public static final Country ERITREA = new Country("ER", "Eritrea");
	public static final Country ESTONIA = new Country("EE", "Estonia");
	public static final Country ETHIOPIA = new Country("ET", "Ethiopia");
	public static final Country FALKLAND_ISLANDS_MALVINAS = new Country("FK",
			"Falkland Islands (Malvinas)");
	public static final Country FAROE_ISLANDS = new Country("FO",
			"Faroe Islands");
	public static final Country FIJI = new Country("FJ", "Fiji");
	public static final Country FINLAND = new Country("FI", "Finland");
	public static final Country FRANCE = new Country("FR", "France");
	public static final Country FRENCH_GUIANA = new Country("GF",
			"French Guiana");
	public static final Country FRENCH_POLYNESIA = new Country("PF",
			"French Polynesia");
	public static final Country FRENCH_SOUTHERN_TERRITORIES = new Country("TF",
			"French Southern Territories");
	public static final Country GABON = new Country("GA", "Gabon");
	public static final Country GAMBIA = new Country("GM", "Gambia");
	public static final Country GEORGIA = new Country("GE", "Georgia");
	public static final Country GERMANY = new Country("DE", "Germany");
	public static final Country GHANA = new Country("GH", "Ghana");
	public static final Country GIBRALTAR = new Country("GI", "Gibraltar");
	public static final Country GREECE = new Country("GR", "Greece");
	public static final Country GREENLAND = new Country("GL", "Greenland");
	public static final Country GRENADA = new Country("GD", "Grenada");
	public static final Country GUADELOUPE = new Country("GP", "Guadeloupe");
	public static final Country GUAM = new Country("GU", "Guam");
	public static final Country GUATEMALA = new Country("GT", "Guatemala");
	public static final Country GUINEA = new Country("GN", "Guinea");
	public static final Country GUINEA_BISSAU = new Country("GW",
			"Guinea-Bissau");
	public static final Country GUYANA = new Country("GY", "Guyana");
	public static final Country HAITI = new Country("HT", "Haiti");
	public static final Country HEARD_ISLAND_AND_MCDONALD_ISLANDS = new Country(
			"HM", "Heard Island and Mcdonald Islands");
	public static final Country HOLY_SEE_VATICAN_CITY_STATE = new Country("VA",
			"Holy See (Vatican City State)");
	public static final Country HONDURAS = new Country("HN", "Honduras");
	public static final Country HONG_KONG = new Country("HK", "Hong Kong");
	public static final Country HUNGARY = new Country("HU", "Hungary");
	public static final Country ICELAND = new Country("IS", "Iceland");
	public static final Country INDIA = new Country("IN", "India");
	public static final Country INDONESIA = new Country("ID", "Indonesia");
	public static final Country IRAN_ISLAMIC_REPUBLIC_OF = new Country("IR",
			"Iran, Islamic Republic Of");
	public static final Country IRAQ = new Country("IQ", "Iraq");
	public static final Country IRELAND = new Country("IE", "Ireland");
	public static final Country ISRAEL = new Country("IL", "Israel");
	public static final Country ITALY = new Country("IT", "Italy");
	public static final Country JAMAICA = new Country("JM", "Jamaica");
	public static final Country JAPAN = new Country("JP", "Japan");
	public static final Country JORDAN = new Country("JO", "Jordan");
	public static final Country KAZAKSTAN = new Country("KZ", "Kazakstan");
	public static final Country KENYA = new Country("KE", "Kenya");
	public static final Country KIRIBATI = new Country("KI", "Kiribati");
	public static final Country KOREA_DEMOCRATIC_PEOPLES_REPUBLIC_OF = new Country(
			"KP", "Korea, Democratic People'S Republic of");
	public static final Country KOREA_REPUBLIC_OF = new Country("KR",
			"Korea, Republic of");
	public static final Country KUWAIT = new Country("KW", "Kuwait");
	public static final Country KYRGYZSTAN = new Country("KG", "Kyrgyzstan");
	public static final Country LAO_PEOPLES_DEMOCRATIC_REPUBLIC = new Country(
			"LA", "Lao People'S Democratic Republic");
	public static final Country LATVIA = new Country("LV", "Latvia");
	public static final Country LEBANON = new Country("LB", "Lebanon");
	public static final Country LESOTHO = new Country("LS", "Lesotho");
	public static final Country LIBERIA = new Country("LR", "Liberia");
	public static final Country LIBYAN_ARAB_JAMAHIRIYA = new Country("LY",
			"Libyan Arab Jamahiriya");
	public static final Country LIECHTENSTEIN = new Country("LI",
			"Liechtenstein");
	public static final Country LITHUANIA = new Country("LT", "Lithuania");
	public static final Country LUXEMBOURG = new Country("LU", "Luxembourg");
	public static final Country MACAU = new Country("MO", "Macau");
	public static final Country MACEDONIA_THE_FORMER_YUGOSLAV_REPUBLIC_OF = new Country(
			"MK", "Macedonia, The Former Yugoslav Republic of");
	public static final Country MADAGASCAR = new Country("MG", "Madagascar");
	public static final Country MALAWI = new Country("MW", "Malawi");
	public static final Country MALAYSIA = new Country("MY", "Malaysia");
	public static final Country MALDIVES = new Country("MV", "Maldives");
	public static final Country MALI = new Country("ML", "Mali");
	public static final Country MALTA = new Country("MT", "Malta");
	public static final Country MARSHALL_ISLANDS = new Country("MH",
			"Marshall Islands");
	public static final Country MARTINIQUE = new Country("MQ", "Martinique");
	public static final Country MAURITANIA = new Country("MR", "Mauritania");
	public static final Country MAURITIUS = new Country("MU", "Mauritius");
	public static final Country MAYOTTE = new Country("YT", "Mayotte");
	public static final Country MEXICO = new Country("MX", "Mexico");
	public static final Country MICRONESIA_FEDERATED_STATES_OF = new Country(
			"FM", "Micronesia, Federated States of");
	public static final Country MOLDOVA_REPUBLIC_OF = new Country("MD",
			"Moldova, Republic Of");
	public static final Country MONACO = new Country("MC", "Monaco");
	public static final Country MONGOLIA = new Country("MN", "Mongolia");
	public static final Country MONTSERRAT = new Country("MS", "Montserrat");
	public static final Country MOROCCO = new Country("MA", "Morocco");
	public static final Country MOZAMBIQUE = new Country("MZ", "Mozambique");
	public static final Country MYANMAR = new Country("MM", "Myanmar");
	public static final Country NAMIBIA = new Country("NA", "Namibia");
	public static final Country NAURU = new Country("NR", "Nauru");
	public static final Country NEPAL = new Country("NP", "Nepal");
	public static final Country NETHERLANDS = new Country("NL", "Netherlands");
	public static final Country NETHERLANDS_ANTILLES = new Country("AN",
			"Netherlands Antilles");
	public static final Country NEW_CALEDONIA = new Country("NC",
			"New Caledonia");
	public static final Country NEW_ZEALAND = new Country("NZ", "New Zealand");
	public static final Country NICARAGUA = new Country("NI", "Nicaragua");
	public static final Country NIGER = new Country("NE", "Niger");
	public static final Country NIGERIA = new Country("NG", "Nigeria");
	public static final Country NIUE = new Country("NU", "Niue");
	public static final Country NORFOLK_ISLAND = new Country("NF",
			"Norfolk Island");
	public static final Country NORTHERN_MARIANA_ISLANDS = new Country("MP",
			"Northern Mariana Islands");
	public static final Country NORWAY = new Country("NO", "Norway");
	public static final Country OMAN = new Country("OM", "Oman");
	public static final Country PAKISTAN = new Country("PK", "Pakistan");
	public static final Country PALAU = new Country("PW", "Palau");
	public static final Country PALESTINIAN_TERRITORY_OCCUPIED = new Country(
			"PS", "Palestinian Territory, Occupied");
	public static final Country PANAMA = new Country("PA", "Panama");
	public static final Country PAPUA_NEW_GUINEA = new Country("PG",
			"Papua New Guinea");
	public static final Country PARAGUAY = new Country("PY", "Paraguay");
	public static final Country PERU = new Country("PE", "Peru");
	public static final Country PHILIPPINES = new Country("PH", "Philippines");
	public static final Country PITCAIRN = new Country("PN", "Pitcairn");
	public static final Country POLAND = new Country("PL", "Poland");
	public static final Country PORTUGAL = new Country("PT", "Portugal");
	public static final Country PUERTO_RICO = new Country("PR", "Puerto Rico");
	public static final Country QATAR = new Country("QA", "Qatar");
	public static final Country REUNION = new Country("RE", "Reunion");
	public static final Country ROMANIA = new Country("RO", "Romania");
	public static final Country RUSSIAN_FEDERATION = new Country("RU",
			"Russian Federation");
	public static final Country RWANDA = new Country("RW", "Rwanda");
	public static final Country SAINT_HELENA = new Country("SH", "Saint Helena");
	public static final Country SAINT_KITTS_AND_NEVIS = new Country("KN",
			"Saint Kitts and Nevis");
	public static final Country SAINT_LUCIA = new Country("LC", "Saint Lucia");
	public static final Country SAINT_PIERRE_AND_MIQUELON = new Country("PM",
			"Saint Pierre and Miquelon");
	public static final Country SAINT_VINCENT_AND_THE_GRENADINES = new Country(
			"VC", "Saint Vincent and The Grenadines");
	public static final Country SAMOA = new Country("WS", "Samoa");
	public static final Country SAN_MARINO = new Country("SM", "San Marino");
	public static final Country SAO_TOME_AND_PRINCIPE = new Country("ST",
			"Sao Tome and Principe");
	public static final Country SAUDI_ARABIA = new Country("SA", "Saudi Arabia");
	public static final Country SENEGAL = new Country("SN", "Senegal");
	public static final Country SEYCHELLES = new Country("SC", "Seychelles");
	public static final Country SIERRA_LEONE = new Country("SL", "Sierra Leone");
	public static final Country SINGAPORE = new Country("SG", "Singapore");
	public static final Country SLOVAKIA = new Country("SK", "Slovakia");
	public static final Country SLOVENIA = new Country("SI", "Slovenia");
	public static final Country SOLOMON_ISLANDS = new Country("SB",
			"Solomon Islands");
	public static final Country SOMALIA = new Country("SO", "Somalia");
	public static final Country SOUTH_AFRICA = new Country("ZA", "South Africa");
	public static final Country SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS = new Country(
			"GS", "South Georgia and The South Sandwich Islands");
	public static final Country SPAIN = new Country("ES", "Spain");
	public static final Country SRI_LANKA = new Country("LK", "Sri Lanka");
	public static final Country SUDAN = new Country("SD", "Sudan");
	public static final Country SURINAME = new Country("SR", "Suriname");
	public static final Country SVALBARD_AND_JAN_MAYEN = new Country("SJ",
			"Svalbard and Jan Mayen");
	public static final Country SWAZILAND = new Country("SZ", "Swaziland");
	public static final Country SWEDEN = new Country("SE", "Sweden");
	public static final Country SWITZERLAND = new Country("CH", "Switzerland");
	public static final Country SYRIAN_ARAB_REPUBLIC = new Country("SY",
			"Syrian Arab Republic");
	public static final Country TAIWAN_PROVINCE_OF_CHINA = new Country("TW",
			"Taiwan, Province of China");
	public static final Country TAJIKISTAN = new Country("TJ", "Tajikistan");
	public static final Country TANZANIA_UNITED_REPUBLIC_OF = new Country("TZ",
			"Tanzania, United Republic of");
	public static final Country THAILAND = new Country("TH", "Thailand");
	public static final Country TOGO = new Country("TG", "Togo");
	public static final Country TOKELAU = new Country("TK", "Tokelau");
	public static final Country TONGA = new Country("TO", "Tonga");
	public static final Country TRINIDAD_AND_TOBAGO = new Country("TT",
			"Trinidad and Tobago");
	public static final Country TUNISIA = new Country("TN", "Tunisia");
	public static final Country TURKEY = new Country("TR", "Turkey");
	public static final Country TURKMENISTAN = new Country("TM", "Turkmenistan");
	public static final Country TURKS_AND_CAICOS_ISLANDS = new Country("TC",
			"Turks and Caicos Islands");
	public static final Country TUVALU = new Country("TV", "Tuvalu");
	public static final Country UGANDA = new Country("UG", "Uganda");
	public static final Country UKRAINE = new Country("UA", "Ukraine");
	public static final Country UNITED_ARAB_EMIRATES = new Country("AE",
			"United Arab Emirates");
	public static final Country UNITED_KINGDOM = new Country("GB",
			"United Kingdom");
	public static final Country UNITED_STATES = new Country("US",
			"United States");
	public static final Country UNITED_STATES_MINOR_OUTLYING_ISLANDS = new Country(
			"UM", "United States Minor Outlying Islands");
	public static final Country URUGUAY = new Country("UY", "Uruguay");
	public static final Country UZBEKISTAN = new Country("UZ", "Uzbekistan");
	public static final Country VANUATU = new Country("VU", "Vanuatu");
	public static final Country VENEZUELA = new Country("VE", "Venezuela");
	public static final Country VIET_NAM = new Country("VN", "Viet Nam");
	public static final Country VIRGIN_ISLANDS_BRITISH = new Country("VG",
			"Virgin Islands, British");
	public static final Country VIRGIN_ISLANDS_US = new Country("VI",
			"Virgin Islands, U.S.");
	public static final Country WALLIS_AND_FUTUNA = new Country("WF",
			"Wallis and Futuna");
	public static final Country WESTERN_SAHARA = new Country("EH",
			"Western Sahara");
	public static final Country YEMEN = new Country("YE", "Yemen");
	public static final Country YUGOSLAVIA = new Country("YU", "Yugoslavia");
	public static final Country ZAMBIA = new Country("ZM", "Zambia");
	public static final Country ZIMBABWE = new Country("ZW", "Zimbabwe");

	private final String isoCode;

	private final String name;

	public Country(String isoCode, String name) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(isoCode));
		this.isoCode = isoCode;
		this.name = name;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return isoCode.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Country)) {
			return false;
		}
		return isoCode.equals(((Country) object).getIsoCode());
	}

	@Override
	public String toString() {
		return name;
	}

}
