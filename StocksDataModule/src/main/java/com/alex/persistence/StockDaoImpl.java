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

@Component
public class StockDaoImpl implements StockDao {

	@Override
	public Map<String, yahoofinance.Stock> getQuote(String[] stockSymbol) throws IOException {
//		 TODO Auto-generated method stub
		Map<String, yahoofinance.Stock> stocks = YahooFinance.get(stockSymbol);

		return stocks;
	}

	

	@Override
	public Map<String, yahoofinance.Stock> getRecommendations(String marketCap) {
		// TODO Auto-generated method stub

		String[] stockSymbols = getStocksFromMarketCapDB(marketCap);

		for (int i = 0; i < stockSymbols.length; i++) {
			stockSymbols[i] = stockSymbols[i].replace(stockSymbols[i].charAt(stockSymbols[i].length() - 1), '.')
					.concat("NS");
		}
		Map<String, yahoofinance.Stock> stocks = null;
		try {
			stocks = YahooFinance.get(stockSymbols, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stocks;
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Stock[] getStockDataFromLibrary(String marketCap) {

		String[] stockSymbols = getStocksFromMarketCapDB(marketCap);

		for (int i = 0; i < stockSymbols.length; i++) {
			stockSymbols[i] = stockSymbols[i].replace(stockSymbols[i].charAt(stockSymbols[i].length() - 1), '.')
					.concat("NS");
		}
		Map<String, yahoofinance.Stock> stocks = null;
//		String[] symbols = new String[] {"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};
		try {
			stocks = YahooFinance.get(stockSymbols);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // single request
		Stock stockstoReturn[] = new Stock[stockSymbols.length];
		for (int i = 0; i < stockSymbols.length; i++) {
			if (stocks.get(stockSymbols[i]) != null) {

				// System.out.println(stocks.get(stockSymbols[i]).getDividend());
				stockstoReturn[i] = new Stock(stocks.get(stockSymbols[i]).getName(), stockSymbols[i], marketCap,
						String.valueOf(stocks.get(stockSymbols[i]).getQuote().getPrice()));

			}

		}

		return stockstoReturn;

	}

	// get data from database
	

	@Override
	public String getStocksOfMarketCapFromAPI(String marketCap) {
		// TODO Auto-generated method stub
		String[] stockSymbols = getStocksFromMarketCapDB(marketCap);

		String stockSymbol_str = "";
		for (String s : stockSymbols) {
			stockSymbol_str = stockSymbol_str + s + ",";
		}
		String URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-chart?interval=1d&symbol=+"
				+ stockSymbol_str + "&range=1d&region=IN";
		//System.out.println(URL);
		return callApi(URL);
		
		
	}

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
				//System.out.println(URL);
				return callApi(URL);
	}



	@Override
	public String getQuotesFromAPI(String[] stockSymbols) {
		
		
		for (int i = 0; i < stockSymbols.length; i++) {
			stockSymbols[i] = stockSymbols[i].replace(stockSymbols[i].charAt(stockSymbols[i].length() - 1), '.')
					.concat("NS");
		}
		
		// TODO Auto-generated method stub
		String URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-chart?interval=1d&symbol=+"
				+ stockSymbols+ "&range=1d&region=IN";
		//System.out.println(URL);
		return callApi(URL);
	}
	
	
	//HELPER FUNCTIONS ---------------------------------------------------------------------
	
	
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
