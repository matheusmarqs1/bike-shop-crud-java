package model.validators;

import java.util.List;
import java.util.Scanner;

import model.dao.ProductDao;
import model.entities.Product;

public class ProductValidator {
	
public static String getValidName(Scanner sc, Product product, ProductDao productDao) {
		
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
	public static int getValidInventory(Scanner sc, Product product) {
		int inventory = 0;
		String inventoryString;
		boolean isValidInventory = false;
		
		do {
			if(product == null) {
				System.out.println("Enter the quantity in stock (e.g., 10 or 0): ");
			}
			else {
				System.out.println("Enter new quantity in stock (" + product.getInventory() + ") - leave empty to keep current: ");
			}
			
			inventoryString = sc.nextLine().trim();
			
			if(product != null && inventoryString.isEmpty()) {
				inventory = product.getInventory();
				isValidInventory = true;
			}
			else {
				if(inventoryString.isEmpty()) {
					System.out.println("Stock cannot be empty! Please enter a stock quantity");
					isValidInventory = false;
				}
				
				else {
					try {
						inventory = Integer.parseInt(inventoryString);
						
						if(inventory < 0) {
							System.out.println("Invalid stock quantity! Stock cannot be a negative number");
							isValidInventory = false;
						}
						else {
							isValidInventory = true;
						}
					}
					catch(NumberFormatException e) {
						System.out.println("Invalid input. Please enter a whole number (e.g., 10, 15)");
						isValidInventory = false;
					}
				}
			}
			
			
		} while(!isValidInventory);
		
		return inventory;
	}

	public static double getValidPrice(Scanner sc, Product product) {
		
		double price = 0.0;
		String priceString;
		boolean isValidPrice = false;
		do {
			if(product == null) {
				System.out.println("Enter product price (e.g., $ 10.50): ");
			}
			else {
				System.out.println("Enter new price ($ " + product.getPrice() + ") - leave empty to keep current: ");
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

	public static String getValidCategory(Scanner sc, Product product) {
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
}
