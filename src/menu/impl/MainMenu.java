package menu.impl;

import java.util.Scanner;

import menu.Menu;
import util.ValidationUtils;

public class MainMenu implements Menu {
	private final Scanner sc = new Scanner(System.in);
	private static final int MIN_OPTION = 1;
	private static final int MAX_OPTION = 4;
	private static final int EXIT_OPTION = 4;
	
	@Override
	public void displayMenu() {
		
		int choice;
		do {
			showMenuOptions();
			choice = ValidationUtils.getValidChoice(sc, MIN_OPTION, MAX_OPTION);
			sc.nextLine();
			handleChoice(choice);
		}while(choice != EXIT_OPTION);
		
	}
	
	public void handleChoice(int choice) {
		try {
			switch(choice) {
				case 1 -> new MenuCustomers().displayMenu();
				case 2 -> new MenuProducts().displayMenu();	
				case 3 -> new MenuOrders().displayMenu();
				case 4 -> System.out.println("Leaving...");
				default -> System.out.println("Invalid option! Try again.");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void showMenuOptions() {
		System.out.println("\n=== MAIN MENU ===\n");
		System.out.println("1. Manage Customers");
		System.out.println("2. Manage Products");
		System.out.println("3. Manage Orders");
		System.out.println("4. Exit");
		
	}
	

}

