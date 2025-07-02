package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.OrderItemDao;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(OrderItem orderItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
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
					+ "products.inventory "
					+ "FROM order_items JOIN products ON order_items.product_id = products.id "
					+ "WHERE order_id = ? ");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			Order order = new Order();
			order.setId(id);
			
			List<OrderItem> list = new ArrayList<>();
			while(rs.next()) {
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


}
