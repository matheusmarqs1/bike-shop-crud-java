package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidationUtils {
	
	
	
	
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
	
}
