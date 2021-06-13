package com.alex.persistence;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.alex.bean.Stock;
import com.alex.utils.StockRowMapper;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import java.util.*;
@Component
public class StockDaoImpl implements StockDao {

//	-------------------------USING LIBRARY-----------------------------------------

	// GET DATA OF SOME SYMBOLS**********************************************

	@Override
	public Map<String, yahoofinance.Stock> getQuote(String[] stockSymbol) throws IOException {
		Map<String, yahoofinance.Stock> stocks = YahooFinance.get(stockSymbol);

		return stocks;
	}

	// GET TOP 5 STOCKS **********************************************

	@Override
	public Map<String, yahoofinance.Stock> getRecommendations(String marketCap) throws IOException {

		String[] stockSymbols = getStocksFromMarketCapDB(marketCap);

		for (int i = 0; i < stockSymbols.length; i++) {
			stockSymbols[i] = stockSymbols[i].replace(stockSymbols[i].charAt(stockSymbols[i].length() - 1), '.')
					.concat("NS");
		}
		Map<String, yahoofinance.Stock> stocks = null;

		stocks = YahooFinance.get(stockSymbols, true);

		return stocks;
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// GET STOCKS OF CERTAIN MARKET
	// CAP**********************************************

	@Override
	public Stock[] getStockDataFromLibrary(String marketCap) throws IOException {

		String[] stockSymbols = getStocksFromMarketCapDB(marketCap);

		for (int i = 0; i < stockSymbols.length; i++) {
//			System.out.println(stockSymbols[i]);
			stockSymbols[i] = stockSymbols[i].replace(stockSymbols[i].charAt(stockSymbols[i].length() - 1), '.')
					.concat("NS");
//			System.out.println(stockSymbols[i]);
		}
		Map<String, yahoofinance.Stock> stocks = null;
//		String[] symbols = new String[] {"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};

		stocks = YahooFinance.get(stockSymbols);
		// single request
		System.out.println(stocks.size());
		Stock stockstoReturn[] = new Stock[stocks.size()];
		for (int i = 0; i < stocks.size(); i++) {
//			System.out.println(stocks.get(stockSymbols[i]));
			if (stocks.get(stockSymbols[i]) != null ) {
//				System.out.println(i);
//				 System.out.println(stocks.get(stockSymbols[i]).getName());
				stockstoReturn[i] = new Stock(stocks.get(stockSymbols[i]).getName(), stockSymbols[i], marketCap,
						String.valueOf(stocks.get(stockSymbols[i]).getQuote().getPrice()),
						String.valueOf(stocks.get(stockSymbols[i]).getQuote().getOpen()));

			}

		}
		

//		for(int i=0;i<stockstoReturn.length;i++)
//		{
//			System.out.println(stockstoReturn[i].getStockName());
//		}
		return stockstoReturn;

	}

//	-------------------------USING API-----------------------------------------

	// GET STOCKS OF CERTAIN MARKET
	// CAP**********************************************
	@Override
	public String getStocksOfMarketCapFromAPI(String marketCap) {
		// TODO Auto-generated method stub
		String[] stockSymbols = getStocksFromMarketCapDB(marketCap);

		String stockSymbol_str = "";
		for (String s : stockSymbols) {
			stockSymbol_str = stockSymbol_str + s.replace(s.charAt(s.length() - 1), '.').concat("NS") + ",";
		}

		String URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=IN&symbols="
				+ stockSymbol_str;
//		String URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-chart?interval=1d&symbol=+"
//				+ stockSymbol_str + "&range=1d&region=IN";
		// System.out.println(URL);
		System.out.println(URL);
//		 return null;
		return callApi(URL);

	}

	// GET TOP 5 STOCKS **********************************************
	@Override
	public String getRecommendationsFromAPI(String marketCap) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String[] stockSymbols = getStocksFromMarketCapDB(marketCap);

		String stockSymbol_str = "";
		for (String s : stockSymbols) {
			stockSymbol_str = stockSymbol_str + s + ",";
		}
		String URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-chart?interval=1d&symbol=+"
				+ stockSymbol_str + "&range=1d&region=IN";
		// System.out.println(URL);
		return callApi(URL);
	}

	// GET DATA OF SOME SYMBOLS**********************************************
	@Override
	public String getQuotesFromAPI(String[] stockSymbols) {

		String stockSymbols_str = "";
		for (int i = 0; i < stockSymbols.length; i++) {
			stockSymbols_str = stockSymbols[i].concat(".NS");
		}
		String URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=IN&symbols="
				+ stockSymbols_str;

		System.out.println(URL);
		// TODO Auto-generated method stub

//		 return null;
		return callApi(URL);
	}

//	-------------------------HELPER FUNCTIONS-----------------------------------------

	// GET DATA FROM DATABASE**********************************************
	@SuppressWarnings("deprecation")
	public String[] getStocksFromMarketCapDB(String marketCap) {

		String SQL = "Select stockSymbol from stocks where marketCap=?";

		ArrayList<String> stockSymbols = (ArrayList<String>) jdbcTemplate.query(SQL, new Object[] { marketCap },
				new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub
						return rs.getString(1);
					}
				});

		String stocks[] = new String[stockSymbols.size()];

		for (int i = 0; i < stockSymbols.size(); i++) {
			stocks[i] = stockSymbols.get(i);
		}

		return stocks;

	}

	// CALL API**********************************************
	public String callApi(String URL) {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL))
				.header("x-rapidapi-key", "5369cef33amshe576e689fc34c2ep164d84jsne2221da15dab")
				.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.body();
	}

}
