package model.dao;

import db.DB;
import model.dao.impl.CustomerDaoJDBC;
import model.dao.impl.OrderDaoJDBC;
import model.dao.impl.OrderItemDaoJDBC;
import model.dao.impl.ProductDaoJDBC;

public class DaoFactory {
	
	public static CustomerDao createCustomerDao() {
		return new CustomerDaoJDBC(DB.getConnection());
	}
	
	public static ProductDao createProductDao() {
		return new ProductDaoJDBC(DB.getConnection());
	}
	
	public static OrderItemDao createOrderItemDao() {
		return new OrderItemDaoJDBC(DB.getConnection());
	}
	
	public static OrderDao createOrderDao() {
		return new OrderDaoJDBC(DB.getConnection());
	}
	
}
