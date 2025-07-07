package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.OrderDao;
import model.entities.Customer;
import model.entities.Order;

public class OrderDaoJDBC implements OrderDao {
	
	private Connection conn;
	
	public OrderDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Order order) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO orders (customer_id, order_number, total_amount, status, datetime) "
					+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, order.getCustomer().getId());
			st.setString(2, order.getOrderNumber());
			st.setDouble(3, order.getTotalAmount());
			st.setString(4, order.getStatus());
			st.setTimestamp(5, Timestamp.valueOf(order.getDatetime()));
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					order.setId(id);
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
	public void update(Order order) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE orders SET customer_id = ?, order_number = ?, total_amount = ?, status = ?, datetime = ? "
					+ "WHERE id = ?");
			
			st.setInt(1, order.getCustomer().getId());
			st.setString(2, order.getOrderNumber());
			st.setDouble(3, order.getTotalAmount());
			st.setString(4, order.getStatus());
			st.setTimestamp(5, Timestamp.valueOf(order.getDatetime()));
			st.setInt(6, order.getId());
			
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Order findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT orders.id AS OrderId, "
					+ "orders.customer_id, "
					+ "orders.order_number, "
					+ "orders.total_amount, "
					+ "orders.status, "
					+ "orders.datetime, "
					+ "customers.id AS CustomerId, "
					+ "customers.first_name, "
					+ "customers.last_name, "
					+ "customers.email, "
					+ "customers.telephone, "
					+ "customers.address "
					+ "FROM orders JOIN customers ON orders.customer_id = customers.id "
					+ "WHERE orders.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Customer customer = instantiateCustomer(rs);
				Order order = instantiateOrder(rs, customer);
				return order;
			}
			return null;		
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
		order.setCustomer(customer);
		order.setOrderNumber(rs.getString("order_number"));
		order.setTotalAmount(rs.getDouble("total_amount"));
		order.setStatus(rs.getString("status"));
		order.setDatetime(rs.getTimestamp("datetime").toLocalDateTime());
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
	public List<Order> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT orders.id AS OrderId, "
					+ "orders.customer_id, "
					+ "orders.order_number, "
					+ "orders.total_amount, "
					+ "orders.status, "
					+ "orders.datetime, "
					+ "customers.id AS CustomerId, "
					+ "customers.first_name, "
					+ "customers.last_name, "
					+ "customers.email, "
					+ "customers.telephone, "
					+ "customers.address "
					+ "FROM orders JOIN customers ON orders.customer_id = customers.id ");
			
			rs = st.executeQuery();
			
			Map<Integer, Customer> map = new HashMap<>();
			List<Order> list = new ArrayList<>();
			
			while(rs.next()) {
				Customer customer = map.get(rs.getInt("CustomerId"));
				if(customer == null) {
					customer = instantiateCustomer(rs);
					map.put(rs.getInt("CustomerId"), customer);
				}
				Order order = instantiateOrder(rs, customer);
				list.add(order);
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

	@Override
	public List<Order> findByCustomerId(Integer customerId) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT orders.id AS OrderId, "
					+ "orders.customer_id, "
					+ "orders.order_number, "
					+ "orders.total_amount, "
					+ "orders.status, "
					+ "orders.datetime, "
					+ "customers.id AS CustomerId, "
					+ "customers.first_name, "
					+ "customers.last_name, "
					+ "customers.email, "
					+ "customers.telephone, "
					+ "customers.address "
					+ "FROM orders JOIN customers ON orders.customer_id = customers.id "
					+ "WHERE customers.id = ?");
			
			st.setInt(1, customerId);
			rs = st.executeQuery();
			
			List<Order> list = new ArrayList<>();
			Map<Integer, Customer> map = new HashMap<>();
			
			while(rs.next()) {
				Customer customer = map.get(rs.getInt("CustomerId"));
				if(customer == null) {
					customer = instantiateCustomer(rs);
					map.put(rs.getInt("CustomerId"), customer);
				}
				Order order = instantiateOrder(rs, customer);
				list.add(order);
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
