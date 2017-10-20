package com.charles.tmall.bean;

import java.io.Serializable;


/**
 * The persistent class for the orderitem database table.
 * 
 */
public class Orderitem implements Serializable {

	private static final long serialVersionUID = -7929527215394069027L;

	private int id;
	//订单项数量
	private int number;
	//对应的订单
	private Order order;
	//对应的商品
	private Product product;
	//对应的用户
	private User user;

	public Orderitem() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}