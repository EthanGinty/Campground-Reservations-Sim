package com.techelevator.dao;

import java.time.LocalDate;
import java.util.List;


import com.techelevator.model.Site;

public interface SiteDAO {
	public List<Site> topFiveAvaliableReservations(LocalDate fromDate, LocalDate toDate, Long campgroundId); 

}
