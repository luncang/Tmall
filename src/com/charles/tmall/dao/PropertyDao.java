package com.charles.tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.charles.tmall.bean.Category;
import com.charles.tmall.bean.Property;
import com.charles.tmall.utils.DBUtil;

public class PropertyDao {

	public int getTotal(int cid) {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "select count(*) from property where cid=?";
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, cid);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return total;
	}

	public void add(Property bean) {

		String sql = "insert into property values(null,?,?)";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
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

	public void update(Property bean) {

		String sql = "update property set cid= ?, name=? where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getId());
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
	}

	public void delete(int id) {

		String sql = "delete from property where id =? ";
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

	public Property get(String name, int cid) {
		Property bean = null;
		String sql = "select * from property where name = ? and cid = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, cid);
			rs = ps.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				bean = new Property();
				bean.setName(name);
				Category category = new CategoryDao().get(cid);
				bean.setCategory(category);
				bean.setId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public Property get(int id) {
		Property bean = null;
		String sql = "select * from property where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs.next()) {
				String name = rs.getString("name");
				int cid = rs.getInt("cid");
				bean.setName(name);
				Category category = new CategoryDao().get(cid);
				bean.setCategory(category);
				bean.setId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public List<Property> list(int cid) {
		return list(cid,0, Short.MAX_VALUE);
	}

	public List<Property> list(int cid, int start, int count) {
		List<Property> beans = new ArrayList<Property>();

		String sql = "select * from property where cid = ? order by id desc limit ?,? ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			rs = ps.executeQuery();
			while (rs.next()) {
				Property bean = new Property();
				int id = rs.getInt(1);
				String name = rs.getString("name");
				bean.setName(name);
				Category category = new CategoryDao().get(cid);
				bean.setCategory(category);
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
