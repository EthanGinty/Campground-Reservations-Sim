package com.techelevator.dao;


import java.time.LocalDate;
import java.util.List;


import com.techelevator.model.Reservation;

public interface ReservationDAO {
	public  List<Reservation> getAllReservedTimes(Long campgroundId, LocalDate fromDate, LocalDate toDate);
	public void createReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate, LocalDate createDate);
	public long getNextReservationId();
	
}
