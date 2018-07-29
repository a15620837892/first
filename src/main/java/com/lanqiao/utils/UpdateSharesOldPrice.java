package com.lanqiao.utils;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lanqiao.service.SharesService;

@Component
public class UpdateSharesOldPrice {
	
	@Autowired
	private SharesService sharesService;

	public void execute() {
		try {
			sharesService.updateOldPrice();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
