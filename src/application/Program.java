package application;

import java.time.LocalDateTime;

import model.entities.Customer;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class Program {

	public static void main(String[] args) {
		
		 Customer customer = new Customer(1, "João", "Silva", "joao@example.com", "99999999", "Rua A");
		 
		 Order order = new Order(1, "001", "pending", LocalDateTime.now(), customer);
		 
		 customer.addOrder(order);
		 
		  Product product1 = new Product(1, "Notebook","a notebook","notebook",3000.0, 10);
	      Product product2 = new Product(2, "Mouse", "a mouse", "mouse", 100.0, 50);
	       
	       OrderItem item1 = new OrderItem(1, product1, order, 2);
	       order.addOrderItem(item1);
	       OrderItem item2 = new OrderItem(2, product2, order, 1); 
	       order.addOrderItem(item2);
	       
	       System.out.println(order);
	       System.out.println(item1);
	       System.out.println(item2);
	       System.out.println(order.getTotalAmount());
	}

}
