package cn.market.bean;

import java.math.BigDecimal;

public class Goods {
	private String goodid;
	private String goodname;
	private String stock;
	private String price;
	private String memberprice;
	private String category;
    private String buy_num;//œ˙ €¡ø
    private String supplier;
    private String supplierphone;
    private String supplieraddress;
	public String getGoodname() {
		return goodname;
	}
	public void setGoodname(String goodname) {
		this.goodname = goodname;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMemberprice() {
		return memberprice;
	}
	public void setMemberprice(String memberprice) {
		this.memberprice = memberprice;
	}
	public String getGoodid() {
		return goodid;
	}
	public void setGoodid(String goodid) {
		this.goodid = goodid;
	}
	public String getBuy_num() {
		return buy_num;
	}
	public void setBuy_num(String buy_num) {
		this.buy_num = buy_num;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getSupplierphone() {
		return supplierphone;
	}
	public void setSupplierphone(String supplierphone) {
		this.supplierphone = supplierphone;
	}
	public String getSupplieraddress() {
		return supplieraddress;
	}
	public void setSupplieraddress(String supplieraddress) {
		this.supplieraddress = supplieraddress;
	}
	
}
