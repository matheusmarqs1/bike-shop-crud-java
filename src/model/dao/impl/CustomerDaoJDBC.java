package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.CustomerDao;
import model.entities.Customer;
import model.entities.Order;

public class CustomerDaoJDBC implements CustomerDao {
	
	private Connection conn;
	
	public CustomerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Customer customer) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO customers " +
					"(first_name, last_name, email, telephone, address) " +
					"VALUES " + 
					"(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, customer.getFirst_name());
			st.setString(2, customer.getLast_name());
			st.setString(3, customer.getEmail());
			st.setString(4, customer.getTelephone());
			st.setString(5, customer.getAddress());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					customer.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
							"SELECT " +
				    	    "customers.id AS CustomerId, " +
				    	    "customers.first_name, " +
				    	    "customers.last_name, " +
				    	    "customers.email, " +
				    	    "customers.telephone, " +
				    	    "customers.address, " +
				    	    "orders.id AS OrderId, " +
				    	    "orders.customer_id, " +
				    	    "orders.order_number, " +
				    	    "orders.total_amount, " +
				    	    "orders.status, " +
				    	    "orders.datetime " +
				    	    "FROM customers LEFT JOIN orders ON customers.id = orders.customer_id " +
				    	    "WHERE customers.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			Customer customer = null;
			
			while(rs.next()) {
				
				if(customer == null) {
					customer = instantiateCustomer(rs);
				}
				
				if(rs.getObject("OrderId", Integer.class) != null) {
					Order order = instantiateOrder(rs, customer);
					customer.addOrder(order);
				}
				
			}
			return customer;
		
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Order instantiateOrder(ResultSet rs, Customer customer) throws SQLException {
		
		Order order = new Order();
		order.setId(rs.getInt("OrderId"));
		order.setOrderNumber(rs.getString("order_number"));
		order.setStatus(rs.getString("status"));
		order.setDatetime(rs.getTimestamp("datetime").toLocalDateTime());
		order.setCustomer(customer);
		return order;
	}

	private Customer instantiateCustomer(ResultSet rs) throws SQLException {
		
		Customer customer = new Customer();
		customer.setId(rs.getInt("CustomerId"));
		customer.setFirst_name(rs.getString("first_name"));
		customer.setLast_name(rs.getString("last_name"));
		customer.setEmail(rs.getString("email"));
		customer.setTelephone(rs.getString("telephone"));
		customer.setAddress(rs.getString("address"));
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
							"SELECT " +
				    	    "customers.id AS CustomerId, " +
				    	    "customers.first_name, " +
				    	    "customers.last_name, " +
				    	    "customers.email, " +
				    	    "customers.telephone, " +
				    	    "customers.address, " +
				    	    "orders.id AS OrderId, " +
				    	    "orders.customer_id, " +
				    	    "orders.order_number, " +
				    	    "orders.total_amount, " +
				    	    "orders.status, " +
				    	    "orders.datetime " +
				    	    "FROM customers LEFT JOIN orders ON customers.id = orders.customer_id " +
				    	    "ORDER BY customers.first_name");
			
			rs = st.executeQuery();
			List<Customer> list = new ArrayList<>();
			Map<Integer, Customer> map = new HashMap<>();
			
			while(rs.next()) {
				Customer customer = map.get(rs.getInt("CustomerId"));
				if(customer == null) {
					customer = instantiateCustomer(rs);
					map.put(rs.getInt("CustomerId"), customer);
					list.add(customer);
				}
				if(rs.getObject("OrderId", Integer.class) != null) {
					Order order = instantiateOrder(rs, customer);
					customer.addOrder(order);
				}
				
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
		
	}

}
