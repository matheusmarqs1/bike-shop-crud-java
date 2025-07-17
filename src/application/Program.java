package application;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.dao.OrderItemDao;
import model.dao.ProductDao;
import model.entities.Customer;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;
import util.CustomerValidator;
import util.OrderItemValidator;
import util.OrderValidator;
import util.ProductValidator;
import util.ValidationUtils;

public class Program {
	
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		try(Scanner sc = new Scanner(System.in)) {
			int choice;
			do {
				System.out.println("=== MAIN MENU ===");
				System.out.println("1. Manage Customers");
				System.out.println("2. Manage Products");
				System.out.println("3. Manage Orders");
				System.out.println("4. Exit");
				
				choice = ValidationUtils.getValidChoice(sc, 1, 4);
					
				switch(choice) {
					case 1:
						menuCustomers(sc);
						break;
						
					case 2:
						menuProducts(sc);
						break;
						
					case 3:
						menuOrders(sc);
						break;
					
					case 4:
						System.out.println("Leaving...");
						break;
						
					default:
						System.out.println("Invalid option!");
						break;
				}
			}while(choice != 4);
		}
		

	}
	
	
	public static void menuCustomers(Scanner sc) {
		
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		int choice;
		
		do {
			System.out.println("=== CUSTOMER MENU ===");
			System.out.println("1. List all customers");
			System.out.println("2. View customer details");
			System.out.println("3. Add new customer");
			System.out.println("4. Update customer");
			System.out.println("5. Delete customer");
			System.out.println("6. Return to main menu");
			
			choice = ValidationUtils.getValidChoice(sc, 1, 6);
			sc.nextLine();
			
			switch(choice) {
				case 1:
					System.out.println("=== CUSTOMER LIST ===");
					List<Customer> list = customerDao.findAll();
					if(list.isEmpty()) {
						System.out.println("No customers registered!");
					}
					else {
						for(Customer customer : list) {
							System.out.println(customer);
						}
					}
					break;
					
				case 2:
					System.out.println("=== VIEW CUSTOMER DETAILS ===");
					
					for(Customer customer : customerDao.findAll()){
						System.out.println(customer);
					}
					
					int id = ValidationUtils.getValidId(sc);
					sc.nextLine();
					Customer customer = customerDao.findById(id);
					if(customer == null) {
						System.out.println("No customer found with that id!");
					}
					else {
						System.out.println(customer);
					}
					break;
				
				case 3:
					Customer newCustomer = null;
					System.out.println("=== ADD NEW CUSTOMER ===");
					
					String firstName = CustomerValidator.getValidFirstName(sc, newCustomer);
					String lastName = CustomerValidator.getValidLastName(sc, newCustomer);
					String email = CustomerValidator.getValidEmail(sc, newCustomer, customerDao);
					String telephone = CustomerValidator.getValidTelephone(sc, newCustomer);
					
					System.out.println("Enter address: ");
					String address = sc.nextLine();
					
					newCustomer = new Customer(null, firstName, lastName, email, telephone, address);
					customerDao.insert(newCustomer);
					System.out.println("Inserted! New id = " + newCustomer.getId());
					break;
					
				case 4:
					System.out.println("=== UPDATE CUSTOMER ===");
					
					for(Customer obj : customerDao.findAll()){
						System.out.println(obj);
					}
					
					int updateId = ValidationUtils.getValidId(sc);
					sc.nextLine();
					Customer updateCustomer = customerDao.findById(updateId);
					
					
					if(updateCustomer == null) {
						System.out.println("No customer found with that id!");
					}
					else {
						System.out.println("Customer details:");
						System.out.println(updateCustomer);
						
						String newFirstName = CustomerValidator.getValidFirstName(sc, updateCustomer);
						String newLastName = CustomerValidator.getValidLastName(sc, updateCustomer);
						String newEmail = CustomerValidator.getValidEmail(sc, updateCustomer, customerDao);
						String newTelephone = CustomerValidator.getValidTelephone(sc, updateCustomer);

						System.out.println("Enter new address (" + updateCustomer.getAddress() + "): ");
						String newAddress = sc.nextLine();
						
						if(newAddress.trim().isEmpty()) {
							newAddress = updateCustomer.getAddress();
						}
						
						updateCustomer.setFirst_name(newFirstName);
						updateCustomer.setLast_name(newLastName);
						updateCustomer.setEmail(newEmail);
						updateCustomer.setTelephone(newTelephone);
						updateCustomer.setAddress(newAddress);
						
						customerDao.update(updateCustomer);
						System.out.println("Customer updated successfully!");
						
					}
					break;
					
				case 5:
					System.out.println("=== DELETE CUSTOMER ===");
					for(Customer obj : customerDao.findAll()) {
						System.out.println(obj);
					}
					
					int deleteId = ValidationUtils.getValidId(sc);
					sc.nextLine();
					
					Customer deleteCustomer = customerDao.findById(deleteId);
					if(deleteCustomer == null) {
						System.out.println("No customer found with that id! ");
					}
					else {
						System.out.println("Customer details:");
						System.out.println(deleteCustomer);
						System.out.print("Are you sure you want to delete this customer? (y/n): ");
						String confirm = sc.nextLine();
						
						if(confirm.equalsIgnoreCase("y")) {
							customerDao.deleteById(deleteCustomer.getId());
							System.out.println("Customer deleted successfully!");
						}
						else {
							System.out.println("Deletion aborted!");
						}
						
					}
					
					break;
					
				case 6:
					
					 System.out.println("Returning to main menu...");
					 return;
				
				default:
					System.out.println("Invalid option!");
					break;	
			}
		} while(choice != 6);
		
	}
	
	

	public static void menuProducts(Scanner sc) {
		
		ProductDao productDao = DaoFactory.createProductDao();
		int choice;
		
		do {
			
			System.out.println("=== PRODUCT MENU ===");
			System.out.println("1. List all products");
			System.out.println("2. View product details");
			System.out.println("3. Add new product");
			System.out.println("4. Update product");
			System.out.println("5. Delete product");
			System.out.println("6. Return to main menu");
			
			choice = ValidationUtils.getValidChoice(sc, 1, 6);
			sc.nextLine();
			
			switch(choice) {
				
				case 1:
					List<Product> list = productDao.findAll();
					System.out.println("=== PRODUCT LIST ===");
					if(list.isEmpty()) {
						System.out.println("No products registered!");
					}
					else {
						for(Product product : list) {
							System.out.println(product);
						}
					}
					break;
				
				case 2:
					System.out.println("=== VIEW PRODUCT DETAILS ===");
					for(Product p : productDao.findAll()) {
						System.out.println(p);
					}
					int id = ValidationUtils.getValidId(sc);
					sc.nextLine();
					Product product = productDao.findById(id);
					if(product == null) {
						System.out.println("No product found with that id!");
					}
					else {
						System.out.println(product);
					}
					
					break;
				
				case 3:
					System.out.println("=== ADD NEW PRODUCT ===");
					
					Product newProduct = null;
					
					String name = ProductValidator.getValidName(sc, newProduct, productDao);
					System.out.println("Enter description: ");
					String description = sc.nextLine();
					String category = ProductValidator.getValidCategory(sc, newProduct);
					double price = ProductValidator.getValidPrice(sc, newProduct);
					int stock = ProductValidator.getValidStock(sc, newProduct);
					
					
					newProduct = new Product(null, name, description, category, price, stock);
					productDao.insert(newProduct);
					System.out.println("Inserted! New id = " + newProduct.getId());
					break;
					
				case 4:
					System.out.println("=== UPDATE PRODUCT ===");
					
					for(Product p : productDao.findAll()) {
						System.out.println(p);
					}
					
					int updateId = ValidationUtils.getValidId(sc);
					sc.nextLine();
					
					Product updateProduct = productDao.findById(updateId);
					if(updateProduct == null) {
						System.out.println("No product found with that id!");
					}
					else {
						System.out.println("Product details:");
						System.out.println(updateProduct);
						
						String newName = ProductValidator.getValidName(sc, updateProduct, productDao);
						
						System.out.println("Enter new description (" + updateProduct.getDescription() + "): ");
						String newDescription = sc.nextLine();
						
						if(newDescription.trim().isEmpty()) {
							newDescription = updateProduct.getDescription();
						}
						
						String newCategory = ProductValidator.getValidCategory(sc, updateProduct);
						double newPrice = ProductValidator.getValidPrice(sc, updateProduct);
						int newStock = ProductValidator.getValidStock(sc, updateProduct);
						
						updateProduct.setName(newName);
						updateProduct.setDescription(newDescription);
						updateProduct.setCategory(newCategory);
						updateProduct.setPrice(newPrice);
						updateProduct.setInventory(newStock);
						
						productDao.update(updateProduct);
						System.out.println("Product updated successfully!");
					}
					break;
					
				case 5:
					System.out.println("=== DELETE PRODUCT ===");
					
					for(Product p : productDao.findAll()) {
						System.out.println(p);
					}
					int deleteId = ValidationUtils.getValidId(sc);
					sc.nextLine();
					Product deleteProduct = productDao.findById(deleteId);
					if(deleteProduct == null) {
						System.out.println("No product found with that id! ");
					}
					else {
						System.out.println("Product details:");
						System.out.println(deleteProduct);
						System.out.print("Are you sure you want to delete this product? (y/n): ");
						String confirm = sc.nextLine();
						
						if(confirm.equalsIgnoreCase("y")) {
							productDao.deleteById(deleteProduct.getId());
							System.out.println("Product deleted successfully!");
						}
						else {
							System.out.println("Deletion aborted!");
						}
					}
				
					break;
					
				case 6:
					System.out.println("Returning to main menu...");
					return;
					
				default:
					System.out.println("Invalid option!");
					break;
			}

		} while(choice != 6);
	}
	
	
	public static void menuOrders(Scanner sc) {
		
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
						System.out.println("=== LIST ORDERS ===");
						List<Order> list = orderDao.findAll();
						if(list.isEmpty()) {
							System.out.println("No orders registered!");
						}
						else {
							for(Order order : list) {
								System.out.println(order);
							}
						}
						break;
						
					case 2:
						System.out.println("=== VIEW ORDER DETAILS ===");
						for(Order o : orderDao.findAll()) {
							System.out.println(o);
						}
						int id = ValidationUtils.getValidId(sc);
						sc.nextLine();
						
						Order order = orderDao.findById(id);
						if(order == null) {
							System.out.println("No order found with that id!");
						}
						else {
							System.out.println(order);
						}
						
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
							
							if(updateOrder.getTotalAmount() == 0 && newStatus != "canceled") {
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
						menuOrderItems(sc);
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
	
	
	public static void menuOrderItems(Scanner sc) {
		
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
