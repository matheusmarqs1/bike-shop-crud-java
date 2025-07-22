package menu.impl;

import java.util.List;
import java.util.Scanner;

import exception.BusinessException;
import exception.NoDataFoundException;
import menu.Menu;
import model.entities.Customer;
import service.CustomerService;
import service.ServiceFactory;
import util.AppUtils;
import util.ValidationUtils;

public class MenuCustomers implements Menu {
	
	private final Scanner sc = new Scanner(System.in);
	private final CustomerService customerService = ServiceFactory.createCustomerService();
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
		try{
			switch(choice) {
			
				case 1 -> listCustomers();
				case 2 -> viewCustomer();
				case 3 -> insertCustomer();
				case 4 -> updateCustomer();
				case 5 -> deleteCustomer();
				case 6 -> System.out.println("Returning to main menu...");
				default -> System.out.println("Invalid option! Try again.");
			}
		
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	private void deleteCustomer() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== DELETE CUSTOMER ===\n");
				System.out.println("Here is the list of all registered customers:");
				listCustomers();
				System.out.println();
				
		        int id = ValidationUtils.getValidId(sc);
		        sc.nextLine();
		        Customer customer = customerService.findById(id);

		        System.out.println("\nCustomer details: \n");
		        System.out.println(customer);

		        if (AppUtils.confirmAction(sc, "delete this customer")) {
		            customerService.deleteById(id);
		            System.out.println("Customer deleted successfully!");
		            exitLoop = true;
		        } else {
		            System.out.println("Deletion cancelled!");
		            exitLoop = true;
		        }
			} catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
			}
			catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
	}
	private void updateCustomer() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== UPDATE CUSTOMER ===\n");
				System.out.println("First, here is the list of all customers:\n");
				listCustomers();
				System.out.println();
				int updatedId = ValidationUtils.getValidId(sc);
				sc.nextLine();
				Customer currentCustomer = customerService.findById(updatedId);
				
				System.out.println("\nCustomer details: \n");
				System.out.println(currentCustomer);
				
				System.out.println("\nYou can enter new values or leave the field empty to keep the current one");
				
				String newFirstName = ValidationUtils.getValidFirstOrLastName(sc, "Enter new first name (" + currentCustomer.getFirst_name() + ") - leave empty to keep current: ", true);
				if (newFirstName.isEmpty()) newFirstName = currentCustomer.getFirst_name();
				
				String newLastName = ValidationUtils.getValidFirstOrLastName(sc, "Enter new last name (" + currentCustomer.getLast_name() + ") - leave empty to keep current: ", true);
				if (newLastName.isEmpty()) newLastName = currentCustomer.getLast_name();
				
				String newEmail = ValidationUtils.getValidEmail(sc, "Enter new email (" + currentCustomer.getEmail() + ") - leave empty to keep current: ", true);
				if (newEmail.isEmpty()) newEmail = currentCustomer.getEmail();
				
				String newTelephone = ValidationUtils.getValidTelephone(sc, "Enter new telephone (" + currentCustomer.getTelephone() + ") - leave empty to keep current: ", true);
				if (newTelephone.isEmpty()) newTelephone = currentCustomer.getTelephone();

				
				String newAddress = ValidationUtils.getValidAddress(sc, "Enter new address (" + currentCustomer.getAddress() + ") - leave empty to keep current: ", true);
				if (newAddress.isEmpty()) newAddress = currentCustomer.getAddress();
				
				Customer updatedCustomer = new Customer(updatedId, newFirstName, newLastName, newEmail, newTelephone, newAddress);
				
				customerService.update(updatedCustomer);
				
				System.out.println("Customer updated successfully!");
				exitLoop = true;
			}
			catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(BusinessException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different email?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
			}
			catch(Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
	}
	private void insertCustomer() {
		boolean exitLoop = false;
		
		do {
			try {
				System.out.println("\n=== ADD NEW CUSTOMER ===\n");
				Customer newCustomer = null;
				
				System.out.println("First name (only letters)");
				String firstName = ValidationUtils.getValidFirstOrLastName(sc, "Enter first name: ", false);
				
				System.out.println("Last name (only letters)");
				String lastName = ValidationUtils.getValidFirstOrLastName(sc, "Enter last name: ", false);
				
				System.out.println("Email address (must be valid and unique, e.g., matheus@example.com)");
				String email = ValidationUtils.getValidEmail(sc, "Enter a valid email: ", false);
				
				System.out.println("Telephone number (only digits, exactly 11 numbers including area code)");
				String telephone = ValidationUtils.getValidTelephone(sc, "Enter the phone: ", false);
				
				System.out.println("Full address (e.g., Street name, number, city)");
				String address = ValidationUtils.getValidAddress(sc, "Enter address: ", false);
				
				newCustomer = new Customer(null, firstName, lastName, email, telephone, address);
				customerService.insert(newCustomer);
				System.out.println("Inserted! New id = " + newCustomer.getId());
				
				exitLoop = true;
			} catch(IllegalArgumentException e)	{
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(BusinessException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different email?");
			}
			catch(Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
				exitLoop = !AppUtils.confirmAction(sc, "try again? (might be a persistent issue)");
			}
		} while(!exitLoop);
		
	}
	private void viewCustomer() {
		boolean exitLoop = false;
		do {
			try {
				System.out.println("\n=== VIEW CUSTOMER ===\n");
				System.out.println("Here is the list of all customers:");
				listCustomers();
				System.out.println();
				int id = ValidationUtils.getValidId(sc);
				sc.nextLine();
				Customer customer = customerService.findById(id);
				exitLoop = true;
				System.out.println(customer);
				
			} catch(IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again?");
			}
			catch(NoDataFoundException e) {
				System.out.println("Error: " + e.getMessage());
				exitLoop = !AppUtils.confirmAction(sc, "try again with a different ID? ");
			}
		} while(!exitLoop);
	}
	private void listCustomers() {
		System.out.println("\n=== CUSTOMER LIST ===\n");
		System.out.println("Below is the list of all customers:");
		List<Customer> list = customerService.findAll();
        if (list.isEmpty()) {
            System.out.println("No customers have been registered yet!");
        } else {
            AppUtils.listAll(list);
        }
    }
	
	public void showMenuOptions() {
		
		System.out.println("\n=== CUSTOMER MENU ===\n");
		System.out.println("1. List all customers");
		System.out.println("2. View customer details");
		System.out.println("3. Add new customer");
		System.out.println("4. Update customer");
		System.out.println("5. Delete customer");
		System.out.println("6. Return to main menu");
		
	}

		
}
