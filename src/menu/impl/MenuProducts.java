package menu.impl;

import java.util.List;
import java.util.Scanner;

import exception.BusinessException;
import exception.NoDataFoundException;
import menu.Menu;
import menu.validators.MenuProductValidator;
import model.entities.Product;
import service.ProductService;
import service.ServiceFactory;
import util.AppUtils;
import util.ValidationUtils;

public class MenuProducts implements Menu {
	
	private final Scanner sc = new Scanner(System.in);
	private final ProductService productService = ServiceFactory.createProductService();
	private static final int MIN_OPTION = 1;
	private static final int MAX_OPTION = 6;
	private static final int EXIT_OPTION = 6;
	
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
			
				case 1 -> listProducts();
				case 2 -> viewProduct();
				case 3 -> insertProduct();
				case 4 -> updateProduct();
				case 5 -> deleteProduct();
				case 6 -> System.out.println("Returning to main menu...");
				default -> System.out.println("Invalid option! Try again");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private void deleteProduct() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== DELETE PRODUCT ===\n");
				System.out.println("Below is a list of all registered products.\nSelect the product ID you wish to delete:\n");
				listProducts();
				System.out.println();
		        int id = ValidationUtils.getValidId(sc);
		        sc.nextLine();
		        
		        Product product = productService.findById(id);

		        System.out.println("\nProduct details: \n");
		        System.out.println(product);

		        if (AppUtils.confirmAction(sc, "delete this product")) {
		            productService.deleteById(id);
		            System.out.println("Product deleted successfully!");
		            exitLoop = true;
		        } else {
		            System.out.println("Deletion cancelled!");
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
			catch(Exception e) {
				System.out.println("An unexpected error: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
	}
	private void updateProduct() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== UPDATE PRODUCT ===\n");
				System.out.println("Below is a list of all registered products.\nSelect the ID of the product you want to update:\n");
				listProducts();
				System.out.println();
				int updateId = ValidationUtils.getValidId(sc);
				sc.nextLine();
				
				Product updateProduct = productService.findById(updateId);
				
				System.out.println("\nProduct details:\n");
				System.out.println(updateProduct);
				
				String newName = MenuProductValidator.getValidProductName(sc, "Enter new product name (" + updateProduct.getName() + ") - leave empty to keep current:  ", true);
				if(newName.isEmpty()) newName = updateProduct.getName();
				
				String newDescription = MenuProductValidator.getValidProductDescription(sc, "Enter new product description (" + updateProduct.getDescription() + ") - leave empty to keep current:  ", true);
				if(newDescription.isEmpty()) newDescription = updateProduct.getDescription();
				
				String newCategory = MenuProductValidator.getValidProductCategory(sc, "Enter new product category (" + updateProduct.getCategory() + ") - leave empty to keep current:  ", true);
				if(newCategory.isEmpty()) newCategory = updateProduct.getCategory();
				
				Double newPrice = MenuProductValidator.getValidProductPrice(sc, "Enter new product price ( U$ " + updateProduct.getPrice() + ") - leave empty to keep current:  ", true);
				double finalPrice = (newPrice == null) ? updateProduct.getPrice() : newPrice.doubleValue();
				
				Integer newInventory = MenuProductValidator.getValidProductInventory(sc, "Enter new product inventory ( " + updateProduct.getInventory() + ") - leave empty to keep current:  " , true);
				int finalInventory = (newInventory == null) ? updateProduct.getInventory() : newInventory.intValue();
				
				updateProduct.setName(newName);
				updateProduct.setDescription(newDescription);
				updateProduct.setCategory(newCategory);
				updateProduct.setPrice(finalPrice);
				updateProduct.setInventory(finalInventory);
				
				productService.update(updateProduct);
				
				System.out.println("Product updated successfully!");
				exitLoop = true;
			} catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again? ");
			}
			catch(BusinessException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different product name? ");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
			}
			catch(Exception e) {
				System.out.println("An unexpected error: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
}
	private void insertProduct() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== ADD NEW PRODUCT ===\n");
				
				Product newProduct = null;
				
				System.out.println("\nProduct name (must be unique)");
				String name = MenuProductValidator.getValidProductName(sc, "Enter the product name: ", false);
				
				System.out.println("\nNow enter a brief description of the product");
				String description = MenuProductValidator.getValidProductDescription(sc, "Enter product description: ", false);
				
				System.out.println("\nProduct category (bicycle, bicycle components, accessories)");
				String category = MenuProductValidator.getValidProductCategory(sc, "Enter product category: ", false);
				
				System.out.println("Product price (positive value)");
				double price = MenuProductValidator.getValidProductPrice(sc, "Enter the price of the product: ", false);
				
				System.out.println("\nProduct inventory quantity (must be a non-negative integer)");
				int inventory = MenuProductValidator.getValidProductInventory(sc, "Enter the quantity in inventory of the product: ", false);
				
				newProduct = new Product(null, name, description, category, price, inventory);
				productService.insert(newProduct);
				System.out.println("Inserted! New id = " + newProduct.getId());
				
				exitLoop = true;
				
			} catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again? ");
			}
			catch(BusinessException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different product name? ");
			}
			catch(Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
	}
	private void viewProduct() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== VIEW PRODUCT ===\n");
				System.out.println("Below is a list of all registered products.\nEnter the ID of the product you want to view:\n");
				listProducts();
				System.out.println();
				int id = ValidationUtils.getValidId(sc);
				sc.nextLine();
				Product product = productService.findById(id);
				exitLoop = true;
				System.out.println(product);
				
			} catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again? ");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
			}
		} while(!exitLoop);
	}
	private void listProducts() {
		System.out.println("\n=== PRODUCT LIST ===\n");
		System.out.println("Displaying all available products in the system:\n");
		List<Product> list = productService.findAll();
        if (list.isEmpty()) {
            System.out.println("No products have been registered yet!");
        } else {
            AppUtils.listAll(list);
        }
	}
	public void showMenuOptions() {
		System.out.println("\n=== PRODUCT MENU ===\n");
		System.out.println("1. List all products");
		System.out.println("2. View product details");
		System.out.println("3. Add new product");
		System.out.println("4. Update product");
		System.out.println("5. Delete product");
		System.out.println("6. Return to main menu");
		
	}
}
	
