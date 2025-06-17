package model.dao;

import model.dao.impl.CustomerDaoJDBC;

public class DaoFactory {
	
	public static CustomerDao createCustomerDao() {
		return new CustomerDaoJDBC();
	}
}
