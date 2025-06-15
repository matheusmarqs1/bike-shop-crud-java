package model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	public Order() {
	}

	public Order(Integer id, String orderNumber, Double totalAmount, String status, LocalDateTime datetime,
			Customer customer) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.totalAmount = totalAmount;
		this.status = status;
		this.datetime = datetime;
		this.customer = customer;
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

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
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
		String customerID = (customer != null) ? ", customerID=" + customer.getId() : "";
		
		return "Order [id=" + id + ", orderNumber=" + orderNumber + ", totalAmount=" + totalAmount + ", status="
					+ status + ", datetime=" + datetime.format(dtf) + customerID + "]";
	}

}
