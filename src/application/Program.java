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
				
				choice = getValidChoice(sc, 1, 4);
					
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
	
	private static boolean validateEmail(String email) {
		 
		return email.matches("^[A-Za-z0-9]+([._-][A-Za-z0-9]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
	}
	
	private static boolean validateTelephone(String telephone) {
		
		return telephone.matches("^\\(?\\d{2}\\)?[\\s.-]?\\d{4,5}[\\s.-]?\\d{4}$");
	}
	
	private static String getValidTelephone(Scanner sc, Customer customer) {

		String telephone;
		boolean isValidTelephone = false;
		
		do {
			if(customer == null) {
				System.out.println("Enter the phone(only numbers, 11 digits): ");
			}
			else {
				System.out.println("Enter new phone (" + customer.getTelephone() + ") - leave empty to keep current: ");
			}
			telephone = sc.nextLine().trim();
			
			if(customer != null && telephone.isEmpty()) {
				telephone = customer.getTelephone();
				isValidTelephone = true;
			}
			else {
				if(telephone.isEmpty()) {
					System.out.println("Telephone name cannot be empty! Please enter a valid telephone");
				}
				else if(!validateTelephone(telephone)) {
					System.out.println("Invalid telephone! Please try again");
				}
				else {
					telephone = telephone.replaceAll("\\D", "");
					isValidTelephone = true;
				}
				
			}
		} while(!isValidTelephone);
		
		return telephone;
	}

	private static String getValidEmail(Scanner sc, Customer customer, CustomerDao customerDao) {
		
		boolean isValidEmail = false;
		String email;
	
		do {
			boolean isDuplicate = false;
			
			if(customer == null) {
				System.out.println("Enter a valid email (eg., matheus@example.com): ");
			}
			else {
				System.out.println("Enter new email (" + customer.getEmail() + ") - leave empty to keep current: ");
			}
			email = sc.nextLine().trim();
			
			if(customer != null && email.isEmpty()) {
				email = customer.getEmail();
				isValidEmail = true;	
			}
			else {
				if(email.isEmpty()) {
					System.out.println("Email cannot be empty! Please enter a valid email");
				}
				else if(!validateEmail(email)) {
					System.out.println("Invalid email format! Please try again");
				}
				else {
					List<Customer> customerList = customerDao.findAll();
					for(Customer obj : customerList) {
						if(obj.getEmail().equals(email) && (customer == null || obj.getId() != customer.getId())) {
							System.out.println("Email already registered!");
							isDuplicate = true;
							break;
						}
					}
					if(!isDuplicate) {
						isValidEmail = true;
					}
				}
			}
			
		} while(!isValidEmail);
		
		return email;
	}
	
	private static String getValidFirstName(Scanner sc, Customer customer) {
		String firstName;
		boolean isValidFirstName = false;
		
		do {
			if(customer == null) {
				System.out.println("Enter first name: ");
			}
			else {
				System.out.println("Enter new first name (" + customer.getFirst_name() + ") - leave empty to keep current: ");
			}
			firstName = sc.nextLine().trim();
			
			if(customer != null && firstName.isEmpty()) {
				firstName = customer.getFirst_name();
				isValidFirstName = true;
			}
			else if(!validateName(firstName)) {
				System.out.println("Invalid name! It should not contain numbers or special characters");
			}
			else {
				isValidFirstName = true;
			}
			
		} while(!isValidFirstName);
		
		return firstName;
		
	}
	
	private static String getValidLastName(Scanner sc, Customer customer) {
		String lastName;
		boolean isValidLastName = false;
		
		do {
			if(customer == null) {
				System.out.println("Enter last name: ");
			}
			else {
				System.out.println("Enter new last name (" + customer.getLast_name() + ") - leave empty to keep current: ");
			}
			lastName = sc.nextLine().trim();
			
			if(customer != null && lastName.isEmpty()) {
				lastName = customer.getLast_name();
				isValidLastName = true;
			}
			else if(!validateName(lastName)) {
				System.out.println("Invalid name! It should not contain numbers or special characters");
			}
			else {
				isValidLastName = true;
			}
		} while(!isValidLastName);
		
		return lastName;
	}

	private static boolean validateName(String firstName) {
		return firstName.matches("^[A-Za-zÀ-ÿ\\s]+$");
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
					
					for(Customer customer : customerDao.findAll()){
						System.out.println(customer);
					}
					
					int id = getValidId(sc);
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
					
					String firstName = getValidFirstName(sc, newCustomer);
					String lastName = getValidLastName(sc, newCustomer);
					String email = getValidEmail(sc, newCustomer, customerDao);
					String telephone = getValidTelephone(sc, newCustomer);
					
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
					
					int updateId = getValidId(sc);
					sc.nextLine();
					Customer updateCustomer = customerDao.findById(updateId);
					
					
					if(updateCustomer == null) {
						System.out.println("No customer found with that id!");
					}
					else {
						System.out.println("Customer details:");
						System.out.println(updateCustomer);
						
						String newFirstName = getValidFirstName(sc, updateCustomer);
						String newLastName = getValidLastName(sc, updateCustomer);
						String newEmail = getValidEmail(sc, updateCustomer, customerDao);
						String newTelephone = getValidTelephone(sc, updateCustomer);

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
	
	private static String getValidName(Scanner sc, Product product, ProductDao productDao) {
		
		String name;
		boolean isValidName = false;
		do {
			if(product == null) {
				System.out.println("Enter product name:");
			}
			else {
				System.out.println("Enter new product name (" + product.getName() + ") - leave empty to keep current:");
			}
			name = sc.nextLine().trim();
			
			if(product != null && name.isEmpty()) {
				name = product.getName();
				isValidName = true;
			}
			else {
				if(name.isEmpty()) {
					System.out.println("Product name cannot be empty! Please enter a name");
					isValidName = false;
				}
				else {
					isValidName = true;
					List<Product> list = productDao.findAll();
					for(Product p : list) {
						if(p.getName().equalsIgnoreCase(name) && (product == null || product.getId() != p.getId())) {
							System.out.println("A product with that name already exists. Do you want to register it anyway?(y/n):");
							String confirm = sc.nextLine();
							
							if(confirm.equalsIgnoreCase("y")) {
								isValidName = true;
								break;
							}
							else {
								if(product == null) {
									System.out.println("Registration cancelled for this name. Please enter a different name");
								}
								else {
									System.out.println("Update cancelled for this name. Please enter a different name");
								}
								isValidName = false;
								break;
							}
						}
					}
				}

				
			}
		} while(!isValidName);
		
		return name;
	}
	private static int getValidStock(Scanner sc, Product product) {
		int stock = 0;
		String stockString;
		boolean isValidStock = false;
		
		do {
			if(product == null) {
				System.out.println("Enter the quantity in stock (e.g., 10 or 0): ");
			}
			else {
				System.out.println("Enter new quantity in stock (" + product.getInventory() + ") - leave empty to keep current: ");
			}
			
			stockString = sc.nextLine().trim();
			
			if(product != null && stockString.isEmpty()) {
				stock = product.getInventory();
				isValidStock = true;
			}
			else {
				if(stockString.isEmpty()) {
					System.out.println("Stock cannot be empty! Please enter a stock quantity");
					isValidStock = false;
				}
				
				else {
					try {
						stock = Integer.parseInt(stockString);
						
						if(stock < 0) {
							System.out.println("Invalid stock quantity! Stock cannot be a negative number");
							isValidStock = false;
						}
						else {
							isValidStock = true;
						}
					}
					catch(NumberFormatException e) {
						System.out.println("Invalid input. Please enter a whole number (e.g., 10, 15)");
						isValidStock = false;
					}
				}
			}
			
			
		} while(!isValidStock);
		
		return stock;
	}

	private static double getValidPrice(Scanner sc, Product product) {
		
		double price = 0.0;
		String priceString;
		boolean isValidPrice = false;
		do {
			if(product == null) {
				System.out.println("Enter product price (e.g., 10.50): ");
			}
			else {
				System.out.println("Enter new price (" + product.getPrice() + ") - leave empty to keep current: ");
			}
			
			priceString = sc.nextLine().trim();
			
			if(product != null && priceString.isEmpty()) {
				price = product.getPrice();
				isValidPrice = true;
			}
			else {
				
				if(priceString.isEmpty()) {
					System.out.println("Price cannot be empty! Please enter a price");
					isValidPrice = false;
				}
				
				else {
					try {
						price = Double.parseDouble(priceString);
						if(price <= 0.0) {
							System.out.println("Invalid price! The price must be a positive number");
							isValidPrice = false;
						}
						else {
							isValidPrice = true;
						}
						
					}
					catch(NumberFormatException e) {
						  System.out.println("Invalid input! Please enter a numeric value for the price (e.g., 10.50 or 25)");
						  isValidPrice = false;
					}
				}
			}
			
		} while(!isValidPrice);
		
		return price;
	}

	private static String getValidCategory(Scanner sc, Product product) {
		boolean isValidCategory = false;
		String category;
		do {
			
			if(product == null) {
				System.out.println("Enter category (e.g., bicycle, accessories, bicycle components):  ");
			}
			else {
				System.out.println("Enter new category (" + product.getCategory() + ") - leave empty to keep current: ");
			}
			category = sc.nextLine().trim();
			
			if(product != null && category.isEmpty()) {
				category = product.getCategory();
				isValidCategory = true;
			}
			
			else {
				if(category.isEmpty()) {
					System.out.println("Category cannot be empty! Please enter a category");
					isValidCategory = false;
				}
				
				else if(!(category.equalsIgnoreCase("bicycle") || category.equalsIgnoreCase("accessories") || category.equalsIgnoreCase("bicycle components"))) {
					System.out.println("Invalid category! Please choose from: bicycle, accessories or bicycle components");
					isValidCategory = false;			
				}
				else {
					category = category.toLowerCase();
					isValidCategory = true;
				}
			}
			
			
		} while(!isValidCategory);
		
		return category;
		
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
			
			choice = getValidChoice(sc, 1, 6);
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
					int id = getValidId(sc);
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
					
					String name = getValidName(sc, newProduct, productDao);
					System.out.println("Enter description: ");
					String description = sc.nextLine();
					String category = getValidCategory(sc, newProduct);
					double price = getValidPrice(sc, newProduct);
					int stock = getValidStock(sc, newProduct);
					
					
					newProduct = new Product(null, name, description, category, price, stock);
					productDao.insert(newProduct);
					System.out.println("Inserted! New id = " + newProduct.getId());
					break;
					
				case 4:
					System.out.println("=== UPDATE PRODUCT ===");
					
					for(Product p : productDao.findAll()) {
						System.out.println(p);
					}
					
					int updateId = getValidId(sc);
					sc.nextLine();
					
					Product updateProduct = productDao.findById(updateId);
					if(updateProduct == null) {
						System.out.println("No product found with that id!");
					}
					else {
						System.out.println("Product details:");
						System.out.println(updateProduct);
						
						String newName = getValidName(sc, updateProduct, productDao);
						
						System.out.println("Enter new description (" + updateProduct.getDescription() + "): ");
						String newDescription = sc.nextLine();
						
						if(newDescription.trim().isEmpty()) {
							newDescription = updateProduct.getDescription();
						}
						
						String newCategory = getValidCategory(sc, updateProduct);
						double newPrice = getValidPrice(sc, updateProduct);
						int newStock = getValidStock(sc, updateProduct);
						
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
					int deleteId = getValidId(sc);
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
	
	private static String getValidStatus(Scanner sc, Order order) {
		boolean isValidStatus = false;
		String newStatus;
		String[] validStatuses = {"pending", "paid", "shipped", "delivered"};
		do {
			System.out.println("Enter new status(e.g., pending, paid, shipped, delivered) : (" + order.getStatus() + ") - leave empty to keep current: ");
			newStatus = sc.nextLine().trim().toLowerCase();
			
			if(newStatus.isEmpty()) {
				newStatus = order.getStatus();
				isValidStatus = true;
			}
			else if(newStatus.equals(order.getStatus())) {
				System.out.println("The order is already in the " + newStatus + " status. Please enter a different status if you wish to update it");
				isValidStatus = false;
			}
			else {
				boolean isStatusListed = false;
				for(String status : validStatuses) {
					if(status.equalsIgnoreCase(newStatus)) {
						isStatusListed = true;
						break;
					}
					
				}
				if(isStatusListed) {
					isValidStatus = true;
				}
				else {
					System.out.println("Invalid status. Please choose from the following options: [pending, paid, shipped, delivered]");
					isValidStatus = false;
				}
				
				
			}
		} while(!isValidStatus);
		
		return newStatus;
	}


	private static String getValidOrderNumber(Scanner sc, OrderDao orderDao) {
		String orderNumber;
		boolean isValidOrderNumber = false;
		do {
			
			System.out.println("Enter order number(eg., ORD-2025-***): ");
			orderNumber = sc.nextLine().trim();
			
			if(!validateOrderNumber(orderNumber)) {
				System.out.println("Invalid order number format. Try again");
				isValidOrderNumber = false;
			}
			else {
				boolean orderNumberExists = false;
				
				List<Order> list = orderDao.findAll();
				for(Order order : list) {
					if(order.getOrderNumber().equals(orderNumber)) {
						System.out.println("Order number already exists. Try again");
						orderNumberExists = true;
						break;
					}
				}
				
				if(!orderNumberExists) {
					isValidOrderNumber = true;
				}
				
			}
			
		} while(!isValidOrderNumber);
		
		return orderNumber;
		
	}
	
	private static boolean validateOrderNumber(String orderNumber) {
		String regexOrderNumber = "^ORD-2025-[A-Za-z0-9]{3}$";
		Pattern pattern = Pattern.compile(regexOrderNumber);
		Matcher matcher = pattern.matcher(orderNumber);
		return matcher.matches();
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
				
				choice = getValidChoice(sc, 1, 8);
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
						int id = getValidId(sc);
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
						int customerId = getValidId(sc);
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
						String orderNumber = getValidOrderNumber(sc, orderDao);
						for(Customer c : customerDao.findAll()) {
							System.out.println(c);
						}
						int orderCustomerId = getValidId(sc);
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
						int updateOrderId = getValidId(sc);
						sc.nextLine();
						Order updateOrder = orderDao.findById(updateOrderId);
						
						if(updateOrder == null) {
							System.out.println("No order found with that id!");
						}
						else {
							
							System.out.println("Order details:");
							System.out.println(updateOrder);
							
							String newStatus = getValidStatus(sc, updateOrder);
							
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
						int cancelOrderId = getValidId(sc);
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
	
	private static Order getValidOrder(Scanner sc, OrderDao orderDao) {
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

	private static OrderItem getValidItem(Scanner sc, OrderItemDao orderItemDao, Order order) {
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

	private static Product getValidProduct(Scanner sc, OrderItem item, ProductDao productDao) {
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

	private static int getValidQuantity(Scanner sc, OrderItem item, Product product) {
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
			
			choice = getValidChoice(sc, 1, 5);
			sc.nextLine();
			
			switch(choice) {
				
				case 1:
					System.out.println("=== LIST ITEMS IN AN ORDER ===");
					for(Order o : orderDao.findAll()) {
						System.out.println(o);
					}
					int orderId = getValidId(sc);
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
							
					Product addProduct = getValidProduct(sc, orderItem, productDao);
					
					System.out.println("Product details: ");
					System.out.println(addProduct);
						
					for(Order o : orderDao.findAll()) {
						System.out.println(o);
					}
					
					
					Order addOrder = getValidOrder(sc, orderDao);
					
					System.out.println("Order details: ");
					System.out.println(addOrder);
					
					int quantity = getValidQuantity(sc, orderItem, addProduct);
	
					orderItem = new OrderItem(null, addProduct, addOrder, quantity);
					orderItemDao.insert(orderItem);
					System.out.println("Inserted! New id = " + orderItem.getId());	
					
					break;
					
				case 3:
					System.out.println("=== UPDATE ITEM IN AN ORDER ===");
					
					for(Order o : orderDao.findAll()) {
						System.out.println(o);
					}
					
					Order updateOrder = getValidOrder(sc, orderDao);

						
					List<OrderItem> list = orderItemDao.findByOrderId(updateOrder.getId());
					
					if(list.isEmpty()) {
						System.out.println("This order does not contain items");
					}
					else {
						System.out.println("ITEMS FROM " + updateOrder.getOrderNumber());
						
						for(OrderItem item : list) {
							System.out.println(item);
						}
						OrderItem updateItem = getValidItem(sc, orderItemDao, updateOrder);
						
						for(Product p : productDao.findAll()) {
								System.out.println(p);
						}

						Product updateProduct = getValidProduct(sc, updateItem, productDao);
						
						int updateQuantity = getValidQuantity(sc, updateItem, updateProduct);
						
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
					
				
					Order deleteOrder = getValidOrder(sc, orderDao);
					
					List<OrderItem> itemsList = orderItemDao.findByOrderId(deleteOrder.getId());
					
					if(itemsList.isEmpty()) {
						System.out.println("This order does not contain items");
					}
						
					else {
							
						System.out.println("ITEMS FROM " + deleteOrder.getOrderNumber());
						for(OrderItem item : itemsList) {
							System.out.println(item);
						}
						
						OrderItem deleteItem = getValidItem(sc, orderItemDao, deleteOrder);
						
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
