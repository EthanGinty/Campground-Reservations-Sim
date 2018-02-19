package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Campground;

public interface CampgroundDAO {
	public  List<Campground> viewCampgroundInfo(long parkId);
	public List<Campground> getDailyFeeFromId(long campgroundId);

	
}
