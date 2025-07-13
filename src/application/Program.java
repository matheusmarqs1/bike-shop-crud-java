package application;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.dao.OrderItemDao;
import model.dao.ProductDao;
import model.entities.Customer;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class Program {
	
	public static Scanner sc = new Scanner(System.in);
	
	
	private static int getValidChoice(Scanner sc, int min, int max) {
		int choice = 0;
		boolean isValid = false;
		do {
			try {
				System.out.print("Choose an option: ");
				choice = sc.nextInt();
				
				
				if(choice < min || choice > max) {
					System.out.println("Please enter a number between " + min + " and " + max);
				}
				else {
					isValid = true;
				}
				
				
			}
			catch(InputMismatchException e) {
				System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
				sc.nextLine();
			}
		} while(!isValid);
		
		return choice;
	}
	
	private static int getValidId(Scanner sc) {
		int id = 0;
		boolean isValid = false;
		do {
			try {
				System.out.print("Enter a valid id: ");
				id = sc.nextInt();
				
				
				if(id <= 0) {
					System.out.println("Please enter a positive number");
				}
				else {
					isValid = true;
				}
				
				
			}
			catch(InputMismatchException e) {
				System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
				sc.nextLine();
			}
		} while(!isValid);
		
		return id;
	}
	
	private static boolean validateEmail(String email) {
		 
		 String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$";
		 Pattern pattern = Pattern.compile(regexEmail);
		 Matcher matcher = pattern.matcher(email);
		 return matcher.matches();
	}
	
	private static boolean validateTelephone(String telephone) {
		
		String regexTelephone = "^\\(?\\d{2}\\)?\\s?9\\d{4}-?\\d{4}$";
		Pattern pattern = Pattern.compile(regexTelephone);
		Matcher matcher = pattern.matcher(telephone);
		return matcher.matches();
	}
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		int choice;
		do {
			System.out.println("\"===== MAIN MENU =====\"");
			System.out.println("1. Manage Customers");
			System.out.println("2. Manage Products");
			System.out.println("3. Manage Orders");
			System.out.println("4. Exit");
			
			choice = getValidChoice(sc, 1, 4);
				
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
		int choice = 0;
		boolean isValidEmail = false;
		boolean isValidTelephone = false;
		
		do {
			System.out.println("\"===== CUSTOMER MENU =====\"");
			System.out.println("1. List all customers");
			System.out.println("2. View customer details");
			System.out.println("3. Add new customer");
			System.out.println("4. Update customer");
			System.out.println("5. Delete customer");
			System.out.println("6. Return to main menu");
			
			choice = getValidChoice(sc, 1, 6);
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
					int id = getValidId(sc);
					Customer customer = customerDao.findById(id);
					if(customer == null) {
						System.out.println("No customer found with that id!");
					}
					else {
						System.out.println(customer);
					}
					break;
				
				case 3:
					System.out.println("=== ADD NEW CUSTOMER ===");
					System.out.println("Enter first name: ");
					String firstName = sc.nextLine();

					System.out.println("Enter last name: ");
					String lastName = sc.nextLine();
					
					
					String email;
					do {
						System.out.println("Enter a valid email (eg., matheus@example.com): ");
						email = sc.nextLine();
						isValidEmail = validateEmail(email);
	
						if(!isValidEmail) {
							System.out.println("Invalid email format! Please try again");
						}
						
						else {
							List<Customer> customerList = customerDao.findAll();
							for(Customer obj : customerList) {
								if(obj.getEmail().equals(email)) {
									System.out.println("Email already registered!");
									isValidEmail = false;
									break;
								}
							}
						}
						
						
					} while(!isValidEmail);
					
					String telephone;
					do {
						System.out.println("Enter the phone(only numbers, 11 digits): ");
						telephone = sc.nextLine();
						isValidTelephone = validateTelephone(telephone);
						if(!isValidTelephone) {
							System.out.println("Invalid telephone! Please try again");
						}
					} while(!isValidTelephone);
					telephone = telephone.replaceAll("\\D", "");
					
					System.out.println("Enter address: ");
					String address = sc.nextLine();
					
					Customer newCustomer = new Customer(null, firstName, lastName, email, telephone, address);
					customerDao.insert(newCustomer);
					System.out.println("Inserted! New id = " + newCustomer.getId());
					break;
					
				case 4:
					System.out.println("=== UPDATE CUSTOMER ===");
					
					int updateId = getValidId(sc);
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
						
						String newEmail;
						do {
							System.out.println("Enter new email (" + updateCustomer.getEmail() + "): ");
							newEmail = sc.nextLine();
							if(newEmail.trim().isEmpty()) {
								newEmail = updateCustomer.getEmail();
								isValidEmail = true;
							}
							else {
								isValidEmail = validateEmail(newEmail);
								if(!isValidEmail) {
									System.out.println("Invalid email format! Please try again");
								}
								else {
									List<Customer> customerList = customerDao.findAll();
									for(Customer obj : customerList) {
										if(obj.getEmail().equals(newEmail) && !(obj.getId() == updateCustomer.getId())) {
											System.out.println("Email already registered!");
											isValidEmail = false;
											break;
										}
									}
								}
							}
						} while(!isValidEmail);
					
						String newTelephone;
						do {
							System.out.println("Enter new phone (" + updateCustomer.getTelephone() + "): ");
							newTelephone = sc.nextLine();
							
							if(newTelephone.trim().isEmpty()) {
								newTelephone = updateCustomer.getTelephone();
								isValidTelephone = true;
							}
							else {
								isValidTelephone = validateTelephone(newTelephone);
								if(!isValidTelephone) {
									System.out.println("Invalid telephone! Please try again");
								}
								else {
									newTelephone = newTelephone.replaceAll("\\D", "");
								}
							}
						} while(!isValidTelephone);
						
						

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
					int deleteId = getValidId(sc);
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
			System.out.println("1. List all products");
			System.out.println("2. View product details");
			System.out.println("3. Add new product");
			System.out.println("4. Update product");
			System.out.println("5. Delete product");
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
					System.out.println("=== VIEW PRODUCT DETAILS ===");
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
					System.out.println("=== ADD NEW PRODUCT ===");
					
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
					System.out.println("=== DELETE PRODUCT ===");
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
				System.out.println("1. List all orders");
				System.out.println("2. View order details");
				System.out.println("3. List customer's order");
				System.out.println("4. Create new order");
				System.out.println("5. Update order status");
				System.out.println("6. Cancel order");
				System.out.println("7. Manage Order Items");
				System.out.println("8. Return to main menu");
				
				System.out.println("Choose an option: ");
				choice = sc.nextInt();
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
						System.out.println("=== LIST CUSTOMER'S ORDER ===");
						
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
						System.out.println("=== CREATE NEW ORDER ===");
						
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
						System.out.println("=== UPDATE ORDER STATUS ===");
						
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
		
		OrderItemDao orderItemDao = DaoFactory.createOrderItemDao();
		OrderDao orderDao = DaoFactory.createOrderDao();
		ProductDao productDao = DaoFactory.createProductDao();
		int choice;
		List<Order> orderList = orderDao.findAll();
		
		do {
			System.out.println("\"===== ORDER ITEMS MENU =====\"");
			
			System.out.println("1. List items in an order");
			System.out.println("2. Add item to an order");
			System.out.println("3. Update item in an order");
			System.out.println("4. Delete item in an order");
			System.out.println("5. Return to order menu");
			
			System.out.print("Choose an option: ");
			choice = sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
				
				case 1:
					System.out.println("=== LIST ITEMS IN AN ORDER ===");
					System.out.println("Enter an order id: ");
					int orderId = sc.nextInt();
					sc.nextLine();
					Order order = orderDao.findById(orderId);
					
					if(order == null) {
						System.out.println("No order found with that id! ");
					}
					else {
						List<OrderItem> list = orderItemDao.findByOrderId(order.getId());
						
						if(list.isEmpty()) {
							System.out.println("This order does not contain items");
						}
						else {
							for(OrderItem item : list) {
								System.out.println(item);
							}
						}
					}
					break;
					
				case 2:
					System.out.println("=== ADD ITEM TO AN ORDER ===");
					
					System.out.println("=== LIST PRODUCTS ===");
					List<Product> productList = productDao.findAll();
					
					for(Product p : productList) {
						System.out.println(p);
					}
						
					System.out.println("Enter an existing product id: ");
					int addProductId = sc.nextInt();
					sc.nextLine();
						
					Product addProduct = productDao.findById(addProductId);
						
					if(addProduct == null) {
						System.out.println("No product found with that id!");
					}
					else {
						System.out.println("=== LIST ORDERS ===");
						
						for(Order o : orderList) {
							System.out.println(o);
						}
						
						System.out.println("Enter an existing order id:");
						int addOrderId = sc.nextInt();
						sc.nextLine();
							
						Order addOrder = orderDao.findById(addOrderId);
						
						if(addOrder == null) {
							System.out.println("No order found with that id!");
						}
						else {
							System.out.println("Enter quantity: ");
							
							if(!sc.hasNextInt()) {
								System.out.println("Please enter a valid number (whole numbers only)");
							}
							else {
								int quantity = sc.nextInt();
								sc.nextLine();
								
								if(quantity <= 0) {
									System.out.println("Quantity must be greater than zero!");
								}
								else {
									OrderItem orderItem = new OrderItem(null, addProduct, addOrder, quantity);
									orderItemDao.insert(orderItem);
									System.out.println("Inserted! New id = " + orderItem.getId());
								}
							}
						}
							
					}
					break;
					
				case 3:
					System.out.println("=== UPDATE ITEM IN AN ORDER ===");
					
					System.out.println("=== LIST ORDERS ===");
					
					for(Order o : orderList) {
						System.out.println(o);
					}
					
					Order updateOrder = null;
					int updateOrderId;
					boolean validOrder = false;
					do {
						try {
							System.out.println("Enter an existing order id:");
							updateOrderId = sc.nextInt();
							sc.nextLine();
							
							if(updateOrderId <= 0) {
								System.out.println("Id can only be positive! ");
							}
							else {
								updateOrder = orderDao.findById(updateOrderId);
								if(updateOrder == null) {
									System.out.println("No order found with that id!");
								}
								else if(!updateOrder.getStatus().equals("pending")) {
									System.out.println("It is not possible to update this order, it is not pending");
									
								}
								else {
									validOrder = true;
								}
							}
						}
						catch(InputMismatchException e) {
							System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
							sc.nextLine();
						}
						
					}while(!validOrder);
					
					
					
					List<OrderItem> list = orderItemDao.findByOrderId(updateOrder.getId());
						
					if(list.isEmpty()) {
						System.out.println("This order does not contain items");
					}
						
					else {
							
						System.out.println("ITEMS FROM " + updateOrder.getOrderNumber());
						for(OrderItem item : list) {
							System.out.println(item);
						}
						
						OrderItem updateItem = null;
						int updateItemId;
						boolean validItem = false;
						do {
							try {
								System.out.println("Enter item ID to update: ");
								updateItemId = sc.nextInt();
								sc.nextLine();
									
								if(updateItemId <= 0) {
									System.out.println("Id can only be positive");
								}
								else {
									updateItem = orderItemDao.findById(updateItemId);
									if(updateItem == null) {
										System.out.println("There is no item with that id");
									}
									else if(!list.contains(updateItem)) {
										System.out.println("This item is not in the order");
									}
									else {
										validItem = true;
									}
								}
							}
							catch(InputMismatchException e) {
								System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
								sc.nextLine();
							}
								
						}while(!validItem);

							
							
						System.out.println("=== LIST PRODUCTS ===");
						List<Product> updateProductList = productDao.findAll();
						for(Product p : updateProductList) {
								System.out.println(p);
							}
								
							int updateProductId;
							Product updateProduct = new Product();
							do {
								
								System.out.println("Enter new product id (" + updateItem.getProduct().getId() + "): ");
								String inputProductId = sc.nextLine();
								
								if(inputProductId.trim().isEmpty()) {
									updateProductId = updateItem.getProduct().getId();
									updateProduct = productDao.findById(updateProductId);
									break;
								}
								else {
									try {
										updateProductId = Integer.parseInt(inputProductId);
										if(updateProductId <= 0) {
											System.out.println("Id can only be positive");
										}
										else {
											updateProduct = productDao.findById(updateProductId);
											break;
										}
									}
									catch(NumberFormatException e) {
										System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
									}
								}
							} while(true);
							
							int updateQuantity;
							do {
								
								System.out.println("Enter new quantity (" + updateItem.getQuantity() + "): ");
								String inputQuantity = sc.nextLine();
								
								if(inputQuantity.trim().isEmpty()) {
									updateQuantity = updateItem.getQuantity();
									break;
								}
								else {
									try {
										updateQuantity = Integer.parseInt(inputQuantity);
										if(updateQuantity <= 0) {
											System.out.println("Quantity can only be positive");
										}
										else {
											break;
										}
									}
									catch(NumberFormatException e) {
										System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
									}
								}
							} while(true);
							
							updateItem.setProduct(updateProduct);
							updateItem.setQuantity(updateQuantity);
							orderItemDao.update(updateItem);
							System.out.println("Item updated successfully!");
						
					}
					break;
				
				
				case 4:
					
					System.out.println("=== DELETE ITEM IN AN ORDER ===");
					
					System.out.println("=== LIST ORDERS ===");

					for(Order o : orderList) {
						System.out.println(o);
					}
					
					Order deleteOrder = null;
					int deleteOrderId;
					
					do {
						try {
							System.out.println("Enter an existing order id:");
							deleteOrderId = sc.nextInt();
							sc.nextLine();
							
							if(deleteOrderId <= 0) {
								System.out.println("Id can only be positive! ");
							}
							else {
								deleteOrder = orderDao.findById(deleteOrderId);
								if(deleteOrder == null) {
									System.out.println("No order found with that id!");
								}
							}
						}
						catch(InputMismatchException e) {
							System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
							sc.nextLine();
						}
						
					}while(deleteOrder == null);
					
					List<OrderItem> itemsList = orderItemDao.findByOrderId(deleteOrder.getId());
					
					if(itemsList.isEmpty()) {
						System.out.println("This order does not contain items");
					}
						
					else {
							
						System.out.println("ITEMS FROM " + deleteOrder.getOrderNumber());
						for(OrderItem item : itemsList) {
							System.out.println(item);
						}
						
						OrderItem deleteItem = null;
						int deleteItemId;
						boolean validItem = false;
						do {
							try {
								System.out.println("Enter item ID to delete: ");
								deleteItemId = sc.nextInt();
								sc.nextLine();
									
								if(deleteItemId <= 0) {
									System.out.println("Id can only be positive");
								}
								else {
									deleteItem = orderItemDao.findById(deleteItemId);
									if(deleteItem == null) {
										System.out.println("There is no item with that id");
									}
									else if(!itemsList.contains(deleteItem)) {
										System.out.println("This item is not in the order");
									}
									else {
										validItem = true;
									}
								}
							}
							catch(InputMismatchException e) {
								System.out.println("Please enter a whole number only (e.g., 1, 2, 10)");
								sc.nextLine();
							}
								
						}while(!validItem);
						
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
