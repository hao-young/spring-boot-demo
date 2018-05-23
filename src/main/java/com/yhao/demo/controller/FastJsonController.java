package com.yhao.demo.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yhao.demo.entity.User;

@RestController
@RequestMapping("fastjson")
public class FastJsonController {

	@RequestMapping("test")
	public User test() {
		User user = new User();
		
		user.setId(1);
		user.setUsername("json");
		user.setPassword("json123");
		user.setBirthday(new Date());
		
		//模拟异常
		//int i = 1/0;
		
		return user;
	}
}
