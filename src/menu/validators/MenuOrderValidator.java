package menu.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuOrderValidator {
	
	private static final String ORDER_NUMBER_REGEX = "^ORD-2025-\\d{3}$";
	private static final List<String> VALID_STATUSES = Arrays.asList("pending", "paid", "shipped", "delivered", "canceled");
	
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

}
