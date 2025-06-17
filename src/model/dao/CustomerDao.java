package model.dao;

import java.util.List;

import model.entities.Customer;

public interface CustomerDao {
	
	void insert(Customer customer);
	void update(Customer customer);
	void deleteById(Integer id);
	Customer findById(Integer id);
	List<Customer> findAll();
}
