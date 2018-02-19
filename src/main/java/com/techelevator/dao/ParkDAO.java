package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Park;

public interface ParkDAO {
	List<Park> getAllParks();
	public List<Park> showParkInfo();
}
