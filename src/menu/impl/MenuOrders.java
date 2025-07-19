package menu.impl;

import java.util.List;
import java.util.Scanner;

import menu.Menu;
import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.entities.Customer;
import model.entities.Order;
import model.validators.OrderValidator;
import util.AppUtils;
import util.ValidationUtils;

public class MenuOrders implements Menu {

	@Override
	public void displayMenu(Scanner sc) {
		
		OrderDao orderDao = DaoFactory.createOrderDao();
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		int choice;
		
			do {
				System.out.println("=== ORDER MENU ===");
				System.out.println("1. List all orders");
				System.out.println("2. View order details");
				System.out.println("3. List customer's order");
				System.out.println("4. Create new order");
				System.out.println("5. Update order status");
				System.out.println("6. Cancel order");
				System.out.println("7. Manage Order Items");
				System.out.println("8. Return to main menu");
				
				choice = ValidationUtils.getValidChoice(sc, 1, 8);
				sc.nextLine();
				
				switch(choice) {
					
					case 1:
						AppUtils.listAll(orderDao.findAll(), "order");
						break;
						
					case 2:
						AppUtils.searchByIdAndDisplay(sc, orderDao.findAll(), orderDao::findById, "order");
						break;
						
					case 3:
						System.out.println("=== LIST CUSTOMER'S ORDER ===");
						for(Customer c : customerDao.findAll()) {
							System.out.println(c);
						}
						int customerId = ValidationUtils.getValidId(sc);
						sc.nextLine();
						
						List<Order> orderList = orderDao.findByCustomerId(customerId);
						
						if(orderList.isEmpty()) {
							System.out.println("No orders found for this customer!");
						}
						else {
							for(Order obj : orderList) {
								System.out.println(obj);
							}
						}
						
						break;
						
					case 4:
						System.out.println("=== CREATE NEW ORDER ===");
						for(Order o : orderDao.findAll()) {
							System.out.println(o);
						}
						String orderNumber = OrderValidator.getValidOrderNumber(sc, orderDao);
						for(Customer c : customerDao.findAll()) {
							System.out.println(c);
						}
						int orderCustomerId = ValidationUtils.getValidId(sc);
						Customer customer = customerDao.findById(orderCustomerId);
							
						if(customer == null) {
							System.out.println("No customer found with that id!");
						}
						else {
							Order newOrder = new Order(null, orderNumber, customer);
							orderDao.insert(newOrder);
							System.out.println("Inserted! New id = " + newOrder.getId());
						}
						
						break;
						
					case 5:
						System.out.println("=== UPDATE ORDER STATUS ===");
						
						for(Order o : orderDao.findAll()) {
							System.out.println(o);
						}
						int updateOrderId = ValidationUtils.getValidId(sc);
						sc.nextLine();
						Order updateOrder = orderDao.findById(updateOrderId);
						
						if(updateOrder == null) {
							System.out.println("No order found with that id!");
						}
						else {
							
							System.out.println("Order details:");
							System.out.println(updateOrder);
							
							String newStatus = OrderValidator.getValidStatus(sc, updateOrder);
							
							if(updateOrder.getTotalAmount() == 0 && !(newStatus.equals("canceled"))) {
								System.out.println("The order total is $0. The status can only be updated to 'canceled' ");
								break;
							}
							updateOrder.setStatus(newStatus);
							orderDao.update(updateOrder);
							System.out.println("Order updated successfully! ");
						}
						
						break;
						
					case 6:
						System.out.println("=== CANCEL ORDER ===");
						for(Order o : orderDao.findAll()) {
							System.out.println(o);
						}
						int cancelOrderId = ValidationUtils.getValidId(sc);
						sc.nextLine();
						
						Order cancelOrder = orderDao.findById(cancelOrderId);
						if(cancelOrder == null) {
							System.out.println("No order found with that id! ");
						}
						else {
							if("canceled".equals(cancelOrder.getStatus())) {
								System.out.println("This order is already canceled!");
								break;
							}
							if("delivered".equals(cancelOrder.getStatus())) {
								System.out.println("Cannot cancel a delivered order!");
								break;
							}
							if("shipped".equals(cancelOrder.getStatus())) {
								System.out.println("Cannot cancel a shipped order!");
								break;
							}
							
							System.out.println("Order details:");
							System.out.println(cancelOrder);
							System.out.print("Are you sure you want to cancel this order? (y/n): ");
							String confirm = sc.nextLine();
							
							if(confirm.equalsIgnoreCase("y")) {
								cancelOrder.setStatus("canceled");
								orderDao.update(cancelOrder);
								System.out.println("Order successfully canceled!");
							}
							else {
								System.out.println("Cancellation aborted!");
							}
							
						}
						break;
						
					case 7:
						new MenuOrderItems().displayMenu(sc);
						break;
						
					case 8 :
						System.out.println("Returning to main menu...");
						break;
						
					default:
						System.out.println("Invalid option!");
						break;
			}
		} while(choice != 8);
		
	}

}
