package util;

import java.util.List;
import java.util.Scanner;

public class AppUtils {
	
	public static <T> void listAll(List<T> list) {
			for(T obj : list) {
				System.out.println(obj);
		}
	}
	
	public static boolean confirmAction(Scanner sc, String actionDescription) {
		System.out.println("Are you sure you want to " + actionDescription + " (y/n): ");
		return sc.nextLine().trim().equalsIgnoreCase("y");
	}
	
	
}
