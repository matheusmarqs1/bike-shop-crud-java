package application;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.entities.Customer;

public class Program {

	public static void main(String[] args) {

	       CustomerDao customerDao = DaoFactory.createCustomerDao();
	       Customer customer = customerDao.findById(11);
	       
	       System.out.println(customer);
	}

}
