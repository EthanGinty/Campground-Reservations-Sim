package com.techelevator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.techelevator.dao.JDBCParkDAO;
import com.techelevator.dao.ParkDAO;
import com.techelevator.model.Park;

public class TestParkDAO extends DAOIntegrationTest {

	private ParkDAO parkDao;

	@Before
	public void setup() {
		parkDao = new JDBCParkDAO(getDataSource());
	}

	@Test
	public void test_get_all_parks() {
		List<Park> parks = parkDao.getAllParks();
		assertNotNull("dao should return a list of parks", parks);
		assertTrue("There should be parks in the db", parks.size() > 0);
	}
	@Test
	public void test_show_park_info() {
		List<Park> parks = parkDao.showParkInfo();
		assertNotNull(parks);
		assertTrue(parks.size() > 0);
	}

}
