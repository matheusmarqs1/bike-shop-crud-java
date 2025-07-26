package menu.validators;

import java.util.Scanner;

public class MenuCustomerValidator {
	
	private static final String NAME_REGEX = "^[A-Za-zÀ-ÿ\\s]+$";
	private static final String EMAIL_REGEX = "^[A-Za-z0-9]+([._-][A-Za-z0-9]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private static final String TELEPHONE_REGEX = "^\\(?\\d{2}\\)?[\\s.-]?\\d{4,5}[\\s.-]?\\d{4}$";
	
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
}
