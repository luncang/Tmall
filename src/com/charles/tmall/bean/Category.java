package com.charles.tmall.bean;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String name;
	
	private List<Product> products;
	
	//即一个分类又对应多个 List<Product>，提供这个属性，是为了在首页竖状导航的分类名称右边显示产品列表。
	private List<List<Product>> productsByRow;


	public Category() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<List<Product>> getProductsByRow() {
		return productsByRow;
	}

	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow = productsByRow;
	}

	
	@Override
    public String toString() {
        return "Category [name=" + name + "]";
    }

}