package com.charles.tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.charles.tmall.bean.User;
import com.charles.tmall.utils.DBUtil;

public class UserDao {

	public int getTotal() {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from user";
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

	public void add(User bean) {

		String sql = "insert into user values(null ,? ,?)";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());

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

	public void update(User bean) {

		String sql = "update user set name= ? , password = ? where id = ? ";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

	}

	public void delete(int id) {

		String sql = "delete from user where id = ? ";
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

	public User get(int id) {
		User bean = null;
		String sql = "select * from user where id =  " + id;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs.next()) {
				bean = new User();
				bean.setId(id);
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public List<User> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<User> list(int start, int count) {
		List<User> beans = new ArrayList<User>();
		String sql = "select * from user order by id desc limit?,?";
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
				User bean = new User();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setName(rs.getString("password"));
				beans.add(bean);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

		return beans;
	}

	public boolean isExit(String name) {
		User user = get(name);
		return user != null;
	}

	public User get(String name) {
		User bean = null;
		String sql = "select * from user where name=?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, name);

			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new User();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setName(rs.getString("password"));
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

		return bean;
	}
	
	
	public User get(String name, String password) {
        User bean = null;
          
    	String sql = "select * from user where name=? and password=?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new User();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setName(rs.getString("password"));
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

        return bean;
    }

}
