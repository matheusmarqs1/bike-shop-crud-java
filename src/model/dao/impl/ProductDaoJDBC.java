package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ProductDao;
import model.entities.Product;

public class ProductDaoJDBC implements ProductDao {
	
	private Connection conn;
	
	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Product product) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO products (name, description, category, price, inventory) "
					+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, product.getName());
			st.setString(2, product.getDescription());
			st.setString(3, product.getCategory());
			st.setDouble(4, product.getPrice());
			st.setInt(5, product.getInventory());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					product.setId(id);
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
	public void update(Product product) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE products SET name = ?, description = ?, category = ?, price = ?, inventory = ? "
					+ "WHERE id = ?");
			
			st.setString(1, product.getName());
			st.setString(2, product.getDescription());
			st.setString(3, product.getCategory());
			st.setDouble(4, product.getPrice());
			st.setInt(5, product.getInventory());
			st.setInt(6, product.getId());
			
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Product findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM products "
					+ "WHERE id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Product product = instantiateProduct(rs);
				return product;
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

	private Product instantiateProduct(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("id"));
		product.setName(rs.getString("name"));
		product.setDescription(rs.getString("description"));
		product.setCategory(rs.getString("category"));
		product.setPrice(rs.getDouble("price"));
		product.setInventory(rs.getInt("inventory"));
		return product;
	}

	@Override
	public List<Product> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM products ");
			rs = st.executeQuery();
			
			List<Product> list = new ArrayList<>();	
			while(rs.next()) {
				Product product = instantiateProduct(rs);
				list.add(product);
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
