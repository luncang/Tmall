package com.charles.tmall.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 2096797756348077874L;

	private int id;
	//商品创建日期
	private Date createDate;
	//商品名
	private String name;
	//商品原始价格
	private float orignalPrice;
	//商品促销价格
	private float promotePrice;
	//商品库存
	private int stock;
	//商品标题
	private String subTitle;
	//商品种类
	private Category category;
	
	private Productimage firstProductImage;
    private List<Productimage> productImages;
    private List<Productimage> productSingleImages;
    private List<Productimage> productDetailImages;
    //累计评价
    private int reviewCount;
    //销量
    private int saleCount;


	public Product() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getOrignalPrice() {
		return this.orignalPrice;
	}

	public void setOrignalPrice(float orignalPrice) {
		this.orignalPrice = orignalPrice;
	}

	public float getPromotePrice() {
		return this.promotePrice;
	}

	public void setPromotePrice(float promotePrice) {
		this.promotePrice = promotePrice;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}



	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Productimage getFirstProductImage() {
		return firstProductImage;
	}

	public void setFirstProductImage(Productimage firstProductImage) {
		this.firstProductImage = firstProductImage;
	}

	public List<Productimage> getProductDetailImages() {
		return productDetailImages;
	}

	public void setProductDetailImages(List<Productimage> productDetailImages) {
		this.productDetailImages = productDetailImages;
	}

	public List<Productimage> getProductSingleImages() {
		return productSingleImages;
	}

	public void setProductSingleImages(List<Productimage> productSingleImages) {
		this.productSingleImages = productSingleImages;
	}

	public List<Productimage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<Productimage> productImages) {
		this.productImages = productImages;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}



}