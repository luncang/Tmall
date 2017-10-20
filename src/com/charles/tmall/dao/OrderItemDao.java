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
import com.charles.tmall.bean.Orderitem;
import com.charles.tmall.bean.Product;
import com.charles.tmall.bean.User;
import com.charles.tmall.utils.DBUtil;
import com.charles.tmall.utils.DateUtil;

public class OrderItemDao {

	public int getTotal() {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from orderitem";
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

	public void add(Orderitem bean) {

		String sql = "insert into OrderItem values(null,?,?,?,?)";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getProduct().getId());

			// 订单项在创建的时候，是没有蒂订单信息的
			if (null == bean.getOrder())
				ps.setInt(2, -1);
			else
				ps.setInt(2, bean.getOrder().getId());

			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());
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

	public void update(Orderitem bean) {

		String sql = "update OrderItem set pid= ?, oid=?, uid=?,number=?  where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getProduct().getId());
			if (null == bean.getOrder())
				ps.setInt(2, -1);
			else
				ps.setInt(2, bean.getOrder().getId());
			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());

			ps.setInt(5, bean.getId());
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
	}

	public void delete(int id) {

		String sql = "delete from orderitem where id =? ";
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

	public Orderitem get(int id) {
		Orderitem bean = new Orderitem();
		String sql = "select * from orderitem where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			st = con.createStatement();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				int pid = rs.getInt("pid");
				int oid = rs.getInt("oid");
				int uid = rs.getInt("uid");
				int number = rs.getInt("number");
				Product product = new ProductDao().get(pid);
				User user = new UserDao().get(uid);
				bean.setProduct(product);
				bean.setUser(user);
				bean.setNumber(number);

				if (-1 != oid) {
					Order order = new OrderDao().get(oid);
					bean.setOrder(order);
				}

				bean.setId(id);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public List<Orderitem> list(int uid) {
		return listByUser(uid, 0, Short.MAX_VALUE);
	}

	public List<Orderitem> listByUser(int uid, int start, int count) {
		List<Orderitem> beans = new ArrayList<Orderitem>();

		String sql = "select * from OrderItem where uid = ? and oid=-1 order by id desc limit ?,? ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, uid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			rs = ps.executeQuery();
			while (rs.next()) {
				Orderitem bean = new Orderitem();
				int id = rs.getInt(1);

				int pid = rs.getInt("pid");
				int oid = rs.getInt("oid");
				int number = rs.getInt("number");

				Product product = new ProductDao().get(pid);
				if (-1 != oid) {
					Order order = new OrderDao().get(oid);
					bean.setOrder(order);
				}

				User user = new UserDao().get(uid);
				bean.setProduct(product);

				bean.setUser(user);
				bean.setNumber(number);
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

	public List<Orderitem> listByOrder(int oid) {
		return listByOrder(oid, 0, Short.MAX_VALUE);
	}

	public List<Orderitem> listByOrder(int oid, int start, int count) {
		List<Orderitem> beans = new ArrayList<Orderitem>();

		String sql = "select * from OrderItem where oid = ? order by id desc limit ?,? ";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, oid);
			ps.setInt(2, start);
			ps.setInt(3, count);

			rs = ps.executeQuery();
			while (rs.next()) {
				Orderitem bean = new Orderitem();
				int id = rs.getInt(1);

				int pid = rs.getInt("pid");
				int uid = rs.getInt("uid");
				int number = rs.getInt("number");

				Product product = new ProductDao().get(pid);
				if (-1 != oid) {
					Order order = new OrderDao().get(oid);
					bean.setOrder(order);
				}

				User user = new UserDao().get(uid);
				bean.setProduct(product);

				bean.setUser(user);
				bean.setNumber(number);
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

	public void fill(List<Order> os) {
		for (Order o : os) {
			List<Orderitem> ois = listByOrder(o.getId());
			float total = 0;
			int totalNumber = 0;
			for (Orderitem oi : ois) {
				total += oi.getNumber() * oi.getProduct().getPromotePrice();
				totalNumber += oi.getNumber();
			}
			o.setTotal(total);
			o.setOrderItems(ois);
			o.setTotalNumber(totalNumber);
		}

	}

	public void fill(Order o) {
		List<Orderitem> ois = listByOrder(o.getId());
		float total = 0;
		for (Orderitem oi : ois) {
			total += oi.getNumber() * oi.getProduct().getPromotePrice();
		}
		o.setTotal(total);
		o.setOrderItems(ois);
	}

	public List<Orderitem> listByProduct(int pid) {
		return listByProduct(pid, 0, Short.MAX_VALUE);
	}

	public List<Orderitem> listByProduct(int pid, int start, int count) {
		List<Orderitem> beans = new ArrayList<Orderitem>();

		String sql = "select * from OrderItem where pid = ? order by id desc limit ?,? ";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);

			rs = ps.executeQuery();
			while (rs.next()) {
				Orderitem bean = new Orderitem();
				int id = rs.getInt(1);

				int uid = rs.getInt("uid");
				int oid = rs.getInt("oid");
				int number = rs.getInt("number");

				Product product = new ProductDao().get(pid);
				if (-1 != oid) {
					Order order = new OrderDao().get(oid);
					bean.setOrder(order);
				}

				User user = new UserDao().get(uid);
				bean.setProduct(product);

				bean.setUser(user);
				bean.setNumber(number);
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

	public int getSaleCount(int pid) {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "select sum(number) from OrderItem where pid = ?";
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, pid);

			rs = ps.executeQuery();
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
