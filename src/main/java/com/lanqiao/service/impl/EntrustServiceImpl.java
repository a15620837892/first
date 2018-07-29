package com.lanqiao.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanqiao.dao.BargainMapper;
import com.lanqiao.dao.EntrustMapper;
import com.lanqiao.dao.SharesMapper;
import com.lanqiao.dao.UserMapper;
import com.lanqiao.dao.UserSharesMapper;
import com.lanqiao.entity.Bargain;
import com.lanqiao.entity.Entrust;
import com.lanqiao.entity.EntrustExample;
import com.lanqiao.entity.Shares;
import com.lanqiao.entity.User;
import com.lanqiao.entity.UserShares;
import com.lanqiao.entity.UserSharesExample;
import com.lanqiao.service.EntrustService;
import com.lanqiao.vo.BargainAndSharesAndEntrustInfo;
import com.lanqiao.websocket.MyTextWebSocketHandler;

@Service
public class EntrustServiceImpl implements EntrustService {
	
	@Autowired
	private EntrustMapper entrustMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserSharesMapper userSharesMapper;
	
	@Autowired
	private BargainMapper bargainMapper;
	
	@Autowired
	private SharesMapper sharesMapper;
	
	@Autowired
	private MyTextWebSocketHandler myTextWebSocketHandler;

	//买入委托
	@Override
	public int insertEntrustAndBuyShares(Entrust buyEntrust) throws Exception {
		//是否更新最新价格标志
		boolean isChangeNewPrice = false;
		//最低卖出价
		Double minSellPrice = entrustMapper.getSharesMinSellPriceBySid(buyEntrust.getSid());
		//最高买入价
		Double maxBuyPrice = entrustMapper.getSharesMaxBuyPriceBySid(buyEntrust.getSid());
		
		//查询买家用户信息并且扣除用户money  并更新
		User buyUser = userMapper.selectByPrimaryKey(buyEntrust.getUid());
		double total = buyEntrust.getPrice() * buyEntrust.getNumber();
		buyUser.setMoney(buyUser.getMoney() - total);
		userMapper.updateByPrimaryKey(buyUser);
		
		//查询买家用户需要买的股票信息
		UserSharesExample userSharesExample = new UserSharesExample();
		userSharesExample.createCriteria().andUidEqualTo(buyEntrust.getUid()).andSidEqualTo(buyEntrust.getSid());
		List<UserShares> selectByExample = userSharesMapper.selectByExample(userSharesExample);
		UserShares buyUserShares = null;
		if(selectByExample.size() != 0) {
			buyUserShares = selectByExample.get(0);
		}
		
		//查看有无可以相互消除的委托单
		EntrustExample example = new EntrustExample();
		example.createCriteria().andTypeEqualTo(1).andPriceEqualTo(buyEntrust.getPrice()).andSidEqualTo(buyEntrust.getSid());
		example.setOrderByClause("date asc");
		List<Entrust> sameSellPriceEntrustList = entrustMapper.selectByExample(example);
		
		//生成websocket传递信息对象
		BargainAndSharesAndEntrustInfo webInfo = new BargainAndSharesAndEntrustInfo();
		
		//如果有可以相互消除的委托单
		if(sameSellPriceEntrustList.size() != 0) {
			
			int bargainNumber = 0;
			int i = 0;
			for (Entrust sellEntrust : sameSellPriceEntrustList) {
				if(buyEntrust.getNumber() > 0) {
					if(buyEntrust.getNumber() >= sellEntrust.getNumber()) {
						//需求量大于等于商家卖出量  生成成交单  成交量为商家卖出量
						bargainNumber = sellEntrust.getNumber();
					}else {
						//需求量小于商家卖出量   生成成交单   成交量为自己买入量
						bargainNumber = buyEntrust.getNumber();
					}
					//生成成交单
					Bargain bargain = new Bargain();
					bargain.setDate(new Date());
					bargain.setNumber(bargainNumber);
					bargain.setPrice(sellEntrust.getPrice());
					bargain.setSid(sellEntrust.getSid());
					bargain.setTotal(bargainNumber * sellEntrust.getPrice());
					bargainMapper.insert(bargain);
					//将成交信息加入到websocket传递的对象中
					webInfo.getBargainList().add(bargain);
					//更新股票的成交量
					Shares shares = sharesMapper.selectByPrimaryKey(buyEntrust.getSid());
					shares.setBargaincount(shares.getBargaincount() + bargain.getNumber());
					sharesMapper.updateByPrimaryKey(shares);
					//买家的股票更新
					if(buyUserShares == null) {
						buyUserShares = new UserShares();
						buyUserShares.setSid(buyEntrust.getSid());
						buyUserShares.setUid(buyEntrust.getUid());
						buyUserShares.setNumber(bargainNumber);
						userSharesMapper.insert(buyUserShares);
						buyUserShares = userSharesMapper.selectByExample(userSharesExample).get(0);
					}else {
						buyUserShares.setNumber(buyUserShares.getNumber() + bargainNumber);
					}
					//更新卖家的money
					User sellUser = userMapper.selectByPrimaryKey(sellEntrust.getUid());
					sellUser.setMoney(sellUser.getMoney() + bargain.getTotal());
					userMapper.updateByPrimaryKey(sellUser);
					//更新买入委托的数量
					buyEntrust.setNumber(buyEntrust.getNumber() - bargainNumber);
					//更新卖出委托的数量
					sellEntrust.setNumber(sellEntrust.getNumber() - bargainNumber);
					if(sellEntrust.getNumber() == 0) {
						if(i == (sameSellPriceEntrustList.size() - 1)) {
							if(minSellPrice != null && minSellPrice.equals(sellEntrust.getPrice())) {
								isChangeNewPrice = true;
							}
						}
						entrustMapper.deleteByPrimaryKey(sellEntrust.getId());
					}else {
						entrustMapper.updateByPrimaryKey(sellEntrust);
					}
				}else {
					break;
				}
				i++;
			}
		}
		
		//如果买入委托还有剩余需求量则   -->  生成买入委托单
		if(buyEntrust.getNumber() > 0) {
			if(maxBuyPrice == null || buyEntrust.getPrice() > maxBuyPrice) {
				isChangeNewPrice = true;
			}
			entrustMapper.insert(buyEntrust);
		}
		
		//更新买家对应股票信息
		if(buyUserShares != null) {
			userSharesMapper.updateByPrimaryKey(buyUserShares);
		}
		
		//更新最新价格
		if(isChangeNewPrice) {
			 minSellPrice = entrustMapper.getSharesMinSellPriceBySid(buyEntrust.getSid());
			 maxBuyPrice = entrustMapper.getSharesMaxBuyPriceBySid(buyEntrust.getSid());
			 if(minSellPrice != null && maxBuyPrice != null) {
				 double newPrice = (minSellPrice + maxBuyPrice) / 2;
				 Shares shares = new Shares();
				 shares.setId(buyEntrust.getSid());
				 shares.setNewprice(newPrice);
				 sharesMapper.updateByPrimaryKeySelective(shares);
			 }
			 
			 //推送最新价格
			 BargainAndSharesAndEntrustInfo priceInfo = new BargainAndSharesAndEntrustInfo();
			 
			 Shares newShares = sharesMapper.selectByPrimaryKey(buyEntrust.getSid());
			 priceInfo.setShares(newShares);
			 
			 myTextWebSocketHandler.sendMessageToUsers(priceInfo);
			 
		}
		
		//推送成交信息以及委托更新标志
		webInfo.setEntrust(true);
		myTextWebSocketHandler.sendMessageToUsersBySid(String.valueOf(buyEntrust.getSid()), webInfo);
		
		return 1;
	}

