package com.alex.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.alex.bean.Stock;

public class StockRowMapper implements RowMapper<Stock> {

	@Override
	public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		Stock s = new Stock();
		s.setStockName(rs.getString("stockName"));
		s.setStockSymbol(rs.getString("stockSymbol"));
		s.setMarketCap(rs.getString("marketCap"));
		return s;
	}

}
