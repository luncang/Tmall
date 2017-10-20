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
import com.charles.tmall.bean.Productimage;
import com.charles.tmall.bean.User;
import com.charles.tmall.utils.DBUtil;

public class ProductImageDao {

	public static final String type_single = "type_single";
	public static final String type_detail = "type_detail";

	public int getTotal() {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from productimage";
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

	public void add(Productimage bean) {

		String sql = "insert into productimage values(null,?,?)";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getProduct().getId());
			ps.setString(2, bean.getType());
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

	public void update(Productimage bean) {

		// String sql = "update productimage set name= ? where id = ?";
		// Connection con = null;
		// Statement st = null;
		// ResultSet rs = null;
		// PreparedStatement ps = null;
		// try {
		// con = DBUtil.getConnection();
		// ps = con.prepareStatement(sql);
		// ps.setString(1, bean.getName());
		// ps.setInt(2, bean.getId());
		// ps.execute();
		//
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// } finally {
		// close(con, st, rs, ps);
		// }
	}

	public void delete(int id) {

		String sql = "delete from productimage where id =? ";
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

	public Productimage get(int id) {
		Productimage bean = null;
		String sql = "select * from productimage where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs.next()) {
				int pid = rs.getInt("pid");
				String type = rs.getString("type");
				Product product = new ProductDao().get(pid);
				bean.setProduct(product);
				bean.setType(type);
				bean.setId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public List<Productimage> list(Product p, String type) {
		return list(p, type, 0, Short.MAX_VALUE);
	}

	public List<Productimage> list(Product p, String type, int start, int count) {
		List<Productimage> beans = new ArrayList<Productimage>();

		String sql = "select * from productimage where pid =? and type =? order by id desc limit ?,? ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ps.setString(2, type);

			ps.setInt(3, start);
			ps.setInt(4, count);

			rs = ps.executeQuery();
			while (rs.next()) {
				Productimage bean = new Productimage();
				int id = rs.getInt(1);

				bean.setProduct(p);
				bean.setType(type);
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
