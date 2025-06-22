package application;

import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Product;

public class Program2 {

	public static void main(String[] args) {
		
		ProductDao productDao = DaoFactory.createProductDao();
		
		System.out.println("==== TEST 1: product findById ====");
		
		Product product = productDao.findById(2);
		System.out.println(product);

	}

}
