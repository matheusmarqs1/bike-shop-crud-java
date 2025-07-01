package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Product;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ProductDao productDao = DaoFactory.createProductDao();
		
		System.out.println("==== TEST 1: product findById ====");
		
		Product product = productDao.findById(2);
		System.out.println(product);
		
		System.out.println("\n==== TEST 2: product findAll ====");
		
		List<Product> list = new ArrayList<>();
		list = productDao.findAll();
		for(Product p : list) {
			System.out.println(p);
		}
		
		System.out.println("\n==== TEST 3: product insert ====");
		
		Product newProduct = new Product(null, "Bike Repair Kit", "Compact repair kit including tire levers, patch kit, multi-tool, and mini pump", "accessories", 34.99, 55);
		productDao.insert(newProduct);
		System.out.println("Inserted! New id = " + newProduct.getId());
		
		
		System.out.println("\n==== TEST 4: product update ====");
		product = productDao.findById(10);
		product.setDescription("Comfortable gloves with gel padding to reduce fatigue and enhance grip during rides");
		product.setPrice(75.00);
		productDao.update(product);
		System.out.println("Update completed! ");
		
		System.out.println("\n==== TEST 5: product deleteById ====");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		productDao.deleteById(id);
		System.out.println("Delete completed! ");
		
		sc.close();
	}

}
