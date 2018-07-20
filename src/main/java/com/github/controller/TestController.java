package com.github.controller;

import com.github.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("t")
public class TestController {

	@Resource private UserService userService;


	@GetMapping({"", "/"})
	public Object index() {

		return "login-t";
	}

	@ResponseBody
	@RequestMapping("page")
	public Object page() {

		return "success";
	}

}
