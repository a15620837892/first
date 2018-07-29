package com.lanqiao.vo;

import java.util.ArrayList;
import java.util.List;

import com.lanqiao.entity.Bargain;
import com.lanqiao.entity.Shares;

public class BargainAndSharesAndEntrustInfo {
	
	private List<Bargain> bargainList = new ArrayList<>();
	
	private Shares shares;
	
	private boolean entrust;

	public List<Bargain> getBargainList() {
		return bargainList;
	}

	public void setBargainList(List<Bargain> bargainList) {
		this.bargainList = bargainList;
	}

	public Shares getShares() {
		return shares;
	}

	public void setShares(Shares shares) {
		this.shares = shares;
	}

	public boolean isEntrust() {
		return entrust;
	}

	public void setEntrust(boolean entrust) {
		this.entrust = entrust;
	}

}
