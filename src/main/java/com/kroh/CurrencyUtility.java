package com.kroh;

import java.io.IOException;

/**
 * Currency Utility
 * 
 */
public class CurrencyUtility {
	
	public static void main( String[] args ) {
		
		try {
			
			System.out.print("Begin\n\n");
			
			System.out.println("Getting daily currency report from "
								+ SBRCurrencyReporter.URL + "\n");
			
			CurrencyReport report = new SBRCurrencyReporter().createCurrencyReport(new XMLParser());
			
			System.out.println("Recieved:\n\n" + report.toString());
			
			System.out.println("Creating a file report \n");
			
			System.out.println("File " + report.createFileReport() + " was created\n" );
			
			
		} catch (IOException e) {
			
			System.out.println(e.getMessage() + "\n");
			
		} finally {
			
			System.out.print("End");
			
		}
		
	} 

}
