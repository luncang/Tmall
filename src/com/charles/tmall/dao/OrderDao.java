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
import com.charles.tmall.bean.Order;
import com.charles.tmall.bean.User;
import com.charles.tmall.utils.DBUtil;
import com.charles.tmall.utils.DateUtil;

public class OrderDao {

	public static final String waitPay = "waitPay";
	public static final String waitDelivery = "waitDelivery";
	public static final String waitConfirm = "waitConfirm";
	public static final String waitReview = "waitReview";
	public static final String finish = "finish";
	public static final String delete = "delete";

	public int getTotal() {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from order";
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

	public void add(Order bean) {

		String sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getOrderCode());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getPost());
			ps.setString(4, bean.getReceiver());
			ps.setString(5, bean.getMobile());
			ps.setString(6, bean.getUserMessage());

			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setTimestamp(8, DateUtil.d2t(bean.getPayDate()));
			ps.setTimestamp(9, DateUtil.d2t(bean.getDeliveryDate()));
			ps.setTimestamp(10, DateUtil.d2t(bean.getConfirmDate()));
			ps.setInt(11, bean.getUser().getId());
			ps.setString(12, bean.getStatus());
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

	public void update(Order bean) {

		String sql = "update order_ set address= ?, post=?, receiver=?,mobile=?,userMessage=? ,createDate = ? , payDate =? , deliveryDate =?, confirmDate = ? , orderCode =?, uid=?, status=? where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getAddress());
			ps.setString(2, bean.getPost());
			ps.setString(3, bean.getReceiver());
			ps.setString(4, bean.getMobile());
			ps.setString(5, bean.getUserMessage());
			ps.setTimestamp(6, DateUtil.d2t(bean.getCreateDate()));

			ps.setTimestamp(7, DateUtil.d2t(bean.getPayDate()));

			ps.setTimestamp(8, DateUtil.d2t(bean.getDeliveryDate()));

			ps.setTimestamp(9, DateUtil.d2t(bean.getConfirmDate()));

			ps.setString(10, bean.getOrderCode());
			ps.setInt(11, bean.getUser().getId());
			ps.setString(12, bean.getStatus());
			ps.setInt(13, bean.getId());
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
	}

	public void delete(int id) {

		String sql = "delete from order where id =? ";
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

	public Order get(int id) {
		Order bean = null;
		String sql = "select * from order where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if (rs.next()) {
				String orderCode = rs.getString("orderCode");
				String address = rs.getString("address");
				String post = rs.getString("post");
				String receiver = rs.getString("receiver");
				String mobile = rs.getString("mobile");
				String userMessage = rs.getString("userMessage");
				String status = rs.getString("status");
				int uid = rs.getInt("uid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
				Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
				Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));

				bean.setOrderCode(orderCode);
				bean.setAddress(address);
				bean.setPost(post);
				bean.setReceiver(receiver);
				bean.setMobile(mobile);
				bean.setUserMessage(userMessage);
				bean.setCreateDate(createDate);
				bean.setPayDate(payDate);
				bean.setDeliveryDate(deliveryDate);
				bean.setConfirmDate(confirmDate);
				User user = new UserDao().get(uid);
				bean.setUser(user);
				bean.setStatus(status);

				bean.setId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public List<Order> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<Order> list(int start, int count) {
		List<Order> beans = new ArrayList<Order>();

		String sql = "select * from order order by id desc limit ?,? ";

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
				Order bean = new Order();
				String orderCode = rs.getString("orderCode");
				String address = rs.getString("address");
				String post = rs.getString("post");
				String receiver = rs.getString("receiver");
				String mobile = rs.getString("mobile");
				String userMessage = rs.getString("userMessage");
				String status = rs.getString("status");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
				Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
				Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
				int uid = rs.getInt("uid");

				int id = rs.getInt("id");
				bean.setId(id);
				bean.setOrderCode(orderCode);
				bean.setAddress(address);
				bean.setPost(post);
				bean.setReceiver(receiver);
				bean.setMobile(mobile);
				bean.setUserMessage(userMessage);
				bean.setCreateDate(createDate);
				bean.setPayDate(payDate);
				bean.setDeliveryDate(deliveryDate);
				bean.setConfirmDate(confirmDate);
				User user = new UserDao().get(uid);
				bean.setUser(user);
				bean.setStatus(status);
				beans.add(bean);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

		return beans;
	}

	public List<Order> list(int uid, String excludedStatus) {
		return list(uid, excludedStatus, 0, Short.MAX_VALUE);
	}

	public List<Order> list(int uid, String excludedStatus, int start, int count) {
		List<Order> beans = new ArrayList<Order>();

		String sql = "select * from Order_ where uid = ? and status != ? order by id desc limit ?,? ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, uid);
			ps.setString(2, excludedStatus);
			ps.setInt(3, start);
			ps.setInt(4, count);
			rs = ps.executeQuery();
			while (rs.next()) {
				 Order bean = new Order();
	                String orderCode =rs.getString("orderCode");
	                String address = rs.getString("address");
	                String post = rs.getString("post");
	                String receiver = rs.getString("receiver");
	                String mobile = rs.getString("mobile");
	                String userMessage = rs.getString("userMessage");
	                String status = rs.getString("status");
	                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
	                Date payDate = DateUtil.t2d( rs.getTimestamp("payDate"));
	                Date deliveryDate = DateUtil.t2d( rs.getTimestamp("deliveryDate"));
	                Date confirmDate = DateUtil.t2d( rs.getTimestamp("confirmDate"));
	                
	                int id = rs.getInt("id");
	                bean.setId(id);
	                bean.setOrderCode(orderCode);
	                bean.setAddress(address);
	                bean.setPost(post);
	                bean.setReceiver(receiver);
	                bean.setMobile(mobile);
	                bean.setUserMessage(userMessage);
	                bean.setCreateDate(createDate);
	                bean.setPayDate(payDate);
	                bean.setDeliveryDate(deliveryDate);
	                bean.setConfirmDate(confirmDate);
	                User user = new UserDao().get(uid);
	                bean.setStatus(status);
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
