package menu.impl;

import java.util.List;
import java.util.Scanner;

import exception.BusinessException;
import exception.NoDataFoundException;
import menu.Menu;
import model.entities.Customer;
import model.entities.Order;
import service.CustomerService;
import service.OrderService;
import service.ServiceFactory;
import util.AppUtils;
import util.ValidationUtils;

public class MenuOrders implements Menu {
	
	private final Scanner sc = new Scanner(System.in);
	private final OrderService orderService = ServiceFactory.createOrderService();
	private final CustomerService customerService = ServiceFactory.createCustomerService();
	private static final int MIN_OPTION = 1;
	private static final int MAX_OPTION = 8;
	private static final int EXIT_OPTION = 8;
	
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
				case 1 -> showAllOrders();
				case 2 -> showOrderDetails();
				case 3 -> showAllCustomerOrders();
				case 4 -> createOrder();
				case 5 -> updateOrderStatus();
				case 6 -> cancelOrder();
				case 7 -> new MenuOrderItems().displayMenu();
				case 8 -> System.out.println("Returning to main menu...");
				default -> System.out.println("Invalid option! Try again");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	private void cancelOrder() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== CANCEL ORDER ===\n");
				System.out.println("Current orders:");
				showAllOrders();
				System.out.println();
				int id = ValidationUtils.getValidId(sc);
				sc.nextLine();
				
				Order order = orderService.findById(id);
				System.out.println("\nOrder details:\n");
				System.out.println(order);
				
				if (AppUtils.confirmAction(sc, "cancel this order")) {
		            order.setStatus("canceled");
		            orderService.update(order);
		            System.out.println("Order successfully canceled!");
		            exitLoop = true;
		        } else {
		            System.out.println("Cancellation aborted!");
		            exitLoop = true;
		        }
			} catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
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

	private void updateOrderStatus() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== UPDATE ORDER STATUS ===\n");
				System.out.println("Current orders:");
				showAllOrders();
				System.out.println();
				int id = ValidationUtils.getValidId(sc);
				sc.nextLine();
				
				Order order = orderService.findById(id);
				System.out.println("\n=== Order details ===\n");
				System.out.println(order);
				
				String newStatus = ValidationUtils.getValidStatus(sc, "Enter new status (Options: pending, paid, shipped, delivered, canceled): ");
				
				order.setStatus(newStatus);
				orderService.update(order);
				System.out.println("Order updated successfully! ");
				exitLoop = true;
			}catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
			}
			catch(BusinessException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different order status?");
			}
			catch(Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);

	}

	private void createOrder() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== CREATE NEW ORDER ===\n");
				System.out.println("Here is the current list of orders:");
				showAllOrders();
				System.out.println();
				
				String orderNumber = ValidationUtils.getValidOrderNumber(sc, "Enter order number (e.g., ORD-2025-NNN, where NNN are 3 digits): ");
				
				List<Customer> customers = customerService.findAll();
				
				if(customers.isEmpty()) {
					System.out.println("No customers registered! Please add a customer first to search for orders");
					exitLoop = true;
					return;
				}
				
				System.out.println("\nAvailable Customers:");
				
				AppUtils.listAll(customers);
				System.out.println();
				int customerId = ValidationUtils.getValidId(sc);
				sc.nextLine();
				Customer customer = customerService.findById(customerId);
				
				Order order = new Order(null, orderNumber, customer);
				orderService.insert(order);
				System.out.println("Inserted! New id = " + order.getId());
				exitLoop = true;
			}
			catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
			}
			catch(BusinessException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different order number?");
			}
			catch(Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
	}

	private void showAllCustomerOrders() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== SHOW ALL ORDERS FOR A CUSTOMER ===\n");
				List<Customer> customers = customerService.findAll();
				if(customers.isEmpty()) {
					System.out.println("No customers registered! Please add a customer first to search for orders");
					exitLoop = true;
					return;
				}
				
				System.out.println("Available customers:");
				AppUtils.listAll(customers);
				System.out.println();
				
				int customerId = ValidationUtils.getValidId(sc);
				sc.nextLine();
				
				System.out.println("\nCustomer details:");
				
				Customer customer = customerService.findById(customerId);
				System.out.println(customer);
				
				List<Order> customerOrders = orderService.findByCustomerId(customerId);
				exitLoop = true;
				
				if(customerOrders.isEmpty()) {
					System.out.println("\nNo orders found for this customer!");
				}
				else {
					System.out.println("\n=== Orders for Customer ID: " + customerId + " ===");
					AppUtils.listAll(customerOrders);
				}
			} catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
			}
		} while(!exitLoop);
	}

	private void showOrderDetails() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== VIEW ORDER ===\n");
				System.out.println("Current orders:");
				showAllOrders();
				System.out.println();
				int id = ValidationUtils.getValidId(sc);
				sc.nextLine();
				Order order = orderService.findById(id);
				System.out.println(order);
				exitLoop = true;
				
			}catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
			}
		} while(!exitLoop);
	}

	private void showAllOrders() {
		System.out.println("\n=== ORDER LIST ===\n");
		System.out.println("Below is the list of all orders:");
		List<Order> list = orderService.findAll();
		if(list.isEmpty()) {
			System.out.println("No orders have been registered yet!");
			return;
		}
		else {
			AppUtils.listAll(list);
		}
	}

	public void showMenuOptions() {
		
		System.out.println("\n=== ORDER MENU ===\n");
		System.out.println("1. List all orders");
		System.out.println("2. View order details");
		System.out.println("3. List customer's order");
		System.out.println("4. Create new order");
		System.out.println("5. Update order status");
		System.out.println("6. Cancel order");
		System.out.println("7. Manage Order Items");
		System.out.println("8. Return to main menu");
		
	}

}
