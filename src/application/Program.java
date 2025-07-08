package application;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Customer;
import model.entities.Product;

public class Program {
	
	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		int choise;
		do {
			System.out.println("\"===== MAIN MENU =====\"");
			System.out.println("1. Manage Customers");
			System.out.println("2. Manage Products");
			System.out.println("3. Manage Orders");
			System.out.println("4. Exit");
			
			System.out.print("Choose an option: ");
			choise = sc.nextInt();
			
			switch(choise) {
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
		}while(choise != 4);

	}


	public static void menuCustomers() {
		
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		
		do {
			System.out.println("\"===== CUSTOMER MENU =====\"");
			System.out.println("1. List customers");
			System.out.println("2. Search customers by id");
			System.out.println("3. Register new customer");
			System.out.println("4. Update customer data");
			System.out.println("5. Delete customer by id");
			System.out.println("6. Return to main menu");
			
			System.out.print("Choose an option: ");
			int choise = sc.nextInt();
			sc.nextLine();
			
			switch(choise) {
				case 1:
					List<Customer> list = customerDao.findAll();
					System.out.println("=== CUSTOMER LIST ===");
					if(list.isEmpty()) {
						System.out.println("No registered customer!");
					}
					else {
						for(Customer customer : list) {
							System.out.println(customer);
						}
					}
					break;
					
				case 2:
					System.out.println("=== CUSTOMER SEARCH BY ID ===");
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
					System.out.println("=== UPDATE CUSTOMER DATA ===");
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
					
					customerDao.deleteById(deleteId);
					System.out.println("Customer deleted successfully!");
					break;
					
				case 6:
					
					 System.out.println("Returning to main menu...");
					 return;
				
				default:
					System.out.println("Invalid option!");
					break;	
			}
		} while(true);
		
	}
	
	public static void menuProducts() {
		
		ProductDao productDao = DaoFactory.createProductDao();
		
		do {
			
			System.out.println("\"===== PRODUCT MENU =====\"");
			System.out.println("1. List products");
			System.out.println("2. Search product by id");
			System.out.println("3. Register new product");
			System.out.println("4. Update product data");
			System.out.println("5. Delete product by id");
			System.out.println("6. Return to main menu");
			
			System.out.print("Choose an option: ");
			int choise = sc.nextInt();
			sc.nextLine();
			
			switch(choise) {
				
				case 1:
					List<Product> list = productDao.findAll();
					System.out.println("=== PRODUCT LIST ===");
					if(list.isEmpty()) {
						System.out.println("No registered product!");
					}
					else {
						for(Product product : list) {
							System.out.println(product);
						}
					}
					break;
				
				case 2:
					System.out.println("=== PRODUCT SEARCH BY ID ===");
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
					System.out.println("=== UPDATE PRODUCT DATA ===");
					
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
					
					productDao.deleteById(deleteId);
					System.out.println("Product deleted successfully!");
					break;
					
				case 6:
					System.out.println("Returning to main menu...");
					return;
					
				default:
					System.out.println("Invalid option!");
					break;
			}

		} while(true);
	}
	
	public static void menuOrders() {
		// TODO Auto-generated method stub
	}

	

}
