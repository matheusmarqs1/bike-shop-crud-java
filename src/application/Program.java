package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.entities.Customer;

public class Program {

	public static void main(String[] args) {
		   
		   Scanner sc = new Scanner(System.in);
		   CustomerDao customerDao = DaoFactory.createCustomerDao();
			
		   System.out.println("==== TEST 1: customer findById ====");
		   
	       Customer customer = customerDao.findById(1);
	       System.out.println(customer);
	       
	       System.out.println("\n==== TEST 2: customer findAll ====");
	       
	       List<Customer> list = new ArrayList<>();
	       list = customerDao.findAll();
	       
	       for(Customer obj : list) {
	    	   System.out.println(obj);
	       }
	       
	       
	       System.out.println("\n==== TEST 3: customer insert ====");
	       Customer newCustomer = new Customer(null, "Matheus", "Teles", "matheus@example.com", "62999900012", "303 Avenida Vera Cruz, Jardim Goiás, Goiânia, Goiás");
	       customerDao.insert(newCustomer);
	       System.out.println("Inserted! New id = " + newCustomer.getId());
	       
	       System.out.println("\n==== TEST 4: customer update ====");
	       customer = customerDao.findById(1);
	       customer.setFirst_name("Pedro");
	       customer.setEmail("pedro.silva@example.com");
	       customerDao.update(customer);
	       System.out.println("Update completed! ");
	       
	       
	       
	       System.out.println("\n==== TEST 5: customer delete ====\n");
	       System.out.println("Enter id for delete test: ");
	       int id = sc.nextInt();
	       customerDao.deleteById(id);
	       System.out.println("Delete completed!");

	       sc.close();
	}

}
