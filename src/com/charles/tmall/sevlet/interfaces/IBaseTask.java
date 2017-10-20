package com.charles.tmall.sevlet.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.charles.tmall.utils.Page;


public interface IBaseTask {
	String add(HttpServletRequest request,HttpServletResponse response,Page page);
	String delete(HttpServletRequest request,HttpServletResponse response,Page page);
	String edit(HttpServletRequest request,HttpServletResponse response,Page page);
	String update(HttpServletRequest request,HttpServletResponse response,Page page);
	String list(HttpServletRequest request,HttpServletResponse response,Page page);
}
