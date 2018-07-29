package com.lanqiao.utils;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lanqiao.service.SharesService;

@Component
public class UpdateSharesNewPriceAndBargaincount {
	
	@Autowired
	private SharesService sharesService;

	public void execute() {
		try {
			sharesService.UpdateNewPriceAndBargaincount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
