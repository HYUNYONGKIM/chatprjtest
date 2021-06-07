package com.hyong.chatprj.test.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler{
	
	List<HashMap<String, Object>> rls = new ArrayList<>();
//	HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
	
	//메세지가 들어오면 시작됨.
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		String msg = message.getPayload(); //message 수정사항이 있으면 이렇게 빼도 되는데 아니면 그냥 message를 넣어도 될듯.
		JSONObject obj = jsonToObjectParser(msg); // msg를 jsonobject로 파싱
		
		String roomNum = (String) obj.get("roomNum");
		HashMap<String, Object> temp = new HashMap<>();
		
		if(rls.size()>0) {
			for(int i=0;i<rls.size();i++) {
				String roomNum_ = (String) rls.get(i).get("roomNum");
				if(roomNum_.equals(roomNum)) {
					temp = rls.get(i);
					break;
				}
			}
			
			for(String k : temp.keySet()) {
				if(k.equals("roomNum")) {
					continue;
				}
				WebSocketSession wss = (WebSocketSession) temp.get(k);
				if(wss!=null) {
					try {
						wss.sendMessage(new TextMessage(obj.toJSONString()));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
		
		
//		for(String key : sessionMap.keySet()) {
//			WebSocketSession wss = sessionMap.get(key);
//			try {
//				wss.sendMessage(new TextMessage(obj.toJSONString())); // 파싱된 jsonObject를 json으로 다시 변환.
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
		
	}
	
	//소켓연결이 되면 시작됨.
	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
//		sessionMap.put(session.getId(), session);
		
		boolean flag = false;
		//방번호 가져오기
		String uri = session.getUri().toString();
		String roomNum = uri.split("/chating/")[1];
		
		//방 목록 사이즈
		int idx = rls.size();
		
		//처음에 rls는 비워져 있으니까 rls를 조회해서 있으면 세션아이디만 넣고, 없으면 새로 만들어 넣어준다. 구분은 방번호로
		if (idx > 0) {
			for (int i = 0; i < idx; i++) {
				String roomNum_ = (String) rls.get(i).get("roomNum");
				if (roomNum_.equals(roomNum)) {
					flag = true;
					idx = i;
					break;
				}
			}
		}

		if(flag) {
			HashMap<String, Object> map = rls.get(idx);
			map.put(session.getId(), session);
		}else {
			HashMap<String,Object> map = new HashMap<>();
			map.put("roomNum", roomNum);
			map.put(session.getId(), session);
			rls.add(map);
		}
				
		JSONObject obj = new JSONObject();
		obj.put("type", "getId");
		obj.put("sessionId", session.getId()); 
		session.sendMessage(new TextMessage(obj.toJSONString())); //type : getId, sessionId : 해당아이디값 의 형태로 보냄. 
	}
	
	//소켓연결이 종료되면 시작됨.
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		if(rls.size()>0) {
			for (int i = 0; i < rls.size(); i++) {
				rls.get(i).remove(session.getId());
			}
		}
		
		super.afterConnectionClosed(session, status);
	}
	
	//json을 파싱하는 함수. json형태의 문자열을 jsonobject로 파싱처리
	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}
