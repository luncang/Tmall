package com.charles.tmall.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the order_ database table.
 * 
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	// 订单地址
	private String address;
	// 订单确认收货日期
	private Date confirmDate;
	// 订单生成日期
	private Date createDate;
	// 订单发货日期
	private Date deliveryDate;
	// 订单联系电话
	private String mobile;
	// 订单编号
	private String orderCode;
	// 支付日期
	private Date payDate;
	// 邮编
	private String post;
	// 订单收件人
	private String receiver;
	// 订单状态
	private String status;
	// 备注
	private String userMessage;
	// 用户
	private User user;
	private List<Orderitem> orderItems;
	private float total;
	private int totalNumber;

	public Order() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserMessage() {
		return this.userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Orderitem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<Orderitem> orderItems) {
		this.orderItems = orderItems;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

}