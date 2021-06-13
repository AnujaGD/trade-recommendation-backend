package com.alex.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alex.bean.Stock;
import com.alex.service.StockService;
import com.alex.utils.CsvToDatabase;
import com.fasterxml.jackson.databind.util.JSONPObject;

@CrossOrigin
@RestController

public class stocksResources {

	@Autowired
	private CsvToDatabase csv2db;
	@GetMapping(path = "/updateNiftyCompanies")
	private void updateNiftyCompanies() {
		try {
			
			csv2db.csvToDb("niftylists\\large.csv","Large Cap");
			csv2db.csvToDb("niftylists\\small.csv","Small Cap");
			csv2db.csvToDb("niftylists\\mid.csv","Mid Cap");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	@GetMapping(path = "/updateMarketCap")
//	private void updateDbMarketCap() {
//		csv2db.updateMarketCap();
//	}

	
	private  int FROM_API_FLAG = 1;

	@Autowired
	private StockService stockService;

	@GetMapping(path = "/getQuote/{stockSymbol}")
	private Stock[] getQuote(@PathVariable String[] stockSymbol) {
		if (FROM_API_FLAG == 1) {
			return stockService.getQuoteFromAPI(stockSymbol);

		} else {
			return stockService.getQuote(stockSymbol);
		}
	}

	@GetMapping(path = "/getQuoteByMarketCap/{marketCap}")
	private Stock[] getQuotes(@PathVariable String marketCap) {
		if(FROM_API_FLAG==1)
		{
			return stockService.getStocksOfMarketCapFromAPI(marketCap);
		}else
		{
			return stockService.getStocksOfMarketCap(marketCap);

			
		}
	}

	@GetMapping(path = "/getRecommendations/{marketCap}")
	private Stock[] getRecommendations(@PathVariable String marketCap) {
		if(FROM_API_FLAG==1)
		{
			return stockService.getRecommendationsFromAPI(marketCap);
		}
		else {
			return stockService.getRecommendations(marketCap);

		}

	}

	@GetMapping(path = "/d")
	private String trial() {

		 String JSON = "{\r\n"
		 + " \"quoteResponse\": {\r\n"
		 + " \"result\": [\r\n"
		 + " {\r\n"
		 + " \"language\": \"en-IN\",\r\n"
		 + " \"region\": \"IN\",\r\n"
		 + " \"quoteType\": \"EQUITY\",\r\n"
		 + " \"quoteSourceName\": \"Delayed Quote\",\r\n"
		 + " \"triggerable\": false,\r\n"
		 + " \"quoteSummary\": {\r\n"
		 + " \"earnings\": {\r\n"
		 + " \"maxAge\": 86400,\r\n"
		 + " \"earningsChart\": {\r\n"
		 + " \"quarterly\": [\r\n"
		 + " {\r\n"
		 + " \"date\": \"2Q2017\",\r\n"
		 + " \"actual\": 3.4,\r\n"
		 + " \"estimate\": 4.02\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"2Q2018\",\r\n"
		 + " \"actual\": 3.34,\r\n"
		 + " \"estimate\": 4.39\r\n"
		 + " }\r\n"
		 + " ],\r\n"
		 + " \"currentQuarterEstimateDate\": \"3Q\",\r\n"
		 + " \"currentQuarterEstimateYear\": 2018,\r\n"
		 + " \"earningsDate\": []\r\n"
		 + " },\r\n"
		 + " \"financialsChart\": {\r\n"
		 + " \"yearly\": [\r\n"
		 + " {\r\n"
		 + " \"date\": 2017,\r\n"
		 + " \"revenue\": 84393500000,\r\n"
		 + " \"earnings\": 39115200000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": 2018,\r\n"
		 + " \"revenue\": 113229600000,\r\n"
		 + " \"earnings\": 36736200000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": 2019,\r\n"
		 + " \"revenue\": 109254400000,\r\n"
		 + " \"earnings\": 39902200000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": 2020,\r\n"
		 + " \"revenue\": 114387700000,\r\n"
		 + " \"earnings\": 37631300000\r\n"
		 + " }\r\n"
		 + " ],\r\n"
		 + " \"quarterly\": [\r\n"
		 + " {\r\n"
		 + " \"date\": \"1Q2020\",\r\n"
		 + " \"revenue\": 29211900000,\r\n"
		 + " \"earnings\": 3343900000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"2Q2020\",\r\n"
		 + " \"revenue\": 22926900000,\r\n"
		 + " \"earnings\": 7580200000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"3Q2020\",\r\n"
		 + " \"revenue\": 29025200000,\r\n"
		 + " \"earnings\": 13870000000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"4Q2020\",\r\n"
		 + " \"revenue\": 37464900000,\r\n"
		 + " \"earnings\": 15614700000\r\n"
		 + " }\r\n"
		 + " ]\r\n"
		 + " },\r\n"
		 + " \"financialCurrency\": \"INR\"\r\n"
		 + " }\r\n"
		 + " },\r\n"
		 + " \"currency\": \"INR\",\r\n"
		 + " \"sharesOutstanding\": 2031750016,\r\n"
		 + " \"bookValue\": 136.795,\r\n"
		 + " \"fiftyDayAverage\": 728.4042,\r\n"
		 + " \"fiftyDayAverageChange\": 4.695801,\r\n"
		 + " \"fiftyDayAverageChangePercent\": 0.006446697,\r\n"
		 + " \"twoHundredDayAverage\": 522.0993,\r\n"
		 + " \"twoHundredDayAverageChange\": 211.00067,\r\n"
		 + " \"twoHundredDayAverageChangePercent\": 0.40413895,\r\n"
		 + " \"marketCap\": 1489578491904,\r\n"
		 + " \"forwardPE\": 33.277348,\r\n"
		 + " \"priceToBook\": 5.3591137,\r\n"
		 + " \"sourceInterval\": 15,\r\n"
		 + " \"exchangeDataDelayedBy\": 15,\r\n"
		 + " \"exchangeTimezoneName\": \"Asia/Kolkata\",\r\n"
		 + " \"exchangeTimezoneShortName\": \"IST\",\r\n"
		 + " \"gmtOffSetMilliseconds\": 19800000,\r\n"
		 + " \"esgPopulated\": false,\r\n"
		 + " \"tradeable\": false,\r\n"
		 + " \"firstTradeDateMilliseconds\": 1196135100000,\r\n"
		 + " \"priceHint\": 2,\r\n"
		 + " \"totalCash\": 1.01976596E11,\r\n"
		 + " \"floatShares\": 720672214,\r\n"
		 + " \"ebitda\": 71986225152,\r\n"
		 + " \"targetPriceHigh\": 480.0,\r\n"
		 + " \"targetPriceLow\": 350.0,\r\n"
		 + " \"targetPriceMean\": 424.75,\r\n"
		 + " \"targetPriceMedian\": 448.0,\r\n"
		 + " \"heldPercentInsiders\": 68.097,\r\n"
		 + " \"heldPercentInstitutions\": 21.849,\r\n"
		 + " \"regularMarketChange\": -11.550049,\r\n"
		 + " \"regularMarketChangePercent\": -1.5510708,\r\n"
		 + " \"regularMarketTime\": 1618308000,\r\n"
		 + " \"regularMarketPrice\": 733.1,\r\n"
		 + " \"regularMarketDayHigh\": 755.75,\r\n"
		 + " \"regularMarketDayRange\": \"701.35 - 755.75\",\r\n"
		 + " \"regularMarketDayLow\": 701.35,\r\n"
		 + " \"regularMarketVolume\": 52808866,\r\n"
		 + " \"regularMarketPreviousClose\": 744.65,\r\n"
		 + " \"bid\": 0.0,\r\n"
		 + " \"ask\": 0.0,\r\n"
		 + " \"bidSize\": 0,\r\n"
		 + " \"askSize\": 0,\r\n"
		 + " \"exchange\": \"NSI\",\r\n"
		 + " \"market\": \"in_market\",\r\n"
		 + " \"messageBoardId\": \"finmb_27674009\",\r\n"
		 + " \"fullExchangeName\": \"NSE\",\r\n"
		 + " \"shortName\": \"ADANI PORT SPECIAL\",\r\n"
		 + " \"longName\": \"Adani Ports and Special Economic Zone Limited\",\r\n"
		 + " \"regularMarketOpen\": 750.0,\r\n"
		 + " \"averageDailyVolume3Month\": 16673267,\r\n"
		 + " \"averageDailyVolume10Day\": 44950247,\r\n"
		 + " \"beta\": 0.990863,\r\n"
		 + " \"fiftyTwoWeekLowChange\": 474.59998,\r\n"
		 + " \"fiftyTwoWeekLowChangePercent\": 1.8359767,\r\n"
		 + " \"fiftyTwoWeekRange\": \"258.5 - 885.0\",\r\n"
		 + " \"fiftyTwoWeekHighChange\": -151.90002,\r\n"
		 + " \"fiftyTwoWeekHighChangePercent\": -0.17163844,\r\n"
		 + " \"fiftyTwoWeekLow\": 258.5,\r\n"
		 + " \"fiftyTwoWeekHigh\": 885.0,\r\n"
		 + " \"exDividendDate\": 1584316800,\r\n"
		 + " \"trailingAnnualDividendRate\": 0.0,\r\n"
		 + " \"trailingPE\": 36.85957,\r\n"
		 + " \"dividendsPerShare\": 3.2,\r\n"
		 + " \"trailingAnnualDividendYield\": 0.0,\r\n"
		 + " \"revenue\": 1.18628901E11,\r\n"
		 + " \"priceToSales\": 12.556623,\r\n"
		 + " \"marketState\": \"CLOSED\",\r\n"
		 + " \"epsTrailingTwelveMonths\": 19.889,\r\n"
		 + " \"epsForward\": 22.03,\r\n"
		 + " \"symbol\": \"ADANIPORTS.NS\"\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"language\": \"en-IN\",\r\n"
		 + " \"region\": \"IN\",\r\n"
		 + " \"quoteType\": \"EQUITY\",\r\n"
		 + " \"quoteSourceName\": \"Delayed Quote\",\r\n"
		 + " \"triggerable\": false,\r\n"
		 + " \"quoteSummary\": {\r\n"
		 + " \"earnings\": {\r\n"
		 + " \"maxAge\": 86400,\r\n"
		 + " \"earningsChart\": {\r\n"
		 + " \"quarterly\": [\r\n"
		 + " {\r\n"
		 + " \"date\": \"4Q2017\",\r\n"
		 + " \"actual\": 17.7,\r\n"
		 + " \"estimate\": 18.08\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"1Q2018\",\r\n"
		 + " \"actual\": 18.3,\r\n"
		 + " \"estimate\": 18.68\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"2Q2018\",\r\n"
		 + " \"actual\": 17.5,\r\n"
		 + " \"estimate\": 18.39\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"3Q2018\",\r\n"
		 + " \"actual\": 18.5,\r\n"
		 + " \"estimate\": 19.27\r\n"
		 + " }\r\n"
		 + " ],\r\n"
		 + " \"currentQuarterEstimateDate\": \"4Q\",\r\n"
		 + " \"currentQuarterEstimateYear\": 2018,\r\n"
		 + " \"earningsDate\": []\r\n"
		 + " },\r\n"
		 + " \"financialsChart\": {\r\n"
		 + " \"yearly\": [\r\n"
		 + " {\r\n"
		 + " \"date\": 2017,\r\n"
		 + " \"revenue\": 441165929000,\r\n"
		 + " \"earnings\": 152255743000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": 2018,\r\n"
		 + " \"revenue\": 523911430000,\r\n"
		 + " \"earnings\": 185100242000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": 2019,\r\n"
		 + " \"revenue\": 620129233000,\r\n"
		 + " \"earnings\": 223324309000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": 2020,\r\n"
		 + " \"revenue\": 712309108000,\r\n"
		 + " \"earnings\": 272539506000\r\n"
		 + " }\r\n"
		 + " ],\r\n"
		 + " \"quarterly\": [\r\n"
		 + " {\r\n"
		 + " \"date\": \"1Q2020\",\r\n"
		 + " \"revenue\": 185421008000,\r\n"
		 + " \"earnings\": 72802106000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"2Q2020\",\r\n"
		 + " \"revenue\": 167455900000,\r\n"
		 + " \"earnings\": 69272400000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"3Q2020\",\r\n"
		 + " \"revenue\": 189851600000,\r\n"
		 + " \"earnings\": 77028600000\r\n"
		 + " },\r\n"
		 + " {\r\n"
		 + " \"date\": \"4Q2020\",\r\n"
		 + " \"revenue\": 209930000000,\r\n"
		 + " \"earnings\": 87693300000\r\n"
		 + " }\r\n"
		 + " ]\r\n"
		 + " },\r\n"
		 + " \"financialCurrency\": \"INR\"\r\n"
		 + " }\r\n"
		 + " },\r\n"
		 + " \"currency\": \"INR\",\r\n"
		 + " \"sharesOutstanding\": 5512779776,\r\n"
		 + " \"bookValue\": 364.416,\r\n"
		 + " \"fiftyDayAverage\": 1506.0111,\r\n"
		 + " \"fiftyDayAverageChange\": -105.01111,\r\n"
		 + " \"fiftyDayAverageChangePercent\": -0.06972798,\r\n"
		 + " \"twoHundredDayAverage\": 1408.4811,\r\n"
		 + " \"twoHundredDayAverageChange\": -7.481079,\r\n"
		 + " \"twoHundredDayAverageChangePercent\": -0.0053114514,\r\n"
		 + " \"marketCap\": 7723404492800,\r\n"
		 + " \"forwardPE\": 14.5392275,\r\n"
		 + " \"priceToBook\": 3.8445075,\r\n"
		 + " \"sourceInterval\": 15,\r\n"
		 + " \"exchangeDataDelayedBy\": 15,\r\n"
		 + " \"exchangeTimezoneName\": \"Asia/Kolkata\",\r\n"
		 + " \"exchangeTimezoneShortName\": \"IST\",\r\n"
		 + " \"gmtOffSetMilliseconds\": 19800000,\r\n"
		 + " \"esgPopulated\": false,\r\n"
		 + " \"tradeable\": false,\r\n"
		 + " \"firstTradeDateMilliseconds\": 820467900000,\r\n"
		 + " \"priceHint\": 2,\r\n"
		 + " \"totalCash\": 8.7755319E11,\r\n"
		 + " \"floatShares\": 4611840284,\r\n"
		 + " \"targetPriceHigh\": 2869.0,\r\n"
		 + " \"targetPriceLow\": 2004.0,\r\n"
		 + " \"targetPriceMean\": 2405.29,\r\n"
		 + " \"targetPriceMedian\": 2400.0,\r\n"
		 + " \"heldPercentInsiders\": 21.514,\r\n"
		 + " \"heldPercentInstitutions\": 36.034,\r\n"
		 + " \"regularMarketChange\": 33.94995,\r\n"
		 + " \"regularMarketChangePercent\": 2.4834461,\r\n"
		 + " \"regularMarketTime\": 1618307999,\r\n"
		 + " \"regularMarketPrice\": 1401.0,\r\n"
		 + " \"regularMarketDayHigh\": 1406.45,\r\n"
		 + " \"regularMarketDayRange\": \"1361.0 - 1406.45\",\r\n"
		 + " \"regularMarketDayLow\": 1361.0,\r\n"
		 + " \"regularMarketVolume\": 9297467,\r\n"
		 + " \"regularMarketPreviousClose\": 1367.05,\r\n"
		 + " \"bid\": 0.0,\r\n"
		 + " \"ask\": 0.0,\r\n"
		 + " \"bidSize\": 0,\r\n"
		 + " \"askSize\": 0,\r\n"
		 + " \"exchange\": \"NSI\",\r\n"
		 + " \"market\": \"in_market\",\r\n"
		 + " \"messageBoardId\": \"finmb_101677\",\r\n"
		 + " \"fullExchangeName\": \"NSE\",\r\n"
		 + " \"shortName\": \"HDFC BANK\",\r\n"
		 + " \"longName\": \"HDFC Bank Limited\",\r\n"
		 + " \"regularMarketOpen\": 1368.0,\r\n"
		 + " \"averageDailyVolume3Month\": 9957591,\r\n"
		 + " \"averageDailyVolume10Day\": 10220837,\r\n"
		 + " \"beta\": 0.87026,\r\n"
		 + " \"fiftyTwoWeekLowChange\": 574.9,\r\n"
		 + " \"fiftyTwoWeekLowChangePercent\": 0.69592065,\r\n"
		 + " \"fiftyTwoWeekRange\": \"826.1 - 1641.0\",\r\n"
		 + " \"fiftyTwoWeekHighChange\": -240.0,\r\n"
		 + " \"fiftyTwoWeekHighChangePercent\": -0.14625229,\r\n"
		 + " \"fiftyTwoWeekLow\": 826.1,\r\n"
		 + " \"fiftyTwoWeekHigh\": 1641.0,\r\n"
		 + " \"exDividendDate\": 1564617600,\r\n"
		 + " \"trailingAnnualDividendRate\": 15.0,\r\n"
		 + " \"trailingPE\": 25.16661,\r\n"
		 + " \"dividendsPerShare\": 0.0,\r\n"
		 + " \"trailingAnnualDividendYield\": 0.0109725315,\r\n"
		 + " \"revenue\": 7.5265848E11,\r\n"
		 + " \"priceToSales\": 10.261499,\r\n"
		 + " \"marketState\": \"CLOSED\",\r\n"
		 + " \"epsTrailingTwelveMonths\": 55.669,\r\n"
		 + " \"epsForward\": 96.36,\r\n"
		 + " \"components\": [\r\n"
		 + " \"NIFTY50.NS\"\r\n"
		 + " ],\r\n"
		 + " \"symbol\": \"HDFCBANK.NS\"\r\n"
		 + " }\r\n"
		 + " ],\r\n"
		 + " \"error\": null\r\n"
		 + " }\r\n"
		 + "}";
		return JSON;
		// String URL =
		// "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=IN&symbols=HDFCBANK.NS%2CADANIPORTS.NS";
		// HttpRequest request = HttpRequest.newBuilder()
		// .uri(URI.create(URL))
		// .header("x-rapidapi-key",
		// "5369cef33amshe576e689fc34c2ep164d84jsne2221da15dab")
		// .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
		// .method("GET", HttpRequest.BodyPublishers.noBody()).build();
		// HttpResponse<String> response = null;
		// try {
		// response = HttpClient.newHttpClient().send(request,
		// HttpResponse.BodyHandlers.ofString());
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// JSONObject jsonObject=null;
		// try {
		// jsonObject = (JSONObject)new JSONParser().parse(response.body());
		// } catch (org.json.simple.parser.ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// JSONObject response_obj = (JSONObject) jsonObject.get("quoteResponse");
		// JSONArray array = (JSONArray) response_obj.get("result");
		// JSONObject[] values = new JSONObject[array.size()];
		// for(int i=0;i<array.size();i++)
		// {
		// values[i] =(JSONObject) array.get(i);
		// System.out.println(values[i].get("marketCap"));
		// }
		//

	}

}
