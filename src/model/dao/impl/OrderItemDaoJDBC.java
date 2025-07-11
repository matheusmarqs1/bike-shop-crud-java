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
import model.dao.OrderItemDao;
import model.entities.Customer;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class OrderItemDaoJDBC implements OrderItemDao {
	
	private Connection conn;
	
	public OrderItemDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(OrderItem orderItem) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO order_items (product_id, order_id, quantity, unit_price) "
					+ "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, orderItem.getProduct().getId() );
			st.setInt(2, orderItem.getOrder().getId());
			st.setInt(3, orderItem.getQuantity());
			st.setDouble(4, orderItem.getUnit_price());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					orderItem.setId(id);
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
	public void update(OrderItem orderItem) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE order_items SET product_id = ?, order_id = ?, quantity = ?, unit_price = ? "
					+ "WHERE id = ?");
			
			st.setInt(1, orderItem.getProduct().getId());
			st.setInt(2, orderItem.getOrder().getId());
			st.setInt(3, orderItem.getQuantity());
			st.setDouble(4, orderItem.getUnit_price());
			st.setInt(5, orderItem.getId());
			
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
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement("DELETE FROM order_items WHERE id = ?");
			st.setInt(1, id);
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected == 0) {
				throw new DbException("Delete failed: no record found with the specified ID");
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
	public List<OrderItem> findByOrderId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT order_items.id AS OrderItemId, "
					+ "order_items.product_id, "
					+ "order_items.order_id, "
					+ "order_items.quantity, "
					+ "order_items.unit_price,"
					+ "products.id AS ProductId, "
					+ "products.name,"
					+ "products.description, "
					+ "products.category, "
					+ "products.price, "
					+ "products.inventory, "
					+ "orders.id AS OrderId, "
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
					+ "FROM order_items "
					+ "JOIN products ON order_items.product_id = products.id "
					+ "JOIN orders ON order_items.order_id = orders.id "
					+ "JOIN customers ON orders.customer_id = customers.id "
					+ "WHERE order_id = ? ");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			List<OrderItem> list = new ArrayList<>();
			Map<Integer, Customer> customerMap = new HashMap<>(); 
			Map<Integer, Order> orderMap = new HashMap<>();
			
			while(rs.next()) {
				Customer customer = customerMap.get(rs.getInt("CustomerId"));
				if(customer == null) {
					customer = instantiateCustomer(rs);
					customerMap.put(rs.getInt("CustomerId"), customer);
				}
				
				Order order = orderMap.get(rs.getInt("OrderId"));
				if(order == null) {
					order = instantiateOrder(rs, customer);
					orderMap.put(rs.getInt("OrderId"), order);
				}
				
				Product product = instantiateProduct(rs);
				OrderItem orderItem = instantiateOrderItem(rs, order, product);
				list.add(orderItem);
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

	private OrderItem instantiateOrderItem(ResultSet rs, Order order, Product product) throws SQLException {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("OrderItemId"));
		orderItem.setProduct(product);
		orderItem.setOrder(order);
		orderItem.setUnit_price(rs.getDouble("unit_price"));
		orderItem.setQuantity(rs.getInt("quantity"));
		return orderItem;
	}

	private Product instantiateProduct(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("ProductId"));
		product.setName(rs.getString("name"));
		product.setDescription(rs.getString("description"));
		product.setCategory(rs.getString("category"));
		product.setPrice(rs.getDouble("price"));
		product.setInventory(rs.getInt("inventory"));
		return product;
	}

	@Override
	public OrderItem findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT order_items.id AS OrderItemId, "
					+ "order_items.product_id, "
					+ "order_items.order_id, "
					+ "order_items.quantity, "
					+ "order_items.unit_price, "
					+ "products.id AS ProductId, "
					+ "products.name,"
					+ "products.description, "
					+ "products.category, "
					+ "products.price, "
					+ "products.inventory, "
					+ "orders.id AS OrderId, "
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
					+ "FROM order_items "
					+ "JOIN products ON order_items.product_id = products.id "
					+ "JOIN orders ON order_items.order_id = orders.id "
					+ "JOIN customers ON orders.customer_id = customers.id "
					+ "WHERE order_items.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Customer customer = instantiateCustomer(rs);
				Order order = instantiateOrder(rs, customer);
				Product product = instantiateProduct(rs);
				OrderItem orderItem = instantiateOrderItem(rs, order, product);
				return orderItem;
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
}
