package com.lanqiao.vo;

import java.text.NumberFormat;

//股票的信息
public class Stable {
	
	private int id;
	    
	private String name;

	private Double newprice;
	
	private Double oldprice;

	private String date;

    private Double price;

    private Integer number;
    
    private String dhal;
    
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getNewprice() {
		return newprice;
	}

	public void setNewprice(Double newprice) {
		this.newprice = newprice;
	}

	public Double getOldprice() {
		return oldprice;
	}

	public void setOldprice(Double oldprice) {
		this.oldprice = oldprice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDhal() {
		return dhal;
	}

	public void setDhal(String dhal) {
		double hal = (this.newprice-this.oldprice)/this.oldprice;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(2);
		dhal = nf.format(hal);
		this.dhal =dhal;
	}
    
    
}
