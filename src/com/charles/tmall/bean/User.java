package com.charles.tmall.bean;

import java.io.Serializable;

/**
 * The persistent class for the user database table.
 * 
 */
public class User implements Serializable {
	private static final long serialVersionUID = -2224686408269027997L;
	private int id;
	// 用户名
	private String name;
	// 用户密码
	private String password;

	public User() {
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}