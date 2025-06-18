package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.entities.Customer;

public class Program {

	public static void main(String[] args) {
		
		   CustomerDao customerDao = DaoFactory.createCustomerDao();
			
		   System.out.println("==== TEST 1: customer findById ====");
		   
	       Customer customer = customerDao.findById(11);
	       System.out.println(customer);
	       
	       System.out.println("\n==== TEST 2: customer findAll ====");
	       
	       List<Customer> list = new ArrayList<>();
	       list = customerDao.findAll();
	       
	       for(Customer obj : list) {
	    	   System.out.println(obj);
	       }
	       
	       System.out.println("\n==== TEST 3: customer insert ====");
	       Customer newCustomer = new Customer(null, "Victor", "Alves", "victor@example.com", "62999900011", "303 Avenida Vera Cruz, Jardim Goiás, Goiânia, Goiás");
	       customerDao.insert(newCustomer);
	       System.out.println("Inserted! New id = " + newCustomer.getId());
	       
	}

}
