package util;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.dao.OrderDao;
import model.dao.OrderItemDao;
import model.dao.ProductDao;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class OrderItemValidator {
	
	public static Order getValidOrder(Scanner sc, OrderDao orderDao) {
		Order order = null;
		int orderId = 0;
		boolean isValidOrder = false;
		
		do {
			try {
				System.out.println("Enter an existing order id: ");
				orderId = sc.nextInt();
				sc.nextLine();
				
				if(orderId <= 0) {
					System.out.println("Id can only be positive! ");
				}
				else {
					order = orderDao.findById(orderId);
					if(order == null) {
						System.out.println("No order found with that id!");
					}
					else if(!(order.getStatus().equals("pending"))){
						System.out.println("This operation is only allowed for pending orders! ");
					}
					else {
						isValidOrder = true;
					}
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
				sc.nextLine();
			}
			
		}while(!isValidOrder);
		
		return order;
	}

	public static OrderItem getValidItem(Scanner sc, OrderItemDao orderItemDao, Order order) {
		boolean isValidItem = false;
		int itemId = 0;
		OrderItem item = null;
		do {
			try {
				System.out.println("Enter item ID: ");
				itemId = sc.nextInt();
				sc.nextLine();
					
				if(itemId <= 0) {
					System.out.println("Id can only be positive (e.g., 1, 2, 10)");
				}
				else {
					item = orderItemDao.findById(itemId);
					if(item == null) {
						System.out.println("There is no item with that id");
					}
					else if(!(orderItemDao.findByOrderId(order.getId()).contains(item))) {
						System.out.println("This item is not in the order");
					}
					else {
						isValidItem = true;
					}
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
				sc.nextLine();
			}
				
		}while(!isValidItem);
		
		return item;
	}

	public static Product getValidProduct(Scanner sc, OrderItem item, ProductDao productDao) {
		int productId = 0;
		Product product = null;
		boolean isValidProduct = false;
		do {
			
			if(item == null) {
				System.out.println("Enter a valid id: ");
			}
			else {
				System.out.println("Enter new product id (" + item.getProduct().getId() + ") - leave empty to keep current: ");
			}
			String inputProductId = sc.nextLine().trim();
			
			if(item != null && inputProductId.isEmpty()) {
				productId = item.getProduct().getId();
				product = productDao.findById(productId);
				isValidProduct = true;
				break;
			}
			else if(item == null && inputProductId.isEmpty()) {
				System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
			}
			else {
				try {
					productId = Integer.parseInt(inputProductId);
					if(productId <= 0) {
						System.out.println("Id can only be positive (e.g., 1, 2, 10)");
					}
					else {
						product = productDao.findById(productId);
						if(product == null) {
							System.out.println("No product found with the specified ID");
						}
						else if(product.getInventory() == 0) {
							System.out.println("Product unavailable. This item is currently out of stock");
						}
						else {
							isValidProduct = true;
						}
						
					}
				}
				catch(NumberFormatException e) {
					System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
					
				}
			}
		} while(!isValidProduct);
		
		return product;
	}

	public static int getValidQuantity(Scanner sc, OrderItem item, Product product) {
		boolean isValidQuantity = false;
		int quantity = 0;
		String quantityString;
		do {
			if(item == null) {
				System.out.println("Please enter the quantity of items (e.g., 1, 2, 5): ");
			}
			else {
				System.out.println("Enter new quantity (" + item.getQuantity() + ")  - leave empty to keep current: ");
			}
			
			quantityString = sc.nextLine();
			System.out.println(quantityString);
			
			if(item != null && quantityString.trim().isEmpty()) {
				quantity = item.getQuantity();
				isValidQuantity = true;
			}
			else {
				try {
					quantity = Integer.parseInt(quantityString);
					
					if(quantity < 0) {
						System.out.println("Invalid quantity! The value cannot be negative");
					}
					else if(quantity > product.getInventory()) {
						System.out.println("Invalid quantity! Only " + product.getInventory() + " units available in stock");
					}
					else {
						isValidQuantity = true;
					}
				}
				catch(NumberFormatException e) {
					System.out.println("Invalid input! Please enter a whole number (e.g., 1, 5, 10)");
				}
			}
			
			
		} while(!isValidQuantity);
		
		return quantity;


	}

}
