package application;

import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.entities.Order;

public class Program4 {

	public static void main(String[] args) {
		
		OrderDao orderDao = DaoFactory.createOrderDao();
		
		System.out.println("==== TEST 1: order findById ====");
		Order order = orderDao.findById(10);
		System.out.println(order);

	}

}
