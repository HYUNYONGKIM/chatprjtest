package com.hyong.chatprj.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/chat")
	public String chat() {
		return "chat";
	}
}