	//卖出委托
	@Override
	public int insertEntrustAndSellShares(Entrust sellEntrust) throws Exception {
		//是否更新最新价格标志
		boolean isChangeNewPrice = false;
		//最低卖出价
		Double minSellPrice = entrustMapper.getSharesMinSellPriceBySid(sellEntrust.getSid());
		//最高买入价
		Double maxBuyPrice = entrustMapper.getSharesMaxBuyPriceBySid(sellEntrust.getSid());

		//扣除用户卖出的股票
		UserSharesExample example = new UserSharesExample();
		example.createCriteria().andUidEqualTo(sellEntrust.getUid()).andSidEqualTo(sellEntrust.getSid());
		UserShares userShares = userSharesMapper.selectByExample(example).get(0);
		userShares.setNumber(userShares.getNumber() - sellEntrust.getNumber());
		if(userShares.getNumber() == 0) {
			userSharesMapper.deleteByPrimaryKey(userShares.getId());
		}else {
			userSharesMapper.updateByPrimaryKey(userShares);
		}
		
		//查询卖家用户个人信息
		User sellUser = userMapper.selectByPrimaryKey(sellEntrust.getUid());
		
		//查看有无可以相互消除的委托单
		EntrustExample entrustExample = new EntrustExample();
		entrustExample.createCriteria().andTypeEqualTo(0).andSidEqualTo(sellEntrust.getSid()).andPriceEqualTo(sellEntrust.getPrice());
		List<Entrust> sameBuyPriceEntrustList = entrustMapper.selectByExample(entrustExample);
		
		//生成websocket传递信息对象
		BargainAndSharesAndEntrustInfo webInfo = new BargainAndSharesAndEntrustInfo();
		
		//如果列表不为空
		if(sameBuyPriceEntrustList.size() != 0) {
			int bargainNumber = 0;
			int i = 0;
			for (Entrust buyEntrust : sameBuyPriceEntrustList) {
				if(sellEntrust.getNumber() > 0) {
					//取数量中的小者作为成交量
					if(sellEntrust.getNumber() >= buyEntrust.getNumber()) {
						bargainNumber = buyEntrust.getNumber();
					}else {
						bargainNumber = sellEntrust.getNumber();
					}
					//生成成交单
					Bargain bargain = new Bargain();
					bargain.setDate(new Date());
					bargain.setNumber(bargainNumber);
					bargain.setPrice(buyEntrust.getPrice());
					bargain.setSid(buyEntrust.getSid());
					bargain.setTotal(bargainNumber * buyEntrust.getPrice());
					bargainMapper.insert(bargain);
					//将成交信息加入到websocket传递的对象中
					webInfo.getBargainList().add(bargain);
					//更新股票的成交量
					Shares shares = sharesMapper.selectByPrimaryKey(buyEntrust.getSid());
					shares.setBargaincount(shares.getBargaincount() + bargain.getNumber());
					sharesMapper.updateByPrimaryKey(shares);
					//更新卖家money
					sellUser.setMoney(sellUser.getMoney() + bargain.getTotal());
					//更新买家股票
					UserSharesExample userSharesExample = new UserSharesExample();
					userSharesExample.createCriteria().andUidEqualTo(buyEntrust.getUid()).andSidEqualTo(buyEntrust.getSid());
					List<UserShares> buySharesList = userSharesMapper.selectByExample(userSharesExample);
					if(buySharesList.size() != 0) {
						UserShares buyShares = buySharesList.get(0);
						buyShares.setNumber(buyShares.getNumber() + bargainNumber);
						userSharesMapper.updateByPrimaryKey(buyShares);
					}else {
						UserShares buyShares = new UserShares();
						buyShares.setUid(buyEntrust.getUid());
						buyShares.setSid(buyEntrust.getSid());
						buyShares.setNumber(bargainNumber);
						userSharesMapper.insert(buyShares);
					}
					//更新买入委托数量
					buyEntrust.setNumber(buyEntrust.getNumber() - bargainNumber);
					if(buyEntrust.getNumber() == 0) {
						if(i == (sameBuyPriceEntrustList.size() - 1)) {
							if(maxBuyPrice != null && maxBuyPrice.equals(buyEntrust.getPrice())) {
								isChangeNewPrice = true;
							}
						}
						entrustMapper.deleteByPrimaryKey(buyEntrust.getId());
					}else {
						entrustMapper.updateByPrimaryKey(buyEntrust);
					}
					//更新卖出委托数量
					sellEntrust.setNumber(sellEntrust.getNumber() - bargainNumber);
				}else {
					break;
				}
				i++;
			}
		}
		
		//如果卖出委托还有剩余量则   -->  生成卖出委托单
		if(sellEntrust.getNumber() > 0) {
			if(minSellPrice == null || sellEntrust.getPrice() < minSellPrice) {
				isChangeNewPrice = true;
			}
			entrustMapper.insert(sellEntrust);
		}
		
		//更新卖家money
		userMapper.updateByPrimaryKey(sellUser);
		
		
		//更新最新价格
		if(isChangeNewPrice) {
			 minSellPrice = entrustMapper.getSharesMinSellPriceBySid(sellEntrust.getSid());
			 maxBuyPrice = entrustMapper.getSharesMaxBuyPriceBySid(sellEntrust.getSid());
			 if(minSellPrice != null && maxBuyPrice != null) {
				 double newPrice = (minSellPrice + maxBuyPrice) / 2;
				 Shares shares = new Shares();
				 shares.setId(sellEntrust.getSid());
				 shares.setNewprice(newPrice);
				 sharesMapper.updateByPrimaryKeySelective(shares);
				 
				 //推送最新价格
				 BargainAndSharesAndEntrustInfo priceInfo = new BargainAndSharesAndEntrustInfo();
				 
				 Shares newShares = sharesMapper.selectByPrimaryKey(sellEntrust.getSid());
				 priceInfo.setShares(newShares);
				 
				 myTextWebSocketHandler.sendMessageToUsers(priceInfo);
			 }
		}
		
		//推送成交信息以及委托更新标志
		webInfo.setEntrust(true);
		myTextWebSocketHandler.sendMessageToUsersBySid(String.valueOf(sellEntrust.getSid()), webInfo);
		
		return 1;
	}

