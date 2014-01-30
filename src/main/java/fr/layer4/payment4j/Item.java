package fr.layer4.payment4j;

public class Item {

	private String id;

	private String name;

	private String description;

	private java.math.BigDecimal quantity;

	private java.math.BigDecimal price;

	private boolean taxable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.math.BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(java.math.BigDecimal quantity) {
		this.quantity = quantity;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public boolean isTaxable() {
		return taxable;
	}

	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}

}
