package com.hyong.chatprj.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyong.chatprj.test.entity.Room;

@Controller
public class MainController {

	private List<Room> roomList = new ArrayList<>();
	static int roomNum = 0;
	
	@RequestMapping("/room")
	public String room() {
		return "room";
	}
	
	@RequestMapping("/createRoom")
	@ResponseBody 
	public List<Room> createRoom(@RequestParam HashMap<Object, Object> params) {
		String roomName = (String) params.get("roomName");
		if(roomName != null && !roomName.trim().equals("")) {
			Room room = new Room();
			room.setRoomName(roomName);
			room.setRoomNum(++roomNum);
			roomList.add(room);
		}
		return roomList;
	}
	
	@RequestMapping("/getRoom")
	@ResponseBody 
	public List<Room> getRoom(@RequestParam HashMap<Object, Object> params) {
		
		return roomList;
	}
	
	@RequestMapping("/moveChating")
	public String chating(@RequestParam HashMap<Object,Object> params, Model model) {
		int roomNum = Integer.parseInt((String) params.get("roomNum"));
		
		//임시 Room List : 만들어진 roomList객체를 Room객체 중 roomNum이랑 같은 것만 필터. collect로 모아서 리스트만듦
		//Java의 스트림과 람다식을 사용 // stream()으로 객체생성->filter는 가공 -> collect는 결과물
		List<Room> new_list = roomList.stream().filter(o->o.getRoomNum()==roomNum).collect(Collectors.toList());
		if(new_list != null && new_list.size()>0) {
			model.addAttribute("roomName", params.get("roomName"));
			model.addAttribute("roomNum", params.get("roomNum"));
			return "chat";
		} else {
			return "room";
		}
	}
	
	
	
	@RequestMapping("/chat")
	public String chat() {
		return "chat";
	}
	
	
	
}
