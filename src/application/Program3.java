package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.dao.OrderItemDao;
import model.dao.ProductDao;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class Program3 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		OrderItemDao orderItemDao = DaoFactory.createOrderItemDao();
		
		System.out.println("==== TEST 1: orderItem findByOrderId ====");
		
		List<OrderItem> list = new ArrayList<>();
		list = orderItemDao.findByOrderId(7);
		for(OrderItem item : list) {
			System.out.println(item);
		}
		
		System.out.println("\n==== TEST 2: orderItem insert ====");
		ProductDao productDao = DaoFactory.createProductDao();
		Product p = productDao.findById(1);
		
		OrderDao orderDao = DaoFactory.createOrderDao();
		Order o = orderDao.findById(10);
		
		OrderItem newItem = new OrderItem(null, p, o, 1);
		
		orderItemDao.insert(newItem);
		System.out.println("Inserted! New id = " + newItem.getId());
		
		
		System.out.println("\n==== TEST 3: orderItem findById ====");
		OrderItem oi = orderItemDao.findById(2);
		System.out.println(oi);
		
		System.out.println("\n==== TEST 4: orderItem update ====");
		p = productDao.findById(7);
		
		oi.setQuantity(2);
		oi.setProduct(p);
		orderItemDao.update(oi);
		System.out.println("Update completed!");
		
		System.out.println("\n==== TEST 5: orderItem deleteById ====");
		System.out.println("Enter id for delete: ");
		int id = sc.nextInt();
		orderItemDao.deleteById(id);
		System.out.println("Delete completed!");
		
		sc.close();
	
	}

}
