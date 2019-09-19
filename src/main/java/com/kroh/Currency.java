package com.kroh;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Currency {
	
	private String currName;
	private int currNominal;
	private double currValue;
	
	public Currency(String currName, int currNominal, double currValue) {
		
		this.currName = currName;
		this.currNominal = currNominal;
		this.currValue = currValue;
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(currNominal+"\t")
			.append(round(currValue, 2) + "\t")
			.append(currName);
		
		return sb.toString();
	} 


	public String getCurrName() {
		return currName;
	}
	
	public void setCurrName(String currName) {
		this.currName = currName;
	}
	
	public int getCurrNominal() {
		return currNominal;
	}
	
	public void setCurrNominal(int currNominal) {
		this.currNominal = currNominal;
	}
	
	public double getCurrValue() {
		return currValue;
	}
	
	public void setCurrValue(float currValue) {
		this.currValue = currValue;
	}
	
	private  double round(double value, int places) {
		
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    
	    return bd.doubleValue();
	}
	
	
}
