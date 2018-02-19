package com.techelevator.dao;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Site;
import com.techelevator.model.Campground;

public class JDBCSiteDAO implements SiteDAO{
	
private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource)
	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Site> topFiveAvaliableReservations(LocalDate fromDate, LocalDate toDate, Long campgroundId) {
		String topFive = "select site.site_id, site.site_number, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities, campground.daily_fee from site "+
						"inner join reservation on reservation.site_id = site.site_id "+
						"inner join campground on campground.campground_id = site.campground_id "+
						"where ? >= from_date "+
						"and ? <= to_date "+
						"and site.campground_id = ? "+
						"limit 5";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(topFive, toDate, fromDate, campgroundId);
		List<Site> sites = new ArrayList<>();
		while(rows.next()) {
			Site site = mapFromRow(rows);
			sites.add(site);
		}
		return sites;
		
	}
	
	


private Site mapFromRow(SqlRowSet rows) {
	Site site = new Site();
	Campground campground = new Campground();
	site.setSiteId(rows.getLong("site_id"));
	site.setSite_Number(rows.getInt("site_number"));
	site.setMaxOccupancy(rows.getInt("max_occupancy"));
	site.setAccessible(rows.getBoolean("accessible"));
	site.setMaxRvLength(rows.getInt("max_rv_length"));
	site.setUtilities(rows.getBoolean("utilities"));
	campground.setDailyFee(rows.getBigDecimal("daily_fee"));
	
	return site;
}

}