package com.kroh;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLParser {
	
	final static Logger log = Logger.getLogger(XMLParser.class);
	
	public Document parse(String url) throws  IOException {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		dbFactory.setNamespaceAware(true); 
		
		try(InputStream is = new URL(url).openStream()) { 
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(is);
			
			doc.getDocumentElement().normalize();
				
			return doc;
			
		} catch (IOException e) {
			
			String errMessage = new String("Server connection error " + e.getMessage());
			
			log.error(errMessage);
			
			throw new IOException(errMessage, e);
			
		} catch (ParserConfigurationException | SAXException e) {
			
			String errMessage = new String("XML parsing error " + e.getMessage());
			
			log.error(errMessage);
			
			throw new IOException(errMessage, e);
			
		} 
		
	}
	
}
