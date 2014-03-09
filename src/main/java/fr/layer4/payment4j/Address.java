package fr.layer4.payment4j;

public class Address {
	
	private String company;

	private String firstName;

	private String lastName;

	private String streetAddress;

	private String city;

	private State state;

	private Country country;

	private String postalCode;

	private String phone;

	public String getCompany() {
		return company;
	}

	public Address setCompany(String company) {
		this.company = company;
		return this;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Address setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Address setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public Address setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
		return this;
	}

	public String getCity() {
		return city;
	}

	public Address setCity(String city) {
		this.city = city;
		return this;
	}

	public State getState() {
		return state;
	}

	public Address setState(State state) {
		this.state = state;
		return this;
	}

	public Country getCountry() {
		return country;
	}

	public Address setCountry(Country country) {
		this.country = country;
		return this;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public Address setPostalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Address setPhone(String phone) {
		this.phone = phone;
		return this;
	}
}
