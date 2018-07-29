package com.lanqiao.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfo {
	
    private Integer id;

    private String username;

    private String password;

    private Integer sex;

    private Date birthday;

    private Double money;
    
    private List<MyUserShares> userSharesList = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public List<MyUserShares> getUserSharesList() {
		return userSharesList;
	}

	public void setUserSharesList(List<MyUserShares> userSharesList) {
		this.userSharesList = userSharesList;
	}

	
}
