package com.techelevator.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;

public class JDBCParkDAO implements ParkDAO {
	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource)
	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT * FROM park ORDER BY name");
		List<Park> parks = new ArrayList<>();
		while (rows.next()) {
			Park park = mapFromRow(rows);
			parks.add(park);
		}
		
		return parks;
	}

	private Park mapFromRow(SqlRowSet rows) {
		Park park = new Park();
		park.setAnnualVisitorCount(rows.getInt("visitors"));
		park.setArea(rows.getInt("area"));
		park.setDescription(rows.getString("description"));
		Date est = rows.getDate("establish_date");
		park.setEstablished(est != null ? rows.getDate("establish_date").toLocalDate() : null);
		park.setParkId(rows.getInt("park_id"));
		park.setLocation(rows.getString("location"));
		park.setName(rows.getString("name"));

		return park;
	}

	@Override
	public List<Park> showParkInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
