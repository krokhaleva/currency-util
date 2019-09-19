package com.kroh;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SBRCurrencyReporter {
	
	static final String URL = "http://www.cbr.ru/scripts/XML_daily.asp";
	
	static final String ATTR_DATE = "Date";
	static final String TAG_VALUTE = "Valute";
	static final String TAG_NAME = "Name";
	static final String TAG_NOMINAL = "Nominal";
	static final String TAG_VALUE = "Value";
	
	private final Logger log = Logger.getLogger(SBRCurrencyReporter.class);

	public CurrencyReport createCurrencyReport(XMLParser parser) throws IOException {
		
		Document doc = parser.parse(URL);
		
		return  new CurrencyReport(parseDate(doc), parseCurrencyList(doc));
	
	}
	
	public Date parseDate(Document doc) throws IOException {
		
		try {
			
			return new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
					.parse(doc.getDocumentElement().getAttribute(SBRCurrencyReporter.ATTR_DATE));
			
		} catch (ParseException e) {
			
			String errMessage = new String("XML parsing error " + e.getMessage());
			
			log.error(errMessage);
			
			throw new IOException(errMessage, e);
			
		}
		
				
	}
	
	public List<Currency> parseCurrencyList(Document doc) {
		
		NodeList nodeList = doc.getElementsByTagName(SBRCurrencyReporter.TAG_VALUTE);
		
		List<Currency> currencyList = new ArrayList<Currency>();
		
		

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node node = nodeList.item(i);

			if(node.getNodeType() == Node.ELEMENT_NODE) {
				
				Element elem = (Element) node;
				
				String name = elem.getElementsByTagName(SBRCurrencyReporter.TAG_NAME).item(0).getTextContent();
				
				int nominal = Integer.parseInt(elem.getElementsByTagName(SBRCurrencyReporter.TAG_NOMINAL).item(0)
						.getTextContent());
				
				double value = Double.parseDouble(elem.getElementsByTagName(SBRCurrencyReporter.TAG_VALUE).item(0)
						.getTextContent().replaceAll(",", "."));
				
				currencyList.add(new Currency(name, nominal, value));
				
			}
		}
		
		return currencyList;
		
	}
	
}
