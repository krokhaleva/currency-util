package com.kroh;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

public class CurrencyReport {
	
	private final Logger log = Logger.getLogger(CurrencyUtility.class);
	
	static final  String FILE_NAME_DEFAULT = "currency_report_";
	static final  String FILE_EXT_DEFAULT = ".txt";
	
	private final Date date;
	private final List<Currency> currencyList;
	
	public CurrencyReport(Date date, List<Currency> currencyList) throws IOException {
		
		this.date = date;
		this.currencyList = currencyList;
		
	}
	

	public Date getDate() {
		return date;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public String createFileReport() throws IOException {
		
		String reportFileName = FILE_NAME_DEFAULT + //
				new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(date) + //
				FILE_EXT_DEFAULT;
		
		return createFileReport(reportFileName);
	}
	
	public String createFileReport(String reportFileName) throws IOException {
		
		try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFileName),
				StandardCharsets.UTF_8))) {
			
			out.write(toString());
			
			return reportFileName;
			
		} catch(IOException e) {
			
			String errMessage = new String("File was not written\n" + e.getMessage());
			
			log.error(errMessage);
			
			throw new IOException(errMessage, e);
			
		}
		
	}
	
	@Override
	public String toString() {
		
		String newLine = System.getProperty("line.separator");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(date).append(newLine);
		
		for(Currency curr : currencyList) {
			
			sb.append(curr.toString()).append(newLine);
			
		}
		
		return sb.toString(); 
		
	}
	
	

}