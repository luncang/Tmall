package com.charles.tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.charles.tmall.bean.Category;
import com.charles.tmall.bean.Product;
import com.charles.tmall.bean.Property;
import com.charles.tmall.bean.Propertyvalue;
import com.charles.tmall.utils.DBUtil;

public class PropertyValueDao {

	public int getTotal() {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from propertyvalue";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, st, rs, null);
		}
		return total;
	}

	public void add(Propertyvalue bean) {

		String sql = "insert into PropertyValue values(null,?,?,?)";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.execute();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
	}

	public void update(Propertyvalue bean) {

		String sql = "update PropertyValue set pid= ?, ptid=?, value=?  where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.setInt(4, bean.getId());
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
	}

	public void delete(int id) {

		String sql = "delete from propertyvalue where id =? ";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
	}

	public Propertyvalue get(int id) {
		Propertyvalue bean = new Propertyvalue();
		String sql = "select * from PropertyValue where id  = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				String value = rs.getString("value");

				Product product = new ProductDao().get(pid);
				Property property = new PropertyDao().get(ptid);
				bean.setProduct(product);
				bean.setProperty(property);
				bean.setValue(value);
				bean.setId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public Propertyvalue get(int ptid, int pid) {
		Propertyvalue bean = new Propertyvalue();
		String sql = "select * from PropertyValue where ptid = ? and pid = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, ptid);
			ps.setInt(2, pid);
			rs = ps.executeQuery();

			if (rs.next()) {
				bean = new Propertyvalue();
				int id = rs.getInt("id");

				String value = rs.getString("value");

				Product product = new ProductDao().get(pid);
				Property property = new PropertyDao().get(ptid);
				bean.setProduct(product);
				bean.setProperty(property);
				bean.setValue(value);
				bean.setId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public List<Propertyvalue> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<Propertyvalue> list(int start, int count) {
		List<Propertyvalue> beans = new ArrayList<Propertyvalue>();

		String sql = "select * from propertyvalue order by id desc limit ?,? ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, count);

			rs = ps.executeQuery();
			while (rs.next()) {
				Propertyvalue bean = new Propertyvalue();
				int id = rs.getInt(1);

				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				String value = rs.getString("value");

				Product product = new ProductDao().get(pid);
				Property property = new PropertyDao().get(ptid);
				bean.setProduct(product);
				bean.setProperty(property);
				bean.setValue(value);
				bean.setId(id);
				beans.add(bean);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

		return beans;
	}

	public void init(Product p) {
		List<Property> pts = new PropertyDao().list(p.getCategory().getId());

		for (Property pt : pts) {
			Propertyvalue pv = get(pt.getId(), p.getId());
			if (null == pv) {
				pv = new Propertyvalue();
				pv.setProduct(p);
				pv.setProperty(pt);
				this.add(pv);
			}
		}
	}

	public List<Propertyvalue> list(int pid) {
		List<Propertyvalue> beans = new ArrayList<Propertyvalue>();

		String sql = "select * from PropertyValue where pid = ? order by ptid desc ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, pid);

			rs = ps.executeQuery();
			while (rs.next()) {
				Propertyvalue bean = new Propertyvalue();
				int id = rs.getInt(1);

				int ptid = rs.getInt("ptid");
				String value = rs.getString("value");

				Product product = new ProductDao().get(pid);
				Property property = new PropertyDao().get(ptid);
				bean.setProduct(product);
				bean.setProperty(property);
				bean.setValue(value);
				bean.setId(id);
				beans.add(bean);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

		return beans;
	}

	private void close(Connection con, Statement st, ResultSet rs, PreparedStatement ps) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
