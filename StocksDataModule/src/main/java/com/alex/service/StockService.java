package com.alex.service;

import org.springframework.stereotype.Component;

import com.alex.bean.Stock;

@Component
public interface StockService {
	
	public Stock[] getQuote(String[] stockSymbol);
	public Stock[] getStocksOfMarketCap(String marketCap);
	public Stock[] getRecommendations(String marketCap);
	public Stock[] getQuoteFromAPI(String[] stockSymbol);
	public Stock[] getStocksOfMarketCapFromAPI(String marketCap);
	public Stock[] getRecommendationsFromAPI(String marketCap);

}
