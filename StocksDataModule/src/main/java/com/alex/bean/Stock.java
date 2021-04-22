package com.alex.bean;

public class Stock {
	
	private String stockName;
	private String stockSymbol;
	private String marketCap;
	private String currentPrice;
	
	public Stock()
	{
		
	}
	public Stock(String stockName, String stockSymbol, String marketCap,String currentPrice) {
		super();
		this.stockName = stockName;
		this.stockSymbol = stockSymbol;
		this.marketCap = marketCap;
		this.currentPrice =currentPrice;
	}
	
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}
	public String getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public String getMarketCap() {
		return marketCap;
	}
	public void setMarketCap(String marketCap) {
		this.marketCap = marketCap;
	}
	
	

}
