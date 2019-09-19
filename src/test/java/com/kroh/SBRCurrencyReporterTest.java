package com.kroh;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Unit test.
 */

@RunWith(MockitoJUnitRunner.class)
public class SBRCurrencyReporterTest {
	 private static XMLParser mockXMLParser;
		
	    private final static String xml = new String(
				  "<ValCurs Date=\"14.09.2019\" "
				+ "name=\"Foreign Currency Market\">"
				+ "<Valute ID=\"R01010\">" 
				+ "<NumCode>036</NumCode>" 
				+ "<CharCode>AUD</CharCode>"
				+ "<Nominal>1</Nominal>" 
				+ "<Name>Австралийский доллар</Name>" 
				+ "<Value>44,3174</Value>" 
				+ "</Valute>" 
				+ "<Valute ID=\"R01020A\">" 
				+ "<NumCode>944</NumCode>" 
				+ "<CharCode>AZN</CharCode>" 
				+ "<Nominal>1</Nominal>" 
				+ "<Name>Азербайджанский манат</Name>"
				+ "<Value>38,0024</Value>" 
				+ "</Valute>" 
				+ "</ValCurs>");
		
	    private CurrencyReport currencyReport;
	 
	    @Before
	    public  void init() throws ParserConfigurationException, SAXException, IOException {
	    	
	    	mockXMLParser = Mockito.mock(XMLParser.class);
	    	
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	     	
	    	try (InputStream is = new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8")))) {
	    		
	    		Document doc = dBuilder.parse(is);
	    		
	    		Mockito.when(mockXMLParser.parse(SBRCurrencyReporter.URL)).thenReturn(doc);
	     		
	    	} 
	    	
	    	currencyReport = new SBRCurrencyReporter().createCurrencyReport(mockXMLParser);
	        
	    }
	    //check date parsing is valid
	    @Test
	    public void testDate() throws IOException {
	    	
	       	String expectedDate = "Sat Sep 14 00:00:00 YEKT 2019"; 
	    	
	    	String actualDate = currencyReport.getDate().toString(); 
	    	
	    	assertEquals("not valid report date", expectedDate, actualDate);
	    }
	    
	    //check valute list parsing is valid     
	    @Test
	    public void testListSize() throws IOException {
	    	
	     	List<Currency> currencyList = currencyReport.getCurrencyList();
	     	
	     	int expectedSize = 2; 
	     	
	     	int actualSize = currencyList.size(); 
	     	
	     	assertEquals("not valid list size", expectedSize, actualSize);
	    }
	    
	    //check valute string representation is valid
	    @Test
	    public void testStringRepresentation() throws IOException {
	 
	     	String expected = "1\t44.32\tАвстралийский доллар"; 
	    	
	    	String actual = currencyReport.getCurrencyList().get(0).toString();
	    	
	    	assertEquals("not valid report date", expected, actual);
	    	
	    }
	    
	    //check valute value parsing is valid
	    @Test
	    public void testValue() throws IOException {
	    	
	    	double expectedValue = 44.3174 +  38.0024;
	    	
	    	double actualValue = currencyReport.getCurrencyList().get(0).getCurrValue() 
	    			+ currencyReport.getCurrencyList().get(1).getCurrValue();
	    	
	    	assertEquals(expectedValue, actualValue, 0);
	    	
	    }  
}
