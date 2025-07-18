package model.validators;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.dao.OrderDao;
import model.entities.Order;

public class OrderValidator {
	
	public static String getValidStatus(Scanner sc, Order order) {
		boolean isValidStatus = false;
		String newStatus;
		String[] validStatuses = {"pending", "paid", "shipped", "delivered"};
		do {
			System.out.println("Enter new status(e.g., pending, paid, shipped, delivered) : (" + order.getStatus() + ") - leave empty to keep current: ");
			newStatus = sc.nextLine().trim().toLowerCase();
			
			if(newStatus.isEmpty()) {
				newStatus = order.getStatus();
				isValidStatus = true;
			}
			else if(newStatus.equals(order.getStatus())) {
				System.out.println("The order is already in the " + newStatus + " status. Please enter a different status if you wish to update it");
				isValidStatus = false;
			}
			else {
				boolean isStatusListed = false;
				for(String status : validStatuses) {
					if(status.equalsIgnoreCase(newStatus)) {
						isStatusListed = true;
						break;
					}
					
				}
				if(isStatusListed) {
					isValidStatus = true;
				}
				else {
					System.out.println("Invalid status. Please choose from the following options: [pending, paid, shipped, delivered]");
					isValidStatus = false;
				}
				
				
			}
		} while(!isValidStatus);
		
		return newStatus;
	}


	public static String getValidOrderNumber(Scanner sc, OrderDao orderDao) {
		String orderNumber;
		boolean isValidOrderNumber = false;
		do {
			
			System.out.println("Enter order number(eg., ORD-2025-***): ");
			orderNumber = sc.nextLine().trim();
			
			if(!validateOrderNumber(orderNumber)) {
				System.out.println("Invalid order number format. Try again");
				isValidOrderNumber = false;
			}
			else {
				boolean orderNumberExists = false;
				
				List<Order> list = orderDao.findAll();
				for(Order order : list) {
					if(order.getOrderNumber().equals(orderNumber)) {
						System.out.println("Order number already exists. Try again");
						orderNumberExists = true;
						break;
					}
				}
				
				if(!orderNumberExists) {
					isValidOrderNumber = true;
				}
				
			}
			
		} while(!isValidOrderNumber);
		
		return orderNumber;
		
	}
	
	private static boolean validateOrderNumber(String orderNumber) {
		String regexOrderNumber = "^ORD-2025-[A-Za-z0-9]{3}$";
		Pattern pattern = Pattern.compile(regexOrderNumber);
		Matcher matcher = pattern.matcher(orderNumber);
		return matcher.matches();
	}

}
