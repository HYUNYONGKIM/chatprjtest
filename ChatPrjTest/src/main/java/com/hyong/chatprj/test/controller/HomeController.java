package com.hyong.chatprj.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	@RequestMapping("/jenkins-test")
	public String test() {
		return "잘실행되고 있음";
	}
}
