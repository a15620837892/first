package com.lanqiao.websocket;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

public class MyTextWebSocketHandler extends TextWebSocketHandler {
	
	private Gson gson = new Gson();

	//保存用户连接的array
	private final static ArrayList<WebSocketSession> users = new ArrayList<>();

	//处理传来的信息
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//sendMessageToUsers(message);
	}

	//连接成功后执行
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		users.add(session);
		System.out.println(session.getAttributes().get("WEBSOCKET_SID")+"号股票的用户上线");
		System.out.println("当前在线用户数量：" + users.size());
	}

	//出现错误后执行
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if(session.isOpen()) {
			session.close();
		}
		users.remove(session);
	}

	//连接关闭后执行
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		users.remove(session);
		System.out.println(session.getAttributes().get("WEBSOCKET_SID")+"号股票的用户下线");
		System.out.println("剩余在线用户数量：" + users.size());
	}
	
	//给所有在线用户发信息
	public void sendMessageToUsers(Object obj) throws IOException {
		//将obj转为字符串
		String json = gson.toJson(obj);
		TextMessage message = new TextMessage(json);
		//发送
		for (WebSocketSession user : users) {
			if(user.isOpen()) {
				user.sendMessage(message);
			}
		}
	}
	
	//给指定用户发信息
	public void sendMessageToUsersBySid(String sid, Object obj) throws IOException {
		//将obj转为字符串
		String json = gson.toJson(obj);
		TextMessage message = new TextMessage(json);
		//发送
		for (WebSocketSession user : users) {
			if(user.getAttributes().get("WEBSOCKET_SID").equals(sid)) {
				if(user.isOpen()) {
					user.sendMessage(message);
				}
			}
		}
	}
	
}
