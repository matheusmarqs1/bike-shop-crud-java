package service;

import service.impl.CustomerServiceImpl;
import service.impl.OrderItemServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ProductServiceImpl;

public class ServiceFactory {
	
	public static CustomerService createCustomerService() {
		return new CustomerServiceImpl();
	}
	
	public static ProductService createProductService() {
		return new ProductServiceImpl();
	}
	
	public static OrderService createOrderService() {
		return new OrderServiceImpl();
	}
	
	public static OrderItemService createOrderItemService() {
		return new OrderItemServiceImpl();
	}
	
}
