package com.charles.tmall.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the review database table.
 * 
 */
public class Review implements Serializable {
	private static final long serialVersionUID = -6395227356871928595L;

	private int id;

	//评价内容
	private String content;
	//评价生成日期
	private Date createDate;
	//对应商品
	private Product product;
	//评价用户
	private User user;

	public Review() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}