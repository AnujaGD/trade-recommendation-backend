package com.alex.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.alex.bean.user;

public class userRowMapper implements RowMapper<user> {

	@Override
	public user mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		user u =new user();
		u.setUserName(rs.getString("username"));
		u.setPass_hash(rs.getString("pass_hash"));
		return u;
	}

}
