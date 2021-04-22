package com.alex.persistence;



import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alex.bean.user;

@Component
public class userDaoimpl implements userDao {

	@Autowired
	private JdbcTemplate jt;
	
	@Override
	public boolean login(user u) {
		String sql = "select * from user_pass where username=?";
		
		ArrayList<user> l = (ArrayList<user>) jt.query(sql, new Object[] {u.getUserName()}, new userRowMapper());
		if(l.size()==0)
		{
			return false;
		}
		
		if(l.get(0).getPass_hash().equals(u.getPass_hash()))
		{
			return true;
		}
		return false;
	}

}
