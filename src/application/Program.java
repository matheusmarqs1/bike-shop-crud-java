package application;

import java.util.Locale;

import menu.impl.MainMenu;

public class Program {
	
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		new MainMenu().displayMenu();

	}

}
