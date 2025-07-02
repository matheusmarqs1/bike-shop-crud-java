package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.OrderItemDao;
import model.entities.OrderItem;

public class Program3 {

	public static void main(String[] args) {
		
		
		OrderItemDao orderItemDao = DaoFactory.createOrderItemDao();
		
		System.out.println("==== TEST 1: orderItem findByOrderId ====");
		
		List<OrderItem> list = new ArrayList<>();
		list = orderItemDao.findByOrderId(7);
		for(OrderItem item : list) {
			System.out.println(item);
		}
		
	}

}
