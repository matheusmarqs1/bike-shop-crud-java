package service;

import java.util.List;

import model.entities.OrderItem;

public interface OrderItemService {
	
	void insert(OrderItem orderItem);
	void update(OrderItem orderItem);
	void deleteById(Integer id);
	List<OrderItem> findByOrderId(Integer id);
	OrderItem findById(Integer id);
	OrderItem findByOrderIdAndOrderItemId(Integer orderId, Integer orderItemId);
}
