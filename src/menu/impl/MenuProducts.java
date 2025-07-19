package menu.impl;

import java.util.Scanner;

import menu.Menu;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Product;
import model.validators.ProductValidator;
import util.AppUtils;
import util.ValidationUtils;

public class MenuProducts implements Menu {

	@Override
	public void displayMenu(Scanner sc) {
		
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
					AppUtils.listAll(productDao.findAll(), "product");
					break;
				
				case 2:
					AppUtils.searchByIdAndDisplay(sc, productDao.findAll(), productDao::findById, "product");
					break;
				
				case 3:
					System.out.println("=== ADD NEW PRODUCT ===");
					
					Product newProduct = null;
					
					String name = ProductValidator.getValidName(sc, newProduct, productDao);
					System.out.println("Enter description: ");
					String description = sc.nextLine();
					String category = ProductValidator.getValidCategory(sc, newProduct);
					double price = ProductValidator.getValidPrice(sc, newProduct);
					int inventory = ProductValidator.getValidInventory(sc, newProduct);
					
					
					newProduct = new Product(null, name, description, category, price, inventory);
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
						
						System.out.println("Enter new description (" + updateProduct.getDescription() + ") - leave empty to keep current:  ");
						String newDescription = sc.nextLine();
						
						if(newDescription.trim().isEmpty()) {
							newDescription = updateProduct.getDescription();
						}
						
						String newCategory = ProductValidator.getValidCategory(sc, updateProduct);
						double newPrice = ProductValidator.getValidPrice(sc, updateProduct);
						int newInventory = ProductValidator.getValidInventory(sc, updateProduct);
						
						updateProduct.setName(newName);
						updateProduct.setDescription(newDescription);
						updateProduct.setCategory(newCategory);
						updateProduct.setPrice(newPrice);
						updateProduct.setInventory(newInventory);
						
						productDao.update(updateProduct);
						System.out.println("Product updated successfully!");
					}
					break;
					
				case 5:
					AppUtils.deleteEntityById(sc, productDao.findAll(), productDao::findById, productDao::deleteById, "product");
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
}
	
	
	
		
				


