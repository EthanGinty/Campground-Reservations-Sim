package com.techelevator.dao;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Reservation;

public class JDBCReservationDAO implements ReservationDAO {
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource)
	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Reservation> getAllReservedTimes(Long campgroundId, LocalDate fromDate, LocalDate toDate) {
		String reservationSelection = "select reservation.from_date, reservation.to_date, reservation.site_id from site "+
										"inner join reservation on reservation.site_id = site.site_id "+
										"where ? >= from_date "+
										"and ? <= to_date "+
										"and site.campground_id = ?";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(reservationSelection, toDate, fromDate, campgroundId);
		List<Reservation> reservations = new ArrayList<>();
		while (rows.next()) {
			Reservation reservation = mapFromRow(rows);
			reservations.add(reservation);
		}
		
		return reservations;
	}
	
	@Override
	public void createReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate, LocalDate createDate) {
		String insertReservation = "INSERT INTO reservation(site_id, name,from_date, to_date, create_date) VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertReservation, siteId, name, fromDate, toDate, createDate);
			
	}
	@Override
	public
	long getNextReservationId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT max(reservation.reservation_id) FROM reservation");
		if(nextIdResult.next()) {
		return nextIdResult.getLong(1);
		} else {
		throw new RuntimeException("This did not work");
		}
		}
	
	
	
	private Reservation mapFromRow(SqlRowSet rows) {
		Reservation reservation = new Reservation();
		reservation.setSiteId(rows.getInt("site_id"));
		reservation.setFromDate(rows.getDate("from_date").toLocalDate());
		reservation.setToDate(rows.getDate("to_date").toLocalDate());
		return reservation;
	}

	private Reservation mapFromRowToCreate(SqlRowSet rows) {
		Reservation reservation = new Reservation();
		reservation.setSiteId(rows.getInt("site_id"));
		reservation.setName(rows.getNString("name"));
		reservation.setFromDate(rows.getDate("from_date").toLocalDate());
		reservation.setToDate(rows.getDate("to_date").toLocalDate());
		reservation.setCreateDate(rows.getDate("create_date").toLocalDate());
		return reservation;
	}



}
