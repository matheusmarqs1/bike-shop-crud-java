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
import model.dao.CustomerDao;
import model.entities.Customer;

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
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE customers "
					+ "SET first_name = ?, last_name = ?, email = ?, telephone = ?, address = ? "
					+ "WHERE id = ? ");
			st.setString(1, customer.getFirst_name());
			st.setString(2, customer.getLast_name());
			st.setString(3, customer.getEmail());
			st.setString(4, customer.getTelephone());
			st.setString(5, customer.getAddress());
			st.setInt(6, customer.getId());
			
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
			st = conn.prepareStatement("DELETE FROM customers "
					+ "WHERE id = ?");
			
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
	public Customer findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Customer customer = instantiateCustomer(rs);
				return customer;
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

	private Customer instantiateCustomer(ResultSet rs) throws SQLException {
		
		Customer customer = new Customer();
		customer.setId(rs.getInt("id"));
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
							"SELECT * FROM customers " +
				    	    "ORDER BY first_name");
			
			rs = st.executeQuery();
			List<Customer> list = new ArrayList<>();
			
			while(rs.next()) {	
				Customer customer = instantiateCustomer(rs);
				list.add(customer);

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
