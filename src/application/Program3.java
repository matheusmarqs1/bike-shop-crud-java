package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.dao.OrderItemDao;
import model.dao.ProductDao;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class Program3 {

	public static void main(String[] args) {
		
		
		OrderItemDao orderItemDao = DaoFactory.createOrderItemDao();
		
		System.out.println("==== TEST 1: orderItem findByOrderId ====");
		
		List<OrderItem> list = new ArrayList<>();
		list = orderItemDao.findByOrderId(7);
		for(OrderItem item : list) {
			System.out.println(item);
		}
		
		System.out.println("==== TEST 2: orderItem insert ====");
		ProductDao productDao = DaoFactory.createProductDao();
		Product p = productDao.findById(1);
		
		OrderDao orderDao = DaoFactory.createOrderDao();
		Order o = orderDao.findById(10);
		
		OrderItem oi = new OrderItem(null, p, o, 1);
		orderItemDao.insert(oi);
		System.out.println("Inserted! New id = " + oi.getId());
		
	}

}
