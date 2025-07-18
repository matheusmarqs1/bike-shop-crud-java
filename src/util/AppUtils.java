package util;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class AppUtils {
	
	// For customer, product and order
	public static <T> void listAll(List<T> list, String entityName) {
		System.out.println("=== " + entityName.toUpperCase() + " list ===");
		if(list.isEmpty()) {
			System.out.println("No " + entityName + " registered!");
		}
		else {
			for(T obj : list) {
				System.out.println(obj);
			}
		}
	}
	
	// For customer, product and order
	public static <T> void searchByIdAndDisplay(Scanner sc, List<T> list, Function<Integer, T> findById, String entityName) {
		if(list.isEmpty()) {
			listAll(list, entityName);
			return;
		}
		listAll(list, entityName);
		int id = ValidationUtils.getValidId(sc);
		sc.nextLine();
		
		T obj = findById.apply(id);
		
		if(obj == null) {
			System.out.println("No " + obj + " found with that id!");
		}
		else {
			System.out.println(obj);
		}
	}
	
	// For customer and Product
	public static <T> void deleteEntityById(Scanner sc, List<T> list, Function<Integer, T> findById, Consumer<Integer> deleteById, String entityName) {
		System.out.println("===  DELETE " + entityName.toUpperCase() + " ===");
		listAll(list, entityName);
		
		int deleteId = ValidationUtils.getValidId(sc);
		sc.nextLine();
		T obj = findById.apply(deleteId);
		if(obj == null) {
			System.out.println("No " + entityName + " found with that id!");
		}
		else {
			System.out.println(entityName.substring(0, 1).toUpperCase() + entityName.substring(1) + " details: ");
			System.out.println(obj);
			System.out.print("Are you sure you want to delete this " + entityName +   " (y/n): ");
			String confirm = sc.nextLine();
			if(confirm.equalsIgnoreCase("y")) {
				deleteById.accept(deleteId);
				System.out.println(entityName.substring(0, 1).toUpperCase() + entityName.substring(1) + " deleted successfully! ");
			}
			else {
				System.out.println("Deletion aborted!");
			}

		}
	}
	
	public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
	
}
