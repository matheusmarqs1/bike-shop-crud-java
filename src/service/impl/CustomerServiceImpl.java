package service.impl;

import java.util.List;

import exception.BusinessException;
import exception.NoDataFoundException;
import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.entities.Customer;
import service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerDao customerDao = DaoFactory.createCustomerDao();
	
	@Override
	public void insert(Customer customer) {
		if(customer == null) {
			throw new IllegalArgumentException("Customer cannot be null");
		}
		validateEmailUniqueness(customer.getEmail(), customer.getId());
		customer.setTelephone(customer.getTelephone().replaceAll("\\D", ""));
		customerDao.insert(customer);
		
	}

	@Override
	public void update(Customer customer) {
		
		if(customer == null) {
			throw new IllegalArgumentException("Customer cannot be null");
		}
		
		validateEmailUniqueness(customer.getEmail(), customer.getId());
		customer.setTelephone(customer.getTelephone().replaceAll("\\D", ""));
		customerDao.update(customer);
		
	}

	@Override
	public void deleteById(Integer id) {
		
		if(id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		
		customerDao.deleteById(id);
		
	}

	@Override
	public Customer findById(Integer id) {
		
		if(id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		
		Customer customer = customerDao.findById(id);
		if(customer == null) {
			throw new NoDataFoundException("Customer not found with that id: " + id) ;
		}
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		return customerDao.findAll();
	}
	
	private void validateEmailUniqueness(String email, Integer customerId) {
		List<Customer> list = customerDao.findAll();
		for(Customer c : list) {
			if(c.getEmail().equals(email) && (customerId == null || !(c.getId().equals(customerId)))) {
				throw new BusinessException("Email already exists!");
			}
		}
		
	}

}
