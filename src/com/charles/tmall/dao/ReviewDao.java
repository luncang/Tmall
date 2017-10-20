package com.charles.tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.charles.tmall.bean.Category;
import com.charles.tmall.bean.Product;
import com.charles.tmall.bean.Review;
import com.charles.tmall.bean.User;
import com.charles.tmall.utils.DBUtil;
import com.charles.tmall.utils.DateUtil;

public class ReviewDao {

	public int getTotal() {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from review";
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

	public int getTotal(int pid) {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from review where pid=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, pid);
			rs = ps.executeQuery();
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

	public void add(Review bean) {

		String sql = "insert into Review values(null,?,?,?,?)";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
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

	public void update(Review bean) {

		String sql = "update review set content= ?, uid=?, pid=? , createDate = ? where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
			ps.setInt(5, bean.getId());
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
	}

	public void delete(int id) {

		String sql = "delete from review where id =? ";
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

	public Review get(int id) {
		Review bean = null;
		String sql = "select * from review where id = ?";
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
				int uid = rs.getInt("uid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

				String content = rs.getString("content");

				Product product = new ProductDao().get(pid);
				User user = new UserDao().get(uid);

				bean.setContent(content);
				bean.setCreateDate(createDate);
				bean.setProduct(product);
				bean.setUser(user);
				bean.setId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public List<Review> list(int pid) {
		return list(pid,0, Short.MAX_VALUE);
	}
	
	
	public int getCount(int pid){
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from review where pid=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, pid);
			rs = ps.executeQuery();
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

	public List<Review> list(int pid,int start, int count) {
		List<Review> beans = new ArrayList<Review>();

		String sql ="select * from Review where pid = ? order by id desc limit ?,? ";

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
				  Review bean = new Review();
	                int id = rs.getInt(1);
	 
	                int uid = rs.getInt("uid");
	                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
	                 
	                String content = rs.getString("content");
	                 
	                Product product = new ProductDao().get(pid);
	                User user = new UserDao().get(uid);
	                 
	                bean.setContent(content);
	                bean.setCreateDate(createDate);
	                bean.setId(id);     
	                bean.setProduct(product);
	                bean.setUser(user);
	                beans.add(bean);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

		return beans;
	}
	
	 public boolean isExist(String content, int pid) {
         
	        String sql = "select * from Review where content = ? and pid = ?";
	        Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			try {
				con = DBUtil.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1, content);
				ps.setInt(2, pid);
				rs = ps.executeQuery();
	         
	  
	            if (rs.next()) {
	                return true;
	            }
	  
	        } catch (SQLException e) {
	  
	            e.printStackTrace();
	        } catch (Exception e) {
				e.printStackTrace();
			}
	 
	        return false;
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
