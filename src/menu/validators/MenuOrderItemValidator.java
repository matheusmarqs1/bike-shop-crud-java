package menu.validators;

import java.util.Scanner;

public class MenuOrderItemValidator {
	
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
