package com.alex.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public interface CsvToDatabase {

	public void csvToDb(String path,String marketCap) throws FileNotFoundException;
	public void insertIntodb(ArrayList<String> stockNamesandSymbols,String marketCap);
	
	public void updateMarketCap();

}
