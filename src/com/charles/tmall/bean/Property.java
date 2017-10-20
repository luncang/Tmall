package com.charles.tmall.bean;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the property database table.
 * 
 */
public class Property implements Serializable {

	private static final long serialVersionUID = -813308591769903838L;

	private int id;
	//属性名
	private String name;
	//对应种类
	private Category category;


	public Property() {
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

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


}