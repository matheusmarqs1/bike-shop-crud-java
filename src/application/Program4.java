package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.entities.Customer;
import model.entities.Order;

public class Program4 {

	public static void main(String[] args) {
		
		OrderDao orderDao = DaoFactory.createOrderDao();
		
		System.out.println("==== TEST 1: order findById ====");
		Order order = orderDao.findById(10);
		System.out.println(order);
		
		System.out.println("\n==== TEST 2: order findAll ====");
		List<Order> list = new ArrayList<>();
		list = orderDao.findAll();
		for(Order obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n==== TEST 3: order findByCustomerId ====");
		list = orderDao.findByCustomerId(5);
		for(Order obj : list) {
			System.out.println(obj);
		}
		
		
		System.out.println("\n==== TEST 4: order insert ====");
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		Customer customer = customerDao.findById(12);
		Order newOrder = new Order(null, "ORD-2025-012", customer);
		orderDao.insert(newOrder);
		System.out.println("Inserted! New id = " + newOrder.getId());
		
		
		System.out.println("\n==== TEST 5: order update ====");
		order.setStatus("paid");
		orderDao.update(order);
		System.out.println("Update completed!");
	
	}

}
