package service.impl;

import java.util.List;

import exception.BusinessException;
import exception.NoDataFoundException;
import model.dao.DaoFactory;
import model.dao.OrderItemDao;
import model.entities.OrderItem;
import service.OrderItemService;

public class OrderItemServiceImpl implements OrderItemService {
	
	private final OrderItemDao orderItemDao = DaoFactory.createOrderItemDao();
	
	@Override
	public void insert(OrderItem orderItem) {
		
		if(orderItem == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		validateOrderIsPending(orderItem.getOrder().getStatus());
		validateProductIsAvailableInInventory(orderItem.getProduct().getInventory());
		validateQuantityDoesNotExceedInventory(orderItem.getQuantity(), orderItem.getProduct().getInventory());
		validateItemIsNotDuplicated(orderItem);
		orderItemDao.insert(orderItem);
		
		
	}

	@Override
	public void update(OrderItem orderItem) {
		if(orderItem == null) {
			throw new IllegalArgumentException("Item cannot be null!");
		}
		validateOrderIsPending(orderItem.getOrder().getStatus());
		validateProductIsAvailableInInventory(orderItem.getProduct().getInventory());
		validateQuantityDoesNotExceedInventory(orderItem.getQuantity(), orderItem.getProduct().getInventory());
		validateItemIsNotDuplicated(orderItem);
		orderItemDao.update(orderItem);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OrderItem> findByOrderId(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("ID cannot be null!");
		}
		
		List<OrderItem> orderItems = orderItemDao.findByOrderId(id);
		
		return orderItems;
	}

	@Override
	public OrderItem findById(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("ID cannot be null!");
		}
		OrderItem orderItem = orderItemDao.findById(id);
		
		if(orderItem == null) {
			throw new NoDataFoundException("Item not found with that id: " + id);
		}
		
		return orderItem;
	}
	
	public OrderItem findByOrderIdAndOrderItemId(Integer orderId, Integer orderItemId) {
		if(orderId == null || orderItemId == null) {
			throw new IllegalArgumentException("ID cannot be null!");
		}
		OrderItem orderItem = findById(orderItemId);
		
		if(!orderItem.getOrder().getId().equals(orderId)) {
			throw new BusinessException("Order item with ID " + orderItemId + " does not belong to order with ID " + orderId);
		}
		return orderItem;
	}
	
	private void validateOrderIsPending(String orderStatus) {
		if(!"pending".equals(orderStatus)) {
			throw new BusinessException("Items can only be added to orders with 'pending' status. Current order status: '" + orderStatus + "'");
		}
	}
	
	private void validateProductIsAvailableInInventory(int productInventory) {
		if(productInventory == 0) {
			throw new BusinessException("Cannot add item! Product is out of stock");
		}
	}
	
	private void validateQuantityDoesNotExceedInventory(int quantity, int productInventory) {
		if(quantity > productInventory) {
			throw new BusinessException("Cannot add item. Requested quantity: " + quantity + ", Available inventory: " + productInventory);
		}
	}
	
	private void validateItemIsNotDuplicated(OrderItem orderItem) {
		List<OrderItem> orderItems = orderItemDao.findByOrderId(orderItem.getOrder().getId());
		for(OrderItem item : orderItems) {
			if(!item.getId().equals(orderItem.getId()) && item.getProduct().getId().equals(orderItem.getProduct().getId())) {
				throw new BusinessException("This product is already in the order! Try updating its quantity instead");
			}
		}
	}
	
}
