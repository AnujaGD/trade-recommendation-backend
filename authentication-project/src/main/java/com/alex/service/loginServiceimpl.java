package com.alex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alex.bean.user;
import com.alex.persistence.userDao;
@Component
public class loginServiceimpl implements loginService {

	
	@Autowired
	private userDao ud;
	@Override
	public boolean login(user u) {
		// TODO Auto-generated method stub
		
		
		return ud.login(u);
	}

}
