package com.alex.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alex.bean.user;
import com.alex.service.loginService;

import ch.qos.logback.classic.Logger;

@CrossOrigin
@RestController
public class userResource {


	@Autowired
	private loginService ls;
	
	/*
	 * @GetMapping(path = "/login/{uname}/{pass}") private boolean
	 * login(@PathVariable("uname") String uname,@PathVariable("pass") String pass)
	 * { user a = new user(uname,pass); return ls.login(a);
	 * 
	 * }
	 */
	
	
	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	public boolean login(@RequestBody user user) {
	    //code
		return ls.login(user);
	}
}
