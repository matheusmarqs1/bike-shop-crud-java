package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class OrderItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Product product;
	private Order order;
	private Integer quantity;
	private Double unit_price;
	
	public OrderItem() {
	}

	public OrderItem(Integer id, Product product, Order order, Integer quantity) {
		this.id = id;
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.unit_price = product.getPrice();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnit_price() {
		return unit_price;
	}
	
	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
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
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + 
				", productId=" + (product != null ? product.getId() : "null") + 
				", orderId=" + (order != null ? order.getId() : "null") + 
				", quantity=" + quantity + 
				", unit_price=" + unit_price + "]";
	}

}
