package model.dao;

import java.util.List;

import model.entities.Order;

public interface OrderDao {
	
	void insert(Order order);
	void update(Order order);
	Order findById(Integer id);
	List<Order> findAll();
	List<Order> findByCustomerId(Integer customerId);
	
	
}
