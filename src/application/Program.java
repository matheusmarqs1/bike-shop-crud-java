package application;

import java.util.Locale;
import java.util.Scanner;

import menu.impl.MainMenu;

public class Program {
	
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		try(Scanner sc = new Scanner(System.in)) {
			new MainMenu().displayMenu(sc);
					
		}
		

	}

}
