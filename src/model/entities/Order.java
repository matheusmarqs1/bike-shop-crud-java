package model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	private Integer id;
	private String orderNumber;
	private Double totalAmount;
	private String status;
	private LocalDateTime datetime;
	
	private Customer customer;
	
	private List<OrderItem> orderItems = new ArrayList<>();
	
	public Order() {
	}

	public Order(Integer id, String orderNumber, String status, LocalDateTime datetime, Customer customer) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.status = status;
		this.datetime = datetime;
		this.customer = customer;
		this.totalAmount = 0.0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	
	public void addOrderItem(OrderItem item) {
		if(item == null) {
			throw new  IllegalArgumentException("Order Item cannot be null");
		}
		if(orderItems.contains(item)) {
			throw new  IllegalArgumentException("Order Item already exists in the list");
		}
		for(OrderItem existingItem : orderItems) {
			if(existingItem.getProduct().equals(item.getProduct())) {
				existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
				recalculateTotalAmount();
				return;
			}
		}
		item.setOrder(this);
		orderItems.add(item);
		recalculateTotalAmount();
		
	}
	
	public void removeOrderItem(OrderItem item) {
		if(item == null) {
			throw new  IllegalArgumentException("Order Item cannot be null");
		}
		if(orderItems.remove(item)) {
			item.setOrder(null);
		}
	}
	
	public void recalculateTotalAmount() {
		double sum = 0.0;
		for(OrderItem item : orderItems) {
			sum += item.getQuantity() * item.getUnit_price();
		}
		totalAmount = sum;
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
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + 
				", orderNumber=" + orderNumber + 
				", totalAmount=" + totalAmount + 
				", status=" + status + 
				", datetime=" + datetime.format(dtf) + 
				", customerId=" + (customer != null ? customer.getId() : "null") + "]";
	}

}
