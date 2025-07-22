package service;

import java.util.List;

import model.entities.Product;

public interface ProductService {
	
	void insert(Product product);
	void update(Product product);
	void deleteById(Integer id);
	Product findById(Integer id);
	List<Product> findAll();
}
