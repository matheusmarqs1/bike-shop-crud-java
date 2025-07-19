package menu.impl;

import java.util.List;
import java.util.Scanner;

import menu.Menu;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.dao.OrderItemDao;
import model.dao.ProductDao;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;
import model.validators.OrderItemValidator;
import util.ValidationUtils;

public class MenuOrderItems implements Menu {

	@Override
	public void displayMenu(Scanner sc) {
		
		OrderItemDao orderItemDao = DaoFactory.createOrderItemDao();
		OrderDao orderDao = DaoFactory.createOrderDao();
		ProductDao productDao = DaoFactory.createProductDao();
		int choice;
		
		
		do {
			System.out.println("=== ORDER ITEMS MENU ===");
			
			System.out.println("1. List items in an order");
			System.out.println("2. Add item to an order");
			System.out.println("3. Update item in an order");
			System.out.println("4. Delete item in an order");
			System.out.println("5. Return to order menu");
			
			choice = ValidationUtils.getValidChoice(sc, 1, 5);
			sc.nextLine();
			
			switch(choice) {
				
				case 1:
					System.out.println("=== LIST ITEMS IN AN ORDER ===");
					for(Order o : orderDao.findAll()) {
						System.out.println(o);
					}
					int orderId = ValidationUtils.getValidId(sc);
					sc.nextLine();
					Order order = orderDao.findById(orderId);
					
					if(order == null) {
						System.out.println("No order found with that id! ");
					}
					else {
						System.out.println("Order details: ");
						System.out.println(order);
						
						List<OrderItem> list = orderItemDao.findByOrderId(order.getId());
						
						if(list.isEmpty()) {
							System.out.println("This order does not contain items");
						}
						else {
							System.out.println("Itens details: ");
							for(OrderItem item : list) {
								System.out.println(item);
							}
						}
					}
					break;
					
				case 2:
					System.out.println("=== ADD ITEM TO AN ORDER ===");
					OrderItem orderItem = null;
					
					for(Product p : productDao.findAll()) {
						System.out.println(p);
					}
							
					Product addProduct = OrderItemValidator.getValidProduct(sc, orderItem, productDao);
					
					System.out.println("Product details: ");
					System.out.println(addProduct);
						
					for(Order o : orderDao.findAll()) {
						System.out.println(o);
					}
					
					
					Order addOrder = OrderItemValidator.getValidOrder(sc, orderDao);
					
					System.out.println("Order details: ");
					System.out.println(addOrder);
					
					int quantity = OrderItemValidator.getValidQuantity(sc, orderItem, addProduct);
	
					orderItem = new OrderItem(null, addProduct, addOrder, quantity);
					orderItemDao.insert(orderItem);
					System.out.println("Inserted! New id = " + orderItem.getId());	
					
					break;
					
				case 3:
					System.out.println("=== UPDATE ITEM IN AN ORDER ===");
					
					for(Order o : orderDao.findAll()) {
						System.out.println(o);
					}
					
					Order updateOrder = OrderItemValidator.getValidOrder(sc, orderDao);

						
					List<OrderItem> list = orderItemDao.findByOrderId(updateOrder.getId());
					
					if(list.isEmpty()) {
						System.out.println("This order does not contain items");
					}
					else {
						System.out.println("ITEMS FROM " + updateOrder.getOrderNumber());
						
						for(OrderItem item : list) {
							System.out.println(item);
						}
						OrderItem updateItem = OrderItemValidator.getValidItem(sc, orderItemDao, updateOrder);
						
						for(Product p : productDao.findAll()) {
								System.out.println(p);
						}

						Product updateProduct = OrderItemValidator.getValidProduct(sc, updateItem, productDao);
						
						int updateQuantity = OrderItemValidator.getValidQuantity(sc, updateItem, updateProduct);
						
						updateItem.setProduct(updateProduct);
						updateItem.setQuantity(updateQuantity);
						orderItemDao.update(updateItem);
						System.out.println("Item updated successfully!");

						
					}	
					
					break;
				
				
				case 4:
					
					System.out.println("=== DELETE ITEM IN AN ORDER ===");

					for(Order o : orderDao.findAll()) {
						System.out.println(o);
					}
					
				
					Order deleteOrder = OrderItemValidator.getValidOrder(sc, orderDao);
					
					List<OrderItem> itemsList = orderItemDao.findByOrderId(deleteOrder.getId());
					
					if(itemsList.isEmpty()) {
						System.out.println("This order does not contain items");
					}
						
					else {
							
						System.out.println("ITEMS FROM " + deleteOrder.getOrderNumber());
						for(OrderItem item : itemsList) {
							System.out.println(item);
						}
						
						OrderItem deleteItem = OrderItemValidator.getValidItem(sc, orderItemDao, deleteOrder);
						
						System.out.print("Are you sure you want to delete this item? (y/n): ");
						String confirm = sc.nextLine();
						
						if(confirm.equalsIgnoreCase("y")) {
							orderItemDao.deleteById(deleteItem.getId());
							System.out.println("Item successfully deleted!");
						}
						else {
							System.out.println("Deletion aborted!");
						}

					}
					break;
					
				case 5:
					System.out.println("Returning to order menu...");
					break;
					
				default:
					System.out.println("Invalid option!");
					break;
					
			}
			
		} while(choice != 5);
		
	}

}
