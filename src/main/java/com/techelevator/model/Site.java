package com.techelevator.model;

public class Site {
	public long getSiteId() {
	    return siteId;
	}
	public void setSiteId(long siteId) {
	    this.siteId = siteId;
	}
	public int getCampgroundId() {
	    return campgroundId;
	}
	public void setCampgroundId(int campgroundId) {
	    this.campgroundId = campgroundId;
	}
	public int getSite_Number() {
	    return siteNumber;
	}
	public void setSite_Number(int site_Number) {
	    this.siteNumber = site_Number;
	}
	public int getMaxOccupancy() {
	    return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
	    this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
	    return accessible;
	}
	public void setAccessible(boolean accessible) {
	    this.accessible = accessible;
	}
	public int getMaxRvLength() {
	    return maxRvLength;
	}
	public void setMaxRvLength(int maxRvLength) {
	    this.maxRvLength = maxRvLength;
	}
	public boolean isUtilities() {
	    return utilities;
	}
	public void setUtilities(boolean utilities) {
	    this.utilities = utilities;
	}
	private long siteId;
	private int campgroundId;
	private int siteNumber;;
	private int maxOccupancy;
	private boolean accessible;
	private int maxRvLength;
	private boolean utilities;

}
