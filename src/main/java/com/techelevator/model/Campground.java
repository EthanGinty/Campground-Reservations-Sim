package com.techelevator.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Campground {
	Map<String, String> monthMap = new HashMap<String, String>();
	{
	{
	monthMap.put("1", "January");
	monthMap.put("2", "February");
	monthMap.put("3", "March");
	monthMap.put("4", "April");
	monthMap.put("5", "May");
	monthMap.put("6", "June");
	monthMap.put("7", "July");
	monthMap.put("8", "August");
	monthMap.put("9", "September");
	monthMap.put("10", "October");
	monthMap.put("11", "November");
	monthMap.put("12", "December");
	}
	}
	
	public long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public long getParkId() {
		return parkId;
	}
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOpenFrom() {
		return openFrom;
	}
	public void setOpenFrom(int openFrom) {
		this.openFrom = openFrom;
	}
	public int getOpenTo() {
		return openTo;
	}
	public void setOpenTo(int openTo) {
		this.openTo = openTo;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal i) {
		this.dailyFee = i;
	}
	public long campgroundId;
	public long parkId;
	public String name;
	public int openFrom;
	public int openTo;
	public BigDecimal dailyFee;
	
}