	@Override
	public List<Entrust> getEntrustListByUidAndSid(Integer uid,Integer sid) throws SQLException {
		EntrustExample example = new EntrustExample();
		example.createCriteria().andUidEqualTo(uid).andSidEqualTo(sid);
		example.setOrderByClause("date desc");
		return entrustMapper.selectByExample(example);
	}

	@Override
	public List<Entrust> getSellEntrustGroupByPriceList(Integer sid) throws SQLException {
		return entrustMapper.getSellEntrustGroupByPriceList(sid);
	}

	@Override
	public List<Entrust> getBuyEntrustGroupByPriceList(Integer sid) throws SQLException {
		return entrustMapper.getBuyEntrustGroupByPriceList(sid);
	}

	@Override
	public int deleteReomveEntrustById(Integer eid) throws Exception {
		//查询要撤销的委托
		Entrust entrust = entrustMapper.selectByPrimaryKey(eid);
		//是否更新最新价格标志
		boolean isChangeNewPrice = false;
		//最低卖出价
		Double minSellPrice = entrustMapper.getSharesMinSellPriceBySid(entrust.getSid());
		//最高买入价
		Double maxBuyPrice = entrustMapper.getSharesMaxBuyPriceBySid(entrust.getSid());
		
		if(entrust.getType() == 0) {
			//撤销买入委托
			User user = userMapper.selectByPrimaryKey(entrust.getUid());
			user.setMoney(user.getMoney() + entrust.getPrice() * entrust.getNumber());
			userMapper.updateByPrimaryKey(user);
			//判断是否需要更新最新价格
			if(maxBuyPrice != null && maxBuyPrice.equals(entrust.getPrice())) {
				isChangeNewPrice = true;
			}
		}else {
			//撤销卖出委托
			UserSharesExample example = new UserSharesExample();
			example.createCriteria().andUidEqualTo(entrust.getUid()).andSidEqualTo(entrust.getSid());
			List<UserShares> selectByExample = userSharesMapper.selectByExample(example);
			if(selectByExample.size() == 0) {
				UserShares newUserShares = new UserShares();
				newUserShares.setUid(entrust.getUid());
				newUserShares.setSid(entrust.getSid());
				newUserShares.setNumber(entrust.getNumber());
				userSharesMapper.insert(newUserShares);
			}else {
				UserShares userShares = selectByExample.get(0);
				userShares.setNumber(userShares.getNumber() + entrust.getNumber());
				userSharesMapper.updateByPrimaryKey(userShares);
			}
			//判断是否需要更新最新价格
			if(minSellPrice != null && minSellPrice.equals(entrust.getPrice())) {
				isChangeNewPrice = true;
			}
		}
		entrustMapper.deleteByPrimaryKey(eid);
		//更新最新价格
		if(isChangeNewPrice) {
			minSellPrice = entrustMapper.getSharesMinSellPriceBySid(entrust.getSid());
			maxBuyPrice = entrustMapper.getSharesMaxBuyPriceBySid(entrust.getSid());
			if(minSellPrice != null && maxBuyPrice != null) {
				double newPrice = (minSellPrice + maxBuyPrice) / 2;
				Shares shares = new Shares();
				shares.setId(entrust.getSid());
				shares.setNewprice(newPrice);
				sharesMapper.updateByPrimaryKeySelective(shares);
				
				 //推送最新价格
				 BargainAndSharesAndEntrustInfo priceInfo = new BargainAndSharesAndEntrustInfo();
				 
				 Shares newShares = sharesMapper.selectByPrimaryKey(entrust.getSid());
				 priceInfo.setShares(newShares);
				 
				 myTextWebSocketHandler.sendMessageToUsers(priceInfo);
			}
		}
		
		//生成websocket传递信息对象
		BargainAndSharesAndEntrustInfo webInfo = new BargainAndSharesAndEntrustInfo();
		webInfo.setEntrust(true);
		myTextWebSocketHandler.sendMessageToUsersBySid(String.valueOf(entrust.getSid()), webInfo);
		
		return 1;
	}

}
