package com.charles.tmall.bean;

import java.io.Serializable;


/**
 * The persistent class for the propertyvalue database table.
 * 
 */
public class Propertyvalue implements Serializable {

	private static final long serialVersionUID = 8263527486937735209L;

	private int id;
	//属性值
	private String value;
	//对应的属性
	private Property property;
	//对应的商品
	private Product product;

	public Propertyvalue() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}