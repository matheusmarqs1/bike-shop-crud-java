package application;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.dao.ProductDao;
import model.entities.Customer;
import model.entities.Order;
import model.entities.Product;

public class Program {
	
	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		int choice;
		do {
			System.out.println("\"===== MAIN MENU =====\"");
			System.out.println("1. Manage Customers");
			System.out.println("2. Manage Products");
			System.out.println("3. Manage Orders");
			System.out.println("4. Exit");
			
			System.out.print("Choose an option: ");
			choice = sc.nextInt();
			
			switch(choice) {
				case 1:
					menuCustomers();
					break;
					
				case 2:
					menuProducts();
					break;
					
				case 3:
					menuOrders();
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


	public static void menuCustomers() {
		
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		int choice;
		
		do {
			System.out.println("\"===== CUSTOMER MENU =====\"");
			System.out.println("1. List customers");
			System.out.println("2. Search customer by id");
			System.out.println("3. Register new customer");
			System.out.println("4. Update customer data");
			System.out.println("5. Delete customer by id");
			System.out.println("6. Return to main menu");
			
			System.out.print("Choose an option: ");
			choice = sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
				case 1:
					List<Customer> list = customerDao.findAll();
					System.out.println("=== CUSTOMER LIST ===");
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
					System.out.println("=== SEARCH CUSTOMER BY ID ===");
					System.out.print("Enter an id for the search: ");
					int id = sc.nextInt();
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
					System.out.println("=== NEW CUSTOMER REGISTRATION ===");
					System.out.println("Enter first name: ");
					String firstName = sc.nextLine();
					System.out.println("Enter last name: ");
					String lastName = sc.nextLine();
					System.out.println("Enter email: ");
					String email = sc.nextLine();
					System.out.println("Enter the phone: ");
					String telephone = sc.nextLine();
					System.out.println("Enter address: ");
					String address = sc.nextLine();
					
					Customer newCustomer = new Customer(null, firstName, lastName, email, telephone, address);
					customerDao.insert(newCustomer);
					System.out.println("Inserted! New id = " + newCustomer.getId());
					break;
					
				case 4:
					System.out.println("=== UPDATE CUSTOMER ===");
					System.out.println("Enter the id of the customer to update: ");
					int updateId = sc.nextInt();
					sc.nextLine();
					Customer updateCustomer = customerDao.findById(updateId);
					
					if(updateCustomer == null) {
						System.out.println("No customer found with that id!");
					}
					else {
						System.out.println("Enter new first name (" + updateCustomer.getFirst_name() + "): ");
						String newFirstName = sc.nextLine();
						
						if(newFirstName.trim().isEmpty()) {
							newFirstName = updateCustomer.getFirst_name();
						}
						
						System.out.println("Enter new last name (" + updateCustomer.getLast_name() + "): ");
						String newLastName = sc.nextLine();
						
						if(newLastName.trim().isEmpty()) {
							newLastName = updateCustomer.getLast_name();
						}
						
						System.out.println("Enter new email (" + updateCustomer.getEmail() + "): ");
						String newEmail = sc.nextLine();
						
						if(newEmail.trim().isEmpty()) {
							newEmail = updateCustomer.getEmail();
						}
						
						System.out.println("Enter new phone (" + updateCustomer.getTelephone() + "): ");
						String newTelephone = sc.nextLine();
						
						if(newTelephone.trim().isEmpty()) {
							newTelephone = updateCustomer.getTelephone();
						}
						
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
					System.out.println("=== DELETE CUSTOMER BY ID ===");
					System.out.println("Enter the id of the customer to delete: ");
					int deleteId = sc.nextInt();
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
	
	public static void menuProducts() {
		
		ProductDao productDao = DaoFactory.createProductDao();
		int choice;
		
		do {
			
			System.out.println("\"===== PRODUCT MENU =====\"");
			System.out.println("1. List products");
			System.out.println("2. Search product by id");
			System.out.println("3. Register new product");
			System.out.println("4. Update product");
			System.out.println("5. Delete product by id");
			System.out.println("6. Return to main menu");
			
			System.out.print("Choose an option: ");
			choice = sc.nextInt();
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
					System.out.println("=== SEARCH PRODUCT BY ID ===");
					System.out.println("Enter an id for the search: ");
					int id = sc.nextInt();
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
					System.out.println("=== NEW PRODUCT REGISTRATION ===");
					
					System.out.println("Enter name: ");
					String name = sc.nextLine();
					System.out.println("Enter description: ");
					String description = sc.nextLine();
					System.out.println("Enter category: ");
					String category = sc.nextLine();
					
					
					double price;
					do {
						System.out.println("Enter price: ");
						String priceString = sc.nextLine();
						try {
							price = Double.parseDouble(priceString);
							break;
						}
						catch(NumberFormatException e) {
							 System.out.println("Invalid input. Please enter a numeric value (e.g., 10.50)");
						}
					} while(true);
					
					int stock;
					do {
						System.out.println("Enter the quantity in stock: ");
						String stockString = sc.nextLine();
						try {
							stock = Integer.parseInt(stockString);
							break;
						}
						catch(NumberFormatException e) {
							System.out.println("Invalid input. Please enter a whole number (e.g., 15)");
						}
					} while(true);
					
					Product newProduct = new Product(null, name, description, category, price, stock);
					productDao.insert(newProduct);
					System.out.println("Inserted! New id = " + newProduct.getId());
					break;
					
				case 4:
					System.out.println("=== UPDATE PRODUCT ===");
					
					System.out.println("Enter the id of the product to update: ");
					int updateId = sc.nextInt();
					sc.nextLine();
					
					Product updateProduct = productDao.findById(updateId);
					if(updateProduct == null) {
						System.out.println("No product found with that id!");
					}
					else {
						System.out.println("Enter new name (" + updateProduct.getName() + "): ");
						String newName = sc.nextLine();
						
						if(newName.trim().isEmpty()) {
							newName = updateProduct.getName();
						}
						
						System.out.println("Enter new description (" + updateProduct.getDescription() + "): ");
						String newDescription = sc.nextLine();
						
						if(newDescription.trim().isEmpty()) {
							newDescription = updateProduct.getDescription();
						}
						
						System.out.println("Enter new category (" + updateProduct.getCategory() + "): ");
						String newCategory = sc.nextLine();
						
						if(newCategory.trim().isEmpty()) {
							newCategory = updateProduct.getCategory();
						}
						
						double newPrice;
						do {
							
							System.out.println("Enter new price (" + updateProduct.getPrice() + "): ");
							String inputPrice = sc.nextLine();
							
							if(inputPrice.trim().isEmpty()) {
								newPrice = updateProduct.getPrice();
								break;
							}
							else {
								try {
									newPrice = Double.parseDouble(inputPrice);
									break;
								}
								catch(NumberFormatException e) {
									System.out.println("Invalid input. Please enter a numeric value(e.g., 10.50)");
								}
							}
						} while(true);
						
						int newStock;
						do {
							
							System.out.println("Enter new quantity in stock: (" + updateProduct.getInventory() + "): ");
							String inputStock = sc.nextLine();
							
							if(inputStock.trim().isEmpty()) {
								newStock = updateProduct.getInventory();
								break;
							}
							else {
								try {
									newStock = Integer.parseInt(inputStock);
									break;
								}
								catch(NumberFormatException e) {
									System.out.println("Invalid input. Please enter a whole number (e.g., 15)");
								}
							
							}
						} while(true);
						
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
					System.out.println("=== DELETE PRODUCT BY ID ===");
					System.out.println("Enter the id of the product to delete: ");
					int deleteId = sc.nextInt();
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
	
	public static void menuOrders() {
		
		OrderDao orderDao = DaoFactory.createOrderDao();
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		int choice;
		
			do {
				System.out.println("\"===== ORDER MENU =====\"");
				System.out.println("1. List orders");
				System.out.println("2. Search order by id");
				System.out.println("3. Search orders by customer id");
				System.out.println("4. Register new order");
				System.out.println("5. Update order");
				System.out.println("6. Cancel order");
				System.out.println("7. Manage Order Items");
				System.out.println("8. Return to main menu");
				
				System.out.println("Choose an option: ");
				choice = sc.nextInt();
				sc.nextLine();
				
				switch(choice) {
					
					case 1:
						System.out.println("=== ORDER LIST ===");
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
						System.out.println("=== SEARCH ORDER BY ID ===");
						
						System.out.println("Enter an id for the search: ");
						int id = sc.nextInt();
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
						System.out.println("=== SEARCH ORDERS BY CUSTOMER ID ===");
						
						System.out.println("Enter a customer id for the search: ");
						int customerId = sc.nextInt();
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
						System.out.println("=== NEW ORDER REGISTRATION ===");
						
						System.out.println("Enter order number(eg., ORD-2025-***): ");
						String orderNumber = sc.nextLine();
						
						System.out.println("Enter an existing customer id: ");
						int orderCustomerId = sc.nextInt();
						sc.nextLine();
							
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
						System.out.println("=== UPDATE ORDER ===");
						
						System.out.println("Enter the id of the order to update: ");
						int updateOrderId = sc.nextInt();
						sc.nextLine();
						
						Order updateOrder = orderDao.findById(updateOrderId);
						
						if(updateOrder == null) {
							System.out.println("No order found with that id!");
						}
						else {
							
							System.out.println("Enter new status(e.g., pending, paid, shipped, delivered) : (" + updateOrder.getStatus() + "): ");
							String newStatus = sc.nextLine();
							
							String[] validStatuses = {"pending", "paid", "shipped", "delivered"};
							boolean isValidStatus = false;
							for(String status : validStatuses) {
								if(newStatus.equalsIgnoreCase(status)) {
									isValidStatus = true;
									break;
								}
							}
							
							if(!isValidStatus) {
								System.out.println("Invalid status! Please choose from pending, paid, shipped, delivered");
							}
							
							else {
								updateOrder.setStatus(newStatus);
								orderDao.update(updateOrder);
								System.out.println("Order updated successfully! ");
							}
						
						}
						break;
						
					case 6:
						System.out.println("=== CANCEL ORDER ===");
						
						System.out.println("Enter the id of the order to cancel: ");
						int cancelOrderId = sc.nextInt();
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
						menuOrderItems();
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


	public static void menuOrderItems() {
		// TODO Auto-generated method stub
		
	}

}
