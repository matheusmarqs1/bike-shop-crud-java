package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String first_name;
	private String last_name;
	private String email;
	private String telephone;
	private String address;
	
	private List<Order> orders = new ArrayList<>();
	
	public Customer() {
	}

	public Customer(Integer id, String first_name, String last_name, String email, String telephone, String address) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.telephone = telephone;
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public void addOrder(Order order) {
		if(order == null) {
			throw new  IllegalArgumentException("Order cannot be null");
		}
		if(order.getCustomer() != null && order.getCustomer() != this) {
			throw new IllegalStateException("This order is already associated with another customer");
		}
		if(orders.contains(order)) {
			throw new IllegalArgumentException("Order already exists in the list");
		}
		order.setCustomer(this);
		orders.add(order);
	}
	
	public void removeOrder(Order order) {
		if(order == null) {
			throw new  IllegalArgumentException("Order cannot be null");
		}
		if(orders.remove(order)) {
			order.setCustomer(null);
		}
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
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + 
				", first_name=" + first_name + 
				", last_name=" + last_name + 
				", email=" + email + 
				", telephone=" + telephone + 
				", address=" + address + "]";
	}

	
	
}
