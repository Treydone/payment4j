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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;

public class State implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static ConcurrentMap<String, State> states = new ConcurrentHashMap<String, State>();

	public static final State ALABAMA = usaState("AL", "Alabama");
	public static final State ALASKA = usaState("AK", "Alaska");
	public static final State ARIZONA = usaState("AZ", "Arizona");
	public static final State ARKANSAS = usaState("AR", "Arkansas");
	public static final State CALIFORNIA = usaState("CA", "California");
	public static final State COLORADO = usaState("CO", "Colorado");
	public static final State CONNECTICUT = usaState("CT", "Connecticut");
	public static final State DELAWARE = usaState("DE", "Delaware");
	public static final State DISTRICT_OF_COLUMBIA = usaState("DC",
			"District of Columbia");
	public static final State FLORIDA = usaState("FL", "Florida");
	public static final State GEORGIA = usaState("GA", "Georgia");
	public static final State HAWAII = usaState("HI", "Hawaii");
	public static final State IDAHO = usaState("ID", "Idaho");
	public static final State ILLINOIS = usaState("IL", "Illinois");
	public static final State INDIANA = usaState("IN", "Indiana");
	public static final State IOWA = usaState("IA", "Iowa");
	public static final State KANSAS = usaState("KS", "Kansas");
	public static final State KENTUCKY = usaState("KY", "Kentucky");
	public static final State LOUISIANA = usaState("LA", "Louisiana");
	public static final State MAINE = usaState("ME", "Maine");
	public static final State MARYLAND = usaState("MD", "Maryland");
	public static final State MASSACHUSETTS = usaState("MA", "Massachusetts");
	public static final State MICHIGAN = usaState("MI", "Michigan");
	public static final State MINNESOTA = usaState("MN", "Minnesota");
	public static final State MISSISSIPPI = usaState("MS", "Mississippi");
	public static final State MISSOURI = usaState("MO", "Missouri");
	public static final State MONTANA = usaState("MT", "Montana");
	public static final State NEBRASKA = usaState("NE", "Nebraska");
	public static final State NEVADA = usaState("NV", "Nevada");
	public static final State NEW_HAMPSHIRE = usaState("NH", "New Hampshire");
	public static final State NEW_JERSEY = usaState("NJ", "New Jersey");
	public static final State NEW_MEXICO = usaState("NM", "New Mexico");
	public static final State NEW_YORK = usaState("NY", "New York");
	public static final State NORTH_CAROLINA = usaState("NC", "North Carolina");
	public static final State NORTH_DAKOTA = usaState("ND", "North Dakota");
	public static final State OHIO = usaState("OH", "Ohio");
	public static final State OKLAHOMA = usaState("OK", "Oklahoma");
	public static final State OREGON = usaState("OR", "Oregon");
	public static final State PENNSYLVANIA = usaState("PA", "Pennsylvania");
	public static final State RHODE_ISLAND = usaState("RI", "Rhode Island");
	public static final State SOUTH_CAROLINA = usaState("SC", "South Carolina");
	public static final State SOUTH_DAKOTA = usaState("SD", "South Dakota");
	public static final State TENNESSEE = usaState("TN", "Tennessee");
	public static final State TEXAS = usaState("TX", "Texas");
	public static final State UTAH = usaState("UT", "Utah");
	public static final State VERMONT = usaState("VT", "Vermont");
	public static final State VIRGINIA = usaState("VA", "Virginia");
	public static final State WASHINGTON = usaState("WA", "Washington");
	public static final State WEST_VIRGINIA = usaState("WV", "West Virginia");
	public static final State WISCONSIN = usaState("WI", "Wisconsin");
	public static final State WYOMING = usaState("WY", "Wyoming");

	public static final State ALBERTA = canadianProvince("AB", "Alberta");
	public static final State BRITISH_COLUMBIA = canadianProvince("BC",
			"British Columbia");
	public static final State MANITOBA = canadianProvince("MB", "Manitoba");
	public static final State NEW_BRUNSWICK = canadianProvince("NB",
			"New Brunswick");
	public static final State NEWFOUNDLAND_AND_LABRADOR = canadianProvince(
			"NL", "Newfoundland and Labrador");
	public static final State NORTHWEST_TERRITORIES = canadianProvince("NT",
			"Northwest Territories");
	public static final State NOVA_SCOTIA = canadianProvince("NS",
			"Nova Scotia");
	public static final State NUNAVUT = canadianProvince("NU", "Nunavut");
	public static final State ONTARIO = canadianProvince("ON", "Ontario");
	public static final State PRINCE_EDWARD_ISLAND = canadianProvince("PE",
			"Prince Edward Island");
	public static final State QUEBEC = canadianProvince("QC", "Quebec");
	public static final State SASKATCHEWAN = canadianProvince("SK",
			"Saskatchewan");
	public static final State YUKON_TERRITORY = canadianProvince("YT",
			"Yukon Territory");

	private final String code;

	private final String name;

	private final Country country;

	public State(String code, String name, Country country) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(code));
		Preconditions.checkNotNull(country);
		this.code = code;
		this.name = name;
		this.country = country;
		states.put(code, this);
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Country getCountry() {
		return country;
	}

	public static State usaState(String code, String name) {
		return new State(code, name, Country.UNITED_STATES);
	}

	public static State canadianProvince(String code, String name) {
		return new State(code, name, Country.CANADA);
	}

	public static State valueOf(String code) {
		return states.get(code);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof State)) {
			return false;
		}
		State other = (State) object;
		return (code.equals(other.getCode()) && country.equals(other
				.getCountry()));
	}

	@Override
	public String toString() {
		return name;
	}

}
