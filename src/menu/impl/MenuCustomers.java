package menu.impl;

import java.util.Scanner;

import menu.Menu;
import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.entities.Customer;
import model.validators.CustomerValidator;
import util.AppUtils;
import util.ValidationUtils;

public class MenuCustomers implements Menu {

	@Override
	public void displayMenu(Scanner sc) {
		
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		int choice;
		
		do {
			System.out.println("=== CUSTOMER MENU ===");
			System.out.println("1. List all customers");
			System.out.println("2. View customer details");
			System.out.println("3. Add new customer");
			System.out.println("4. Update customer");
			System.out.println("5. Delete customer");
			System.out.println("6. Return to main menu");
			
			choice = ValidationUtils.getValidChoice(sc, 1, 6);
			sc.nextLine();
			
			switch(choice) {
				case 1:
					AppUtils.listAll(customerDao.findAll(), "customer");
					break;
					
				case 2:
					AppUtils.searchByIdAndDisplay(sc,  customerDao.findAll(), customerDao::findById, "customer");
					break;
				
				case 3:
					Customer newCustomer = null;
					System.out.println("=== ADD NEW CUSTOMER ===");
					
					String firstName = CustomerValidator.getValidFirstName(sc, newCustomer);
					String lastName = CustomerValidator.getValidLastName(sc, newCustomer);
					String email = CustomerValidator.getValidEmail(sc, newCustomer, customerDao);
					String telephone = CustomerValidator.getValidTelephone(sc, newCustomer);
					
					System.out.println("Enter address: ");
					String address = sc.nextLine();
					
					newCustomer = new Customer(null, firstName, lastName, email, telephone, address);
					customerDao.insert(newCustomer);
					System.out.println("Inserted! New id = " + newCustomer.getId());
					break;
					
				case 4:
					System.out.println("=== UPDATE CUSTOMER ===");
					
					for(Customer obj : customerDao.findAll()){
						System.out.println(obj);
					}
					
					int updateId = ValidationUtils.getValidId(sc);
					sc.nextLine();
					Customer updateCustomer = customerDao.findById(updateId);
					
					
					if(updateCustomer == null) {
						System.out.println("No customer found with that id!");
					}
					else {
						System.out.println("Customer details:");
						System.out.println(updateCustomer);
						
						String newFirstName = CustomerValidator.getValidFirstName(sc, updateCustomer);
						String newLastName = CustomerValidator.getValidLastName(sc, updateCustomer);
						String newEmail = CustomerValidator.getValidEmail(sc, updateCustomer, customerDao);
						String newTelephone = CustomerValidator.getValidTelephone(sc, updateCustomer);

						System.out.println("Enter new address (" + updateCustomer.getAddress() + ") - leave empty to keep current: ");
						String newAddress = sc.nextLine();
						
						if(newAddress.trim().isEmpty()) {
							newAddress = updateCustomer.getAddress();
						}
						
						updateCustomer.setFirst_name(newFirstName);
						updateCustomer.setLast_name(newLastName);
						updateCustomer.setEmail(newEmail);
						updateCustomer.setTelephone(newTelephone);
						updateCustomer.setAddress(newAddress);
						
						customerDao.update(updateCustomer);
						System.out.println("Customer updated successfully!");
						
					}
					break;
					
				case 5:
					AppUtils.deleteEntityById(sc, customerDao.findAll(), customerDao::findById, customerDao::deleteById, "customer");
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


