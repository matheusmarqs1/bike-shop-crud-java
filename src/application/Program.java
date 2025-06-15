package application;

import java.time.LocalDateTime;

import model.entities.Customer;
import model.entities.Order;

public class Program {

	public static void main(String[] args) {
		
		Customer customer = new Customer(1, "Lucas", "Fernandes", "lucasfernandes@example.com", "6290000000", "202 Avenida Anhanguera, Setor Campinas, Goiânia, Goiás");
		System.out.println(customer);
		
		Order order = new Order(1, "ORD-2025-011", 399.58, "pending",LocalDateTime.now(), customer);
		System.out.println(order);
		
		customer.addOrder(order);
		
		System.out.println("after inserting the order into the list");
		System.out.println(customer);
		System.out.println(order);
		customer.removeOrder(order);
		System.out.println("after removing order from list");
		System.out.println(customer);
		System.out.println(order);
	}

}
