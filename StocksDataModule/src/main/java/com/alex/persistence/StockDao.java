package com.alex.persistence;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alex.bean.Stock;

@Component
public interface StockDao {

	public Map<String, yahoofinance.Stock> getQuote(String[] stockSymbol) throws IOException;
	public Map<String, yahoofinance.Stock>  getRecommendations(String marketCap) throws IOException;
	public Stock[] getStockDataFromLibrary(String marketCap) throws IOException;
	
	public String getStocksOfMarketCapFromAPI(String marketCap);
	public String getRecommendationsFromAPI(String marketCap);
	public String getQuotesFromAPI(String[] stockSymbols);
}
