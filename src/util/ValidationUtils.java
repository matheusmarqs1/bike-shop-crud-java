package util;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ValidationUtils {
	
	private static final String NAME_REGEX = "^[A-Za-zÀ-ÿ\\s]+$";
	private static final String EMAIL_REGEX = "^[A-Za-z0-9]+([._-][A-Za-z0-9]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private static final String TELEPHONE_REGEX = "^\\(?\\d{2}\\)?[\\s.-]?\\d{4,5}[\\s.-]?\\d{4}$";
	private static final List<String> VALID_CATEGORIES = Arrays.asList("bicycle", "bicycle components", "accessories");
	private static final String ORDER_NUMBER_REGEX = "^ORD-2025-\\d{3}$";
	private static final List<String> VALID_STATUSES = Arrays.asList("pending", "paid", "shipped", "delivered", "canceled");
	
	public static int getValidChoice(Scanner sc, int min, int max) {
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
	
	public static int getValidId(Scanner sc) {
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

	
	public static String getValidFirstOrLastName(Scanner sc, String prompt, boolean allowEmpty) {
		boolean isValidName = false;
		String name;
		do {
			System.out.println(prompt);
			name = sc.nextLine().trim();
			try {
				if(allowEmpty && name.isEmpty()) {
					isValidName = true;
				}
				else {
					if(name == null || name.isEmpty()) {
						throw new IllegalArgumentException("Name is required! It cannot be empty");
					}
					else if(!name.matches(NAME_REGEX)) {
						throw new IllegalArgumentException("Please enter a name using only letters (A-Z, a-z) and spaces");
					}
					else {
						isValidName = true;
					}
				}
			}catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			
		} while(!isValidName);
		
		return name;
	}
	
	public static String getValidEmail(Scanner sc, String prompt, boolean allowEmpty) {
		boolean isValidEmail = false;
		String email;
		do {
			System.out.println(prompt);
			email = sc.nextLine().trim();
			try {
				if(allowEmpty && email.isEmpty()) {
					isValidEmail = true;
				}
				else {
					if(email == null || email.isEmpty()) {
						throw new IllegalArgumentException("Email is required! It cannot be empty");
					}
					else if(!email.matches(EMAIL_REGEX)) {
						throw new IllegalArgumentException("Invalid email format! Expected format: example@domain.com");
					}
					else {
						isValidEmail = true;
					}
				}
				
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidEmail);
		
		return email;
	}
	
	public static String getValidTelephone(Scanner sc, String prompt, boolean allowEmpty) {
		boolean isValidTelephone = false;
		String telephone;
		do {
			System.out.println(prompt);
			telephone = sc.nextLine().trim();
			try {
				if(allowEmpty && telephone.isEmpty()) {
					isValidTelephone = true;
				}
				else {
					if(telephone == null || telephone.isEmpty()) {
						throw new IllegalArgumentException("Telephone is required! It cannot be empty");
					}
					else if(!telephone.matches(TELEPHONE_REGEX)) {
						throw new IllegalArgumentException("Invalid telephone format! Expected formats: (62)99999-8888 or 62999998888");
					}
					else {
						isValidTelephone = true;
					}
				}
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidTelephone);
		
		return telephone;
	}
	
	public static String getValidAddress(Scanner sc, String prompt, boolean allowEmpty) {
		boolean isValidAddress = false;
		String address;
		do {
			System.out.println(prompt);
			address = sc.nextLine().trim();
			try {
				if(allowEmpty && address.isEmpty()) {
					isValidAddress = true;
				}
				else {
					if(address == null || address.isEmpty()) {
						throw new IllegalArgumentException("Address is required! It cannot be empty");
					}
					else {
						isValidAddress = true;
					}
				}
				
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidAddress);
		
		return address;
	}
	
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
	
	public static String getValidOrderNumber(Scanner sc, String prompt) {
		boolean isValidOrderNumber = false;
		String orderNumber;
		do {
			System.out.println(prompt);
			orderNumber = sc.nextLine().trim();
			try {
				if(orderNumber == null || orderNumber.isEmpty()) {
					throw new IllegalArgumentException("Order number is required! It cannot be empty");
				}
				else if(!orderNumber.matches(ORDER_NUMBER_REGEX)) {
					throw new IllegalArgumentException("Invalid order number format! Expected format: ORD-2025-NNN (where NNN are 3 digits)");
				}
				else {
					isValidOrderNumber = true;
				}
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidOrderNumber);
		
		return orderNumber;
	}
	
	public static String getValidStatus(Scanner sc, String prompt) {
		boolean isValidStatus = false;
		String status;
		do {
			System.out.println(prompt);
			status = sc.nextLine().trim();
			try {
				if(status.isEmpty()) {
					 throw new IllegalArgumentException("Order status is required! It cannot be empty");
				}
				else if(!VALID_STATUSES.contains(status.toLowerCase())) {
					throw new IllegalArgumentException("Invalid status! Please choose from: " 
							+ String.join(", ", VALID_STATUSES));
				}
				else {
					isValidStatus = true;
				}
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while(!isValidStatus);
		
		return status;
	}
	
	public static int getValidQuantity(Scanner sc, String prompt) {
		boolean isValidQuantity = false;
		int quantity = 0;
		do {
			System.out.println(prompt);
			String quantityInput = sc.nextLine().trim();
			
			try {
				quantity = Integer.parseInt(quantityInput);
				
				if(quantity <= 0) {
					throw new IllegalArgumentException("Invalid quantity! Please enter a value greater than zero");
				}
				else {
					isValidQuantity = true;
				}
			
			} catch(NumberFormatException e) {
				System.out.println("Invalid input! Please enter an integer value (e.g., 0, 10, 25)");
			} 
			catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			
		} while(!isValidQuantity);
		
		return quantity;
	}
}
