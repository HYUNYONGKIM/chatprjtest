package com.hyong.chatprj.test.handler;

import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler{
	
	HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
	
	//메세지가 들어오면 시작됨.
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//		System.out.println(message); //TextMessage payload=[강남:안녕하세요], byteCount=22, last=true]

		String msg = message.getPayload(); //message 수정사항이 있으면 이렇게 빼도 되는데 아니면 그냥 message를 넣어도 될듯.
		
		for(String key : sessionMap.keySet()) {
			WebSocketSession wss = sessionMap.get(key);
			try {
				wss.sendMessage(new TextMessage(msg));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	//소켓연결이 되면 시작됨.
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		sessionMap.put(session.getId(), session);
	}
	
	//소켓연결이 종료되면 시작됨.
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		sessionMap.remove(session.getId());
		super.afterConnectionClosed(session, status);
	}
}
