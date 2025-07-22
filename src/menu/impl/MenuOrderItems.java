package menu.impl;

import java.util.List;
import java.util.Scanner;

import exception.BusinessException;
import exception.NoDataFoundException;
import menu.Menu;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;
import service.OrderItemService;
import service.OrderService;
import service.ProductService;
import service.ServiceFactory;
import util.AppUtils;
import util.ValidationUtils;

public class MenuOrderItems implements Menu {
	
	private final Scanner sc = new Scanner(System.in);
	private final OrderService orderService = ServiceFactory.createOrderService();
	private final OrderItemService orderItemService = ServiceFactory.createOrderItemService();
	private final ProductService productService = ServiceFactory.createProductService();
	private static final int MIN_OPTION = 1;
	private static final int MAX_OPTION = 5;
	private static final int EXIT_OPTION = 5;
	@Override
	public void displayMenu() {
		int choice;
		do {
			showMenuOptions();
			choice = ValidationUtils.getValidChoice(sc, MIN_OPTION, MAX_OPTION);
			sc.nextLine();
			handleChoice(choice);
			
		} while(choice != EXIT_OPTION);
		
	}
	
	public void handleChoice(int choice) {
		try {
			switch(choice) {
				case 1 -> listItemsInOrder();
				case 2 -> addItemToOrder();
				case 3 -> updateItemInOrder();
				case 4 -> deleteItemFromOrder();
				case 5 -> System.out.println("Returning to order menu...");
				default -> System.out.println("Invalid option! Try again");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void deleteItemFromOrder() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== DELETE ITEM FROM AN ORDER ===\n");
				List<Order> orderList = orderService.findAll();
					
				if(orderList.isEmpty()) {
					System.out.println("No orders have been registered yet!");
					exitLoop = true;
					return;
				}
				System.out.println("Below is a list of all registered orders");
				System.out.println("Please review the list and enter the ID of the order you want to modify:\n");
				AppUtils.listAll(orderList);
				System.out.println();
				int orderId = ValidationUtils.getValidId(sc);
				sc.nextLine();
				
				System.out.println("\n=== ITEMS IN ORDER #" + orderId + " ===\n");
				List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
				if(orderItems.isEmpty()) {
					System.out.println("This order does not contain items!");
					exitLoop = true;
					return;
				}
				System.out.println("These are the items currently in the selected order.");
				System.out.println("Please review and choose the item you want to delete:\n");
				AppUtils.listAll(orderItems);
				System.out.println();
				int orderItemId = ValidationUtils.getValidId(sc);
				OrderItem orderItem = orderItemService.findByOrderIdAndOrderItemId(orderId, orderItemId);
				
				System.out.println("\nItem details:\n");
				System.out.println(orderItem);
				
				boolean confirmItemDeletion = AppUtils.confirmAction(sc, "delete this item?");
				
				if(confirmItemDeletion) {
					orderItemService.deleteById(orderItem.getId());
					System.out.println("Item successfully deleted!");
					exitLoop = true;
				}
				else {
					System.out.println("Deletion aborted!");
					exitLoop = true;
				}
				
			}catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID?");
			}
			catch(BusinessException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
	}

	private void updateItemInOrder() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== UPDATE ITEM IN AN ORDER ===\n");
				
				List<Order> orderList = orderService.findAll();
				
				if(orderList.isEmpty()) {
					System.out.println("No orders have been registered yet!");
					exitLoop = true;
					return;
				}
				System.out.println("Select the order that contains the item you want to update:\n");
				AppUtils.listAll(orderList);
				System.out.println();
				int orderId = ValidationUtils.getValidId(sc);
				sc.nextLine();
				
				System.out.println("\n=== ITEMS IN ORDER #" + orderId + " ===\n");
				List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
				if(orderItems.isEmpty()) {
					System.out.println("This order does not contain items!");
					exitLoop = true;
					return;
				}
				
				System.out.println("Here are the items in the selected order:");
				AppUtils.listAll(orderItems);
				int orderItemId = ValidationUtils.getValidId(sc);
				sc.nextLine();
				OrderItem orderItem = orderItemService.findByOrderIdAndOrderItemId(orderId, orderItemId);
				
				List<Product> productList = productService.findAll();
				if(productList.isEmpty()) {
					System.out.println("No products have been registered yet!");
					exitLoop = true;
					return;
				}
				
				Product product = orderItem.getProduct();
				int quantity = orderItem.getQuantity();
				
				boolean confirmUpdateProduct = AppUtils.confirmAction(sc, "update the product?");
				if(confirmUpdateProduct) {
					System.out.println("\n=== AVAILABLE PRODUCTS ===\n");
					AppUtils.listAll(productList);
					
					System.out.println();
					System.out.println("Current product in the order: " + orderItem.getProduct().getName());
					
					System.out.print("\nEnter the ID of the new product\n");
					int productId = ValidationUtils.getValidId(sc);
					sc.nextLine();
					
					product = productService.findById(productId);
				}
				
				boolean confirmUpdateQuantity = AppUtils.confirmAction(sc, "update the quantity?");
				if(confirmUpdateQuantity) {
					System.out.println("Current quantity: " + orderItem.getQuantity());
					quantity = ValidationUtils.getValidQuantity(sc, "Enter the new quantity for this item: ");
				}
				
				orderItem.setProduct(product);
				orderItem.setQuantity(quantity);
				orderItemService.update(orderItem);
				System.out.println("Item updated successfully!");
				exitLoop = true;
			
			} catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID?");
			}
			catch(BusinessException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
		
	}

	private void addItemToOrder() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== ADD ITEM TO AN ORDER ===\n");
				
				List<Product> productList = productService.findAll();
				
				if(productList.isEmpty()) {
					System.out.println("No products have been registered yet!");
					exitLoop = true;
					return;
				}
				
				System.out.println("Choose the product you want to add to an order:\n");
				AppUtils.listAll(productList);
				System.out.println();
				int productId = ValidationUtils.getValidId(sc);
				sc.nextLine();
				
				Product product = productService.findById(productId);
				System.out.println("\nProduct details:\n");
				System.out.println(product);
				
				List<Order> orderList = orderService.findAll();
				if(orderList.isEmpty()) {
					System.out.println("No orders have been registered yet!");
					exitLoop = true;
					return;
				}
				System.out.println("\nNow choose the order to which this product should be added:\n");
				AppUtils.listAll(orderList);
				int orderId = ValidationUtils.getValidId(sc);
				sc.nextLine();
				
				Order order = orderService.findById(orderId);
				System.out.println("\nOrder details:\n");
				System.out.println(order);
				
				int quantity = ValidationUtils.getValidQuantity(sc, "Please enter the quantity of items (e.g., 1, 2, 5): ");
				
				OrderItem orderItem = new OrderItem(null, product, order, quantity);
				orderItemService.insert(orderItem);
				System.out.println("Inserted! New id = " + orderItem.getId());
				exitLoop = true;
				
			} catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID?");
			}
			catch(BusinessException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
	}

	private void listItemsInOrder() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== LIST ITEMS IN AN ORDER ===\n");
				List<Order> orderList = orderService.findAll();
				if(orderList.isEmpty()) {
					System.out.println("No orders have been registered yet!");
					exitLoop = true;
					return;
				}
				
				System.out.println("Here is a list of all registered orders:");
				AppUtils.listAll(orderList);
				System.out.println();
				int id = ValidationUtils.getValidId(sc);
				sc.nextLine();
				
				Order order = orderService.findById(id);
				System.out.println("\nOrder details: \n");
				System.out.println(order);
				
				List<OrderItem> orderItems = orderItemService.findByOrderId(order.getId());
				exitLoop = true;
				if(orderItems.isEmpty()) {
					System.out.println("This order does not contain items!");
				}
				else {
					System.out.println("\nThese are the items in this order:\n");
					AppUtils.listAll(orderItems);
				}

			} catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID?");
			}
		} while(!exitLoop);
	}

	public void showMenuOptions() {
		
		System.out.println("\n=== ORDER ITEMS MENU ===\n");
		
		System.out.println("1. List items in an order");
		System.out.println("2. Add item to an order");
		System.out.println("3. Update item in an order");
		System.out.println("4. Delete item in an order");
		System.out.println("5. Return to order menu");
	}

}
