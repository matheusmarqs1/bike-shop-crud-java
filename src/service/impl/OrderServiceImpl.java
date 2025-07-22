package service.impl;

import java.util.List;

import exception.BusinessException;
import exception.NoDataFoundException;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.entities.Order;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
	
	private final OrderDao orderDao = DaoFactory.createOrderDao();
	
	@Override
	public void insert(Order order) {
		if(order == null) {
			throw new IllegalArgumentException("Order cannot be null");
		}
		validateOrderNumberUniqueness(order.getOrderNumber(), order.getId());
		orderDao.insert(order);
		
	}

	@Override
	public void update(Order order) {
		if(order == null) {
			throw new IllegalArgumentException("Order cannot be null");
		}
		
		Order originalOrder = orderDao.findById(order.getId());
		
		if(originalOrder == null) {
			throw new NoDataFoundException("Order not found with that id: " + order.getId());
		}
		
		validateStatusTransition(order.getStatus(), originalOrder.getStatus(), order.getTotalAmount());
		order.setStatus(order.getStatus().toLowerCase());
		orderDao.update(order);
		
	}

	@Override
	public Order findById(Integer id) {
		
		if(id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		Order order = orderDao.findById(id);
		if(order == null) {
			throw new NoDataFoundException("Order not found with that id: " + id);
		}
		return order;
	}

	@Override
	public List<Order> findAll() {
		return orderDao.findAll();
	}

	@Override
	public List<Order> findByCustomerId(Integer customerId) {
		if(customerId == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		List<Order> list = orderDao.findByCustomerId(customerId);
		
		return list;
	}
	
	private void validateOrderNumberUniqueness(String orderNumber, Integer orderId) {
		List<Order> list = orderDao.findAll();
		for(Order o : list) {
			if(o.getOrderNumber().equals(orderNumber) && orderId == null) {
				throw new BusinessException("Order number already exists!");
			}
		}
		
	}
	
	private void validateStatusTransition(String newStatus, String currentStatus, Double totalAmount) {
		if("delivered".equalsIgnoreCase(currentStatus) || "canceled".equalsIgnoreCase(currentStatus)) {
			throw new BusinessException("Order status '" + currentStatus + "' cannot be changed. It is a final status");
		}
		if(totalAmount == null || totalAmount == 0.0) {
			if("pending".equalsIgnoreCase(currentStatus) && !"canceled".equalsIgnoreCase(newStatus)) {
				 throw new BusinessException("Order cannot progress from 'pending' to '" + newStatus.toLowerCase() + "' with a zero total amount. Please add items to the order or cancel it");
			}
			if(!"pending".equalsIgnoreCase(currentStatus) && !"canceled".equalsIgnoreCase(newStatus)) {
				throw new BusinessException("Order status cannot be updated to '" + newStatus + "' as its total amount is zero. An order must have items to progress");
			}
		}
		
		switch(currentStatus) {
			case "pending":
				if(!"paid".equalsIgnoreCase(newStatus) && !"canceled".equalsIgnoreCase(newStatus)) {
					throw new BusinessException("Order status 'pending' can only be updated to 'paid' or 'canceled' ");
				}
				break;
			
			case "paid":
				if(!"shipped".equalsIgnoreCase(newStatus) && !"canceled".equalsIgnoreCase(newStatus)) {
					throw new BusinessException("Order status 'paid' can only be updated to 'shipped' or 'canceled' ");
				}
				break;
			
			case "shipped":
				if(!"delivered".equalsIgnoreCase(newStatus) && !"canceled".equalsIgnoreCase(newStatus)) {
					throw new BusinessException("Order status 'shipped' can only be updated to 'delivered' or 'canceled' ");
				}
				break;
			
			default:
				throw new BusinessException("Invalid current order status: " + currentStatus);
			
		}
	}
	

}
