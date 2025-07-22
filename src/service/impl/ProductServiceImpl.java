package service.impl;

import java.util.List;

import exception.BusinessException;
import exception.NoDataFoundException;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Product;
import service.ProductService;

public class ProductServiceImpl implements ProductService {
	
	private final ProductDao productDao = DaoFactory.createProductDao();

	@Override
	public void insert(Product product) {
		
		if(product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}
		
		validateNameUniqueness(product.getName(), product.getId());
		product.setCategory(product.getCategory().toLowerCase());
		
		productDao.insert(product);
	}

	@Override
	public void update(Product product) {
		
		if(product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}
		
		validateNameUniqueness(product.getName(), product.getId());
		product.setCategory(product.getCategory().toLowerCase());
		
		productDao.update(product);
		
	}

	@Override
	public void deleteById(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		
		productDao.deleteById(id);
		
	}

	@Override
	public Product findById(Integer id) {
		
		if(id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		Product product = productDao.findById(id);
		
		if(product == null) {
			throw new NoDataFoundException("Product not found with that id: " + id);
		}
		
		return product;
	}

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}
	
	private void validateNameUniqueness(String name, Integer productId) {
		List<Product> list = productDao.findAll();
		for(Product p : list) {
			if(p.getName().equals(name) && (productId == null || !(p.getId().equals(productId)))) {
				throw new BusinessException("Product name already exists!");
			}
		}
		
	}

}
