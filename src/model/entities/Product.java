package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String description;
	private String category;
	private Double price;
	private Integer inventory;
	
	public Product() {
	}

	public Product(Integer id, String name, String description, String category, Double price, Integer inventory) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.inventory = inventory;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + 
				", name=" + name + 
				", description=" + description + 
				", category=" + category + 
				", price=" + price + 
				", inventory=" + inventory + "]";
	}
	
	
}
