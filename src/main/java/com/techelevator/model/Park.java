package com.techelevator.model;

import java.time.LocalDate;

public class Park {
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstablished() {
		return established;
	}
	public void setEstablished(LocalDate established) {
		this.established = established;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getAnnualVisitorCount() {
		return annualVisitorCount;
	}
	public void setAnnualVisitorCount(int annualVisitorCount) {
		this.annualVisitorCount = annualVisitorCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private long parkId;
	private String name;
	private String location;
	private LocalDate established;
	private int area;
	private int annualVisitorCount;
	private String description;

}
