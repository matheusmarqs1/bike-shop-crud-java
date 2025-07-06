package model.dao;

import java.util.List;

import model.entities.OrderItem;

public interface OrderItemDao {
	
	void insert(OrderItem orderItem);
	void update(OrderItem orderItem);
	void deleteById(Integer id);
	List<OrderItem> findByOrderId(Integer id);
	OrderItem findById(Integer id);

}
