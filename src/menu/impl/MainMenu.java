package menu.impl;

import java.util.Scanner;

import menu.Menu;
import util.ValidationUtils;

public class MainMenu implements Menu {

	@Override
	public void displayMenu(Scanner sc) {
		int choice;
		do {
			System.out.println("=== MAIN MENU ===");
			System.out.println("1. Manage Customers");
			System.out.println("2. Manage Products");
			System.out.println("3. Manage Orders");
			System.out.println("4. Exit");
			
			choice = ValidationUtils.getValidChoice(sc, 1, 4);
				
			switch(choice) {
				case 1:
					new MenuCustomers().displayMenu(sc);
					break;
					
				case 2:
					new MenuProducts().displayMenu(sc);
					break;
					
				case 3:
					new MenuOrders().displayMenu(sc);
					break;
				
				case 4:
					System.out.println("Leaving...");
					break;
					
				default:
					System.out.println("Invalid option! Try again.");
					break;
			}
		}while(choice != 4);
		
	}

}
