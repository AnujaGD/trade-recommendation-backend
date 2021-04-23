package com.alex.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.money.CurrencyUnitFormatter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alex.bean.Stock;

@Component
public class CsvToDatabaseImpl implements CsvToDatabase {

	
	private final BigInteger LARGE_CAP_UL = new BigInteger("751114500000"); // 10 billion dollars

	private final BigInteger MID_CAP_UL = new BigInteger("751114500000"); // 10 billion dollars

	private final BigInteger MID_CAP_LL = new BigInteger("150140300000");// 2 billion dollars

	private final BigInteger LOW_CAP_LL = new BigInteger("22521045000"); // 300 million dollars

	@Override
	public void csvToDb(String path,String marketCap) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
	
		File f = new File(path);
		System.out.println(f.getAbsolutePath());
		Scanner sc = new Scanner(f);
		sc.useDelimiter("\n");
		ArrayList<String> stockNamesandSymbols = new ArrayList<>();

		while (sc.hasNext()) {
			stockNamesandSymbols.add(sc.next());
		}

		insertIntodb(stockNamesandSymbols,marketCap);

	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertIntodb(ArrayList<String> stockNamesandSymbols,String marketCap) {
		String sql = "insert into stocks (stockName,stockSymbol,marketCap) values (?,?,?)";
		int totalValues = stockNamesandSymbols.size();
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
		Object[] params = null;

		for (int i = 1; i < totalValues; i++) {
			params = new Object[] { stockNamesandSymbols.get(i).split(",")[0],
					stockNamesandSymbols.get(i).split(",")[1], marketCap };
			jdbcTemplate.update(sql, params, types);
		}

	}

	@Override
	public void updateMarketCap() {
		// TODO Auto-generated method stub
		String select_stmt = "select * from stocks";
		String insert_stmt = "";
		int[] types = new int[] { Types.VARCHAR,Types.VARCHAR };
		Object[] params = null;

		ArrayList<Stock> stocks = (ArrayList<Stock>) jdbcTemplate.query(select_stmt, new Object[] {},
				new StockRowMapper());
		
		System.out.println(stocks.size());
		ArrayList<String> marketCaps_Half_1 = getHalfMarketValuesFromApi(stocks);
		ArrayList<String> marketCaps_Half_2 = getHalf_2_MarketValuesFromApi(stocks);
		
		for( String k :marketCaps_Half_2)
		{
			marketCaps_Half_1.add(k);
		}
		System.out.println(marketCaps_Half_1.size());
		
		
		for (int i = 0; i < stocks.size()-1; i++) {
			params = new Object[] {categoriseIntoLMS(marketCaps_Half_1.get(i)),stocks.get(i).getStockSymbol()};
			insert_stmt = "update stocks set marketCap=? where stockSymbol=?";
//			System.out.println(categoriseIntoLMS(marketCaps_Half_1.get(i)))	;
			jdbcTemplate.update(insert_stmt, params, types);
		}
		
		

	}

	

	private Object categoriseIntoLMS(String marketCapValue) {
		// TODO Auto-generated method stub

		if(marketCapValue!="null")
		{
			BigInteger mCap = new BigInteger(marketCapValue);
			Object res = null;
			if (mCap.compareTo(LARGE_CAP_UL) > 0) // mcap is larger
			{
				res = "Large Cap";
			} else if (mCap.compareTo(MID_CAP_LL) > 0 && mCap.compareTo(MID_CAP_UL) < 0) {
				res = "Mid Cap";
			} else if (mCap.compareTo(LOW_CAP_LL) > 0 && mCap.compareTo(MID_CAP_LL) < 0) {
				res = "Low Cap";
			}
			else
			{
				res = "Low Cap";
			}
			return res;
		}
		return "NULL";
		
	}

	
	
	public ArrayList<String> getHalfMarketValuesFromApi(ArrayList<Stock> stocks) {
		ArrayList<String> marketCapValues = new ArrayList<>();
		
		String halfSymbols  ="";
		String temp="";
		for(int i=0;i<stocks.size()/2;i++)
		{
			temp = stocks.get(i).getStockSymbol();
			halfSymbols = halfSymbols+temp.replace(temp.charAt(temp.length()-1),'.').concat("NS")
					+"%2C";
			
		}
		System.out.println(halfSymbols);
		String URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=IN&symbols="+halfSymbols;
		
//		String response = response_string;
		String response = callApi(URL);
		marketCapValues = formatJson(response);
		return marketCapValues;
	}
	
	private ArrayList<String> getHalf_2_MarketValuesFromApi(ArrayList<Stock> stocks) {
	ArrayList<String> marketCapValues = new ArrayList<>();
		
		String halfSymbols  ="";
		String temp="";
		for(int i=stocks.size()/2;i<stocks.size();i++)
		{
			temp = stocks.get(i).getStockSymbol();
			halfSymbols = halfSymbols+temp.replace(temp.charAt(temp.length()-1),'.').concat("NS")
					+"%2C";
			
		}
		System.out.println(halfSymbols);
		String URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=IN&symbols="+halfSymbols;
		
//		String response = response_string;
		String response = callApi(URL);
		marketCapValues = formatJson(response);
		return marketCapValues;
	}

	public String callApi(String URL)
	{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL))
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
//		System.out.println(response.body());
		return response.body();
	}
	
	public ArrayList<String> formatJson(String response)
	{
		ArrayList<String> marketCapValues = new ArrayList<>();
		JSONObject jsonObject=null;
		try {
			jsonObject = (JSONObject)new JSONParser().parse(response);
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   JSONObject response_obj = (JSONObject) jsonObject.get("quoteResponse");
	   JSONArray array = (JSONArray) response_obj.get("result");
	   JSONObject[] values = new JSONObject[array.size()];
	   System.out.println(array.size());
	   for(int i=0;i<array.size();i++)
	   {
		   values[i] =(JSONObject) array.get(i);
		   marketCapValues.add((String) String.valueOf(values[i].get("marketCap")));
	   }
	   
	   return marketCapValues;
	}
}
