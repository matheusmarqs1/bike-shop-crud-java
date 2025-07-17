package util;

import java.util.List;
import java.util.Scanner;

import model.dao.CustomerDao;
import model.entities.Customer;

public class CustomerValidator {
	private static boolean validateEmail(String email) {
		 
		return email.matches("^[A-Za-z0-9]+([._-][A-Za-z0-9]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
	}
	
	private static boolean validateTelephone(String telephone) {
		
		return telephone.matches("^\\(?\\d{2}\\)?[\\s.-]?\\d{4,5}[\\s.-]?\\d{4}$");
	}
	
	public static String getValidTelephone(Scanner sc, Customer customer) {

		String telephone;
		boolean isValidTelephone = false;
		
		do {
			if(customer == null) {
				System.out.println("Enter the phone(only numbers, 11 digits): ");
			}
			else {
				System.out.println("Enter new phone (" + customer.getTelephone() + ") - leave empty to keep current: ");
			}
			telephone = sc.nextLine().trim();
			
			if(customer != null && telephone.isEmpty()) {
				telephone = customer.getTelephone();
				isValidTelephone = true;
			}
			else {
				if(telephone.isEmpty()) {
					System.out.println("Telephone name cannot be empty! Please enter a valid telephone");
				}
				else if(!validateTelephone(telephone)) {
					System.out.println("Invalid telephone! Please try again");
				}
				else {
					telephone = telephone.replaceAll("\\D", "");
					isValidTelephone = true;
				}
				
			}
		} while(!isValidTelephone);
		
		return telephone;
	}

	public static String getValidEmail(Scanner sc, Customer customer, CustomerDao customerDao) {
		
		boolean isValidEmail = false;
		String email;
	
		do {
			boolean isDuplicate = false;
			
			if(customer == null) {
				System.out.println("Enter a valid email (eg., matheus@example.com): ");
			}
			else {
				System.out.println("Enter new email (" + customer.getEmail() + ") - leave empty to keep current: ");
			}
			email = sc.nextLine().trim();
			
			if(customer != null && email.isEmpty()) {
				email = customer.getEmail();
				isValidEmail = true;	
			}
			else {
				if(email.isEmpty()) {
					System.out.println("Email cannot be empty! Please enter a valid email");
				}
				else if(!validateEmail(email)) {
					System.out.println("Invalid email format! Please try again");
				}
				else {
					List<Customer> customerList = customerDao.findAll();
					for(Customer obj : customerList) {
						if(obj.getEmail().equals(email) && (customer == null || obj.getId() != customer.getId())) {
							System.out.println("Email already registered!");
							isDuplicate = true;
							break;
						}
					}
					if(!isDuplicate) {
						isValidEmail = true;
					}
				}
			}
			
		} while(!isValidEmail);
		
		return email;
	}
	
	public static String getValidFirstName(Scanner sc, Customer customer) {
		String firstName;
		boolean isValidFirstName = false;
		
		do {
			if(customer == null) {
				System.out.println("Enter first name: ");
			}
			else {
				System.out.println("Enter new first name (" + customer.getFirst_name() + ") - leave empty to keep current: ");
			}
			firstName = sc.nextLine().trim();
			
			if(customer != null && firstName.isEmpty()) {
				firstName = customer.getFirst_name();
				isValidFirstName = true;
			}
			else if(!validateName(firstName)) {
				System.out.println("Invalid name! It should not contain numbers or special characters");
			}
			else {
				isValidFirstName = true;
			}
			
		} while(!isValidFirstName);
		
		return firstName;
		
	}
	
	public static String getValidLastName(Scanner sc, Customer customer) {
		String lastName;
		boolean isValidLastName = false;
		
		do {
			if(customer == null) {
				System.out.println("Enter last name: ");
			}
			else {
				System.out.println("Enter new last name (" + customer.getLast_name() + ") - leave empty to keep current: ");
			}
			lastName = sc.nextLine().trim();
			
			if(customer != null && lastName.isEmpty()) {
				lastName = customer.getLast_name();
				isValidLastName = true;
			}
			else if(!validateName(lastName)) {
				System.out.println("Invalid name! It should not contain numbers or special characters");
			}
			else {
				isValidLastName = true;
			}
		} while(!isValidLastName);
		
		return lastName;
	}

	public static boolean validateName(String firstName) {
		return firstName.matches("^[A-Za-zÀ-ÿ\\s]+$");
	}

}
