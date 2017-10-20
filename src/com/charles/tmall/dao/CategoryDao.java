package com.charles.tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.charles.tmall.bean.Category;
import com.charles.tmall.utils.DBUtil;

public class CategoryDao {

	public int getTotal() {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from category";
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

	public void add(Category bean) {

		String sql = "insert into category values(null,?)";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getName());
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

	public void update(Category bean) {

		String sql = "update category set name= ? where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getId());
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
	}

	public void delete(int id) {

		String sql = "delete from category where id =? ";
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

	public Category get(int id) {
		Category bean = null;
		String sql = "select * from category where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs.next()) {
				bean = new Category();
				bean.setName(rs.getString("name"));
				bean.setId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public List<Category> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<Category> list(int start, int count) {
		List<Category> beans = new ArrayList<Category>();

		String sql = "select * from category order by id desc limit ?,? ";

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
				Category bean = new Category();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				bean.setId(id);
				bean.setName(name);
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
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (st != null)
				st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
