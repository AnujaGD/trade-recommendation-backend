package com.alex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alex.bean.Stock;
import com.alex.persistence.StockDao;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

@Component
public class StockServiceImpl implements StockService {

	@Autowired
	private StockDao stockDao;

	@Override
	public Stock[] getQuote(String[] stockSymbol) {
		Stock stockToreturn[] = new Stock[stockSymbol.length];
		try {
			 Map<String, yahoofinance.Stock>stocks =  stockDao.getQuote(stockSymbol);
			 for(int i=0;i<stockSymbol.length;i++)
			 {
				 if(stocks.get(stockSymbol[i])!=null)
				 {
					 stockToreturn[i] = new Stock(stocks.get(stockSymbol[i]).getName(), stockSymbol[i], "",
								String.valueOf(stocks.get(stockSymbol[i]).getQuote().getPrice()));
				 }
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stockToreturn;

	}


	@Override
	public Stock[] getStocksOfMarketCap(String marketCap) {
		// TODO Auto-generated method stub
		return stockDao.getStockDataFromLibrary(marketCap);
		
	}

	@Override
	public Stock[] getRecommendations(String marketCap) {
		// TODO Auto-generated method stub
		Map<String, yahoofinance.Stock> stocks = stockDao.getRecommendations(marketCap);

		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.WEEK_OF_MONTH, -2);
		String[] stockSymbols = new String[stocks.size()];
		int cnt = 0;
		for (String s : stocks.keySet()) {
			stockSymbols[cnt++] = s;
		}
		cnt=0;
		Map<String, Double> stockGrowthParameter = new HashMap<String, Double>();

		double dividend = 0;
		double initialPrice = 0;
		List<HistoricalQuote> temp;
		double finalPrice = 0;
		for (int i = 0; i < stockSymbols.length; i++) {
			if (stocks.get(stockSymbols[i]) != null) {
				try {
					temp = stocks.get(stockSymbols[i]).getHistory(from, to, Interval.DAILY);
					initialPrice = Double.parseDouble(temp.get(0).getOpen().toString());
					finalPrice = Double.parseDouble(temp.get(temp.size() - 1).getClose().toString());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			dividend = Double.parseDouble(stocks.get(stockSymbols[i]).getDividend().getAnnualYieldPercent().toString())
//					* Double.parseDouble(stocks.get(stockSymbols[i]).getQuote().getPrice().toString());

				stockGrowthParameter.put(stockSymbols[i], (finalPrice - initialPrice + dividend) / initialPrice);
			}
		}
//		System.out.println(stockGrowthParameter);
//		System.out.println("*******************************");
		Map<String, Double> sortedStockGrowthParameters = stockGrowthParameter.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
		
//		System.out.println(sortedStockGrowthParameters);
		
		Stock recommendedStocks[] = new Stock[5];
		for(String s :sortedStockGrowthParameters.keySet())
		{
			recommendedStocks[cnt++] = new Stock(stocks.get(s).getName(), s, marketCap,
					String.valueOf(stocks.get(s).getQuote().getPrice()));
			if(cnt==5)
			{
				break;
			}
		}
		cnt=0;
		
		return recommendedStocks;
	}

	@Override
	public Stock[] getQuoteFromAPI(String[] stockSymbol) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Stock[] getStocksOfMarketCapFromAPI(String marketCap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock[] getRecommendationsFromAPI(String marketCap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Stock[] formatJSON(String response) {
		Stock[] recommendations = new Stock[50];

		return recommendations;

//		ArrayList<String> marketCapValues = new ArrayList<>();
//		JSONObject jsonObject=null;
//		try {
//			jsonObject = (JSONObject)new JSONParser().parse(response);
//		} catch (org.json.simple.parser.ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	   JSONObject response_obj = (JSONObject) jsonObject.get("quoteResponse");
//	   JSONArray array = (JSONArray) response_obj.get("result");
//	   JSONObject[] values = new JSONObject[array.size()];
//	   System.out.println(array.size());
//	   for(int i=0;i<array.size();i++)
//	   {
//		   values[i] =(JSONObject) array.get(i);
//		   marketCapValues.add((String) String.valueOf(values[i].get("marketCap")));
//	   }
//	return response;
	}
}
