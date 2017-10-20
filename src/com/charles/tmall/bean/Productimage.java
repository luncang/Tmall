package com.charles.tmall.bean;

import java.io.Serializable;


/**
 * The persistent class for the productimage database table.
 * 
 */
public class Productimage implements Serializable {
	private static final long serialVersionUID = -5104655097167448677L;

	private int id;

	private String type;
	//对应产品
	private Product product;
	
	

	public Productimage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}