package com.yhao.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 在这里的 @RestController (等价于@controller 和 @Requestbody)
 * 方法中return返回的都是json
 * 能返回jsp,html页面了
 * @author yhao
 *
 */
@RestController
public class HelloWorldController {
	
	/**
	 * 在这里使用的@RequestMapping 建立请求映射：
	 * http://127.0.0.1:8080/hello
	 * @return
	 */
	@RequestMapping("/hello")
	public String index() {
		return "Hello world!";
	}
}
