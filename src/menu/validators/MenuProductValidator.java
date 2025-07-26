package menu.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuProductValidator {
	
	private static final List<String> VALID_CATEGORIES = Arrays.asList("bicycle", "bicycle components", "accessories");
	
	public static String getValidProductName(Scanner sc, String prompt, boolean allowEmpty) {
		boolean isValidProductName = false;
		String productName;
		do {
			System.out.println(prompt);
			productName = sc.nextLine().trim();
			
			try {
				if(allowEmpty && productName.isEmpty()) {
					isValidProductName = true;
				}
				else {
					if(productName == null || productName.isEmpty()) {
						throw new IllegalArgumentException("Product name is required! It cannot be empty");
					}
					else {
						isValidProductName = true;
					}
				}
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidProductName);
		
		return productName;
	}
	
	public static String getValidProductDescription(Scanner sc, String prompt, boolean allowEmpty) {
		boolean isValidProductDescription = false;
		String productDescription;
		do {
			System.out.println(prompt);
			productDescription = sc.nextLine().trim();
			
			try {
				if(allowEmpty && productDescription.isEmpty()) {
					isValidProductDescription = true;
				}
				else {
					if(productDescription == null || productDescription.isEmpty()) {
						throw new IllegalArgumentException("Product description is required! It cannot be empty");
					}
					else {
						isValidProductDescription = true;
					}
				}
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidProductDescription);
		
		return productDescription;
	}
	
	public static String getValidProductCategory(Scanner sc, String prompt, boolean allowEmpty) {
		boolean isValidProductCategory = false;
		String productCategory;
		do {
			System.out.println(prompt);
			productCategory = sc.nextLine().trim();
			
			try {
				if(allowEmpty && productCategory.isEmpty()) {
					isValidProductCategory = true;
				}
				else {
					if(productCategory == null || productCategory.isEmpty()) {
						throw new IllegalArgumentException("Product category is required! It cannot be empty");
					}
					else if(!VALID_CATEGORIES.contains(productCategory.toLowerCase())) {
						throw new IllegalArgumentException("Invalid category! Please choose from: "
								+ String.join(", ", VALID_CATEGORIES));
					}
					else {
						isValidProductCategory = true;
					}
				}
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidProductCategory);
		
		return productCategory;
	}
	
	public static Double getValidProductPrice(Scanner sc, String prompt, boolean allowEmpty) {
		boolean isValidProductPrice = false;
		Double productPrice = null;
		do {
			System.out.println(prompt);
			String priceInput = sc.nextLine().trim();
			
			try {
				if(allowEmpty && priceInput.isEmpty()) {
					isValidProductPrice = true;
					return null;
				}
				else {
					productPrice = Double.parseDouble(priceInput);
					if(productPrice <= 0) {
						throw new IllegalArgumentException("Invalid price! Please enter a value greater than zero");
					}
					else {
						isValidProductPrice = true;
					}
				}
				
			} catch(NumberFormatException e) {
				 System.out.println("Invalid input! Please enter a numeric value (e.g., 10.50 or 25)");
			}
			catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidProductPrice);
		return productPrice;
	}
	
	public static Integer getValidProductInventory(Scanner sc, String prompt, boolean allowEmpty) {
		boolean isValidProductInventory = false;
		Integer productInventory = null;
		do {
			System.out.println(prompt);
			String inventoryInput = sc.nextLine().trim();
			try {
				if(allowEmpty && inventoryInput.isEmpty()) {
					isValidProductInventory = true;
					return null;
				}
				else {
					productInventory = Integer.parseInt(inventoryInput);
					
					if(productInventory < 0) {
						throw new IllegalArgumentException("Invalid inventory! Please enter a value greater than or equal to zero");
					}
					else {
						isValidProductInventory = true;
					}
				}
				
			} catch(NumberFormatException e) {
				 System.out.println("Invalid input! Please enter an integer value (e.g., 0, 10, 25)");
			}
			catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidProductInventory);
		
		return productInventory;
	}
	

}
