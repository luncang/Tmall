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
import com.charles.tmall.bean.Productimage;
import com.charles.tmall.utils.DBUtil;
import com.charles.tmall.utils.DateUtil;

public class ProductDao {

	public int getTotal(int cid) {
		int total = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select count(*) from product where cid =?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, cid);
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

	public void add(Product bean) {

		String sql = "insert into Product values(null,?,?,?,?,?,?,?)";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOrignalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
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

	public void update(Product bean) {

		String sql = "update Product set name= ?, subTitle=?, orignalPrice=?,promotePrice=?,stock=?, cid = ?, createDate=? where id = ?";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOrignalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setInt(8, bean.getId());
			ps.execute();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
	}

	public void delete(int id) {

		String sql = "delete from product where id =? ";
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

	public Product get(int id) {
		Product bean = null;
		String sql = "select * from product where id = ?";
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
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("orignalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				int cid = rs.getInt("cid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

				bean.setName(name);
				bean.setSubTitle(subTitle);
				bean.setOrignalPrice(orignalPrice);
				bean.setPromotePrice(promotePrice);
				bean.setStock(stock);
				Category category = new CategoryDao().get(cid);
				bean.setCategory(category);
				bean.setCreateDate(createDate);
				bean.setId(id);
				setFirstProductImage(bean);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}
		return bean;
	}

	public List<Product> list(int cid) {
		return list(cid, 0, Short.MAX_VALUE);
	}

	public List<Product> list(int cid, int start, int count) {
		List<Product> beans = new ArrayList<Product>();

		String sql = "select * from product where cid=? order by id desc limit ?,? ";
		Category category = new CategoryDao().get(cid);
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
				Product bean = new Product();
				int id = rs.getInt(1);
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("orignalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

				bean.setName(name);
				bean.setSubTitle(subTitle);
				bean.setOrignalPrice(orignalPrice);
				bean.setPromotePrice(promotePrice);
				bean.setStock(stock);
				bean.setCreateDate(createDate);
				bean.setId(id);
				bean.setCategory(category);
				setFirstProductImage(bean);
				beans.add(bean);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

		return beans;
	}

	public List<Product> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<Product> list(int start, int count) {
		List<Product> beans = new ArrayList<Product>();

		String sql = "select * from product order by id desc limit ?,? ";
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
				Product bean = new Product();
				int id = rs.getInt(1);
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("orignalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

				bean.setName(name);
				bean.setSubTitle(subTitle);
				bean.setOrignalPrice(orignalPrice);
				bean.setPromotePrice(promotePrice);
				bean.setStock(stock);
				bean.setCreateDate(createDate);
				bean.setId(id);

				Category category = new CategoryDao().get(cid);
				bean.setCategory(category);
				beans.add(bean);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close(con, st, rs, ps);
		}

		return beans;
	}

	/**
	 * 为分类填充产品集合
	 * @param cs
	 */
	public void fill(List<Category> cs) {
		for (Category c : cs)
			fill(c);
	}

	/**
	 * 为分类填充产品集合
	 * @param c
	 */
	public void fill(Category c) {
		List<Product> ps = this.list(c.getId());
		c.setProducts(ps);
	}

	/**
	 * 为多个分类设置productsByRow属性
	 * 假设一个分类恰好对应40种产品，那么这40种产品本来是放在一个集合List里。 
	 * 可是，在页面上显示的时候，需要每8种产品，放在一列 为了显示的方便，我把这40种产品，
	 * 按照每8种产品方在一个集合里的方式，拆分成了5个小的集合，这5个小的集合里的每个元素是8个产品。 
	 * 这样到了页面上，显示起来就很方便了。 否则页面上的处理就会复杂不少。
	 * @param cs
	 */
	public void fillByRow(List<Category> cs) {
		int productNumberEachRow = 8;
		for (Category c : cs) {
			List<Product> products = c.getProducts();
			List<List<Product>> productsByRow = new ArrayList<>();
			for (int i = 0; i < products.size(); i += productNumberEachRow) {
				int size = i + productNumberEachRow;
				size = size > products.size() ? products.size() : size;
				List<Product> productsOfEachRow = products.subList(i, size);
				productsByRow.add(productsOfEachRow);
			}
			c.setProductsByRow(productsByRow);
		}
	}

	/**
	 * 一个产品有多个图片，但是只有一个主图片，把第一个图片设置为主图片
	 * @param p
	 */
	public void setFirstProductImage(Product p) {
		List<Productimage> pis = new ProductImageDao().list(p, ProductImageDao.type_single);
		if (!pis.isEmpty())
			p.setFirstProductImage(pis.get(0));
	}

	/**
	 * 为产品设置销售和评价数量
	 * @param p
	 */
	public void setSaleAndReviewNumber(Product p) {
		int saleCount = new OrderItemDao().getSaleCount(p.getId());
		p.setSaleCount(saleCount);

		int reviewCount = new ReviewDao().getCount(p.getId());
		p.setReviewCount(reviewCount);

	}

	/**
	 * 为产品设置销售和评价数量
	 * @param products
	 */
	public void setSaleAndReviewNumber(List<Product> products) {
		for (Product p : products) {
			setSaleAndReviewNumber(p);
		}
	}

	/**
	 * 根据关键字查询产品
	 * @param keyword
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Product> search(String keyword, int start, int count) {
		List<Product> beans = new ArrayList<Product>();

		if (null == keyword || 0 == keyword.trim().length())
			return beans;
		String sql = "select * from Product where name like ? limit ?,? ";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + keyword.trim() + "%");
			ps.setInt(2, start);
			ps.setInt(3, count);
			rs = ps.executeQuery();

			while (rs.next()) {
				Product bean = new Product();
				int id = rs.getInt(1);
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("orignalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

				bean.setName(name);
				bean.setSubTitle(subTitle);
				bean.setOrignalPrice(orignalPrice);
				bean.setPromotePrice(promotePrice);
				bean.setStock(stock);
				bean.setCreateDate(createDate);
				bean.setId(id);

				Category category = new CategoryDao().get(cid);
				bean.setCategory(category);
				setFirstProductImage(bean);
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
