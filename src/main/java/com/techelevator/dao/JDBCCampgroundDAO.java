package com.techelevator.dao;


import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


import com.techelevator.model.Campground;

public class JDBCCampgroundDAO implements CampgroundDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource)
	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public List<Campground> viewCampgroundInfo(long parkId) {
		String campgroundInfo = "SELECT * FROM campground WHERE park_id = ?";
		SqlRowSet row = jdbcTemplate.queryForRowSet(campgroundInfo, parkId);
		List<Campground> campgrounds = new ArrayList<>();
		while(row.next()) {
			Campground theCampground = mapFromRow(row);
			campgrounds.add(theCampground);
		}
		return campgrounds;
	}
	@Override
	public List<Campground> getDailyFeeFromId(long campgroundId) {
		String selectingDailyFee = "SELECT daily_fee FROM campground WHERE campground_id = ?";
		SqlRowSet row = jdbcTemplate.queryForRowSet(selectingDailyFee, campgroundId);
		List<Campground> campgrounds = new ArrayList<>();
		while(row.next()) {
			Campground theCampground = mapFromRowForFee(row);
			campgrounds.add(theCampground);
		}
		return campgrounds;
	}

	private Campground mapFromRow(SqlRowSet rows) {
		Campground campground = new Campground();
		campground.setParkId(rows.getInt("park_id"));
		campground.setCampgroundId(rows.getInt("campground_id"));
		campground.setName(rows.getString("name"));
		campground.setOpenFrom(rows.getInt("open_from_mm"));
		campground.setOpenTo(rows.getInt("open_to_mm"));
		campground.setDailyFee(rows.getBigDecimal("daily_fee"));

		return campground;
	}
	private Campground mapFromRowForFee(SqlRowSet rows) {
		Campground campground = new Campground();
		campground.setDailyFee(rows.getBigDecimal("daily_fee"));

		return campground;
	}
	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
}
}