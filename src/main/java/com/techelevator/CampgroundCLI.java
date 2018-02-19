package com.techelevator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.dao.CampgroundDAO;
import com.techelevator.dao.JDBCCampgroundDAO;
import com.techelevator.dao.JDBCParkDAO;
import com.techelevator.dao.JDBCReservationDAO;
import com.techelevator.dao.JDBCSiteDAO;
import com.techelevator.dao.ParkDAO;
import com.techelevator.dao.ReservationDAO;
import com.techelevator.dao.SiteDAO;
import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;


public class CampgroundCLI  {

	private static final String VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String RETURN_TO_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] PARK_OPTIONS = { VIEW_CAMPGROUNDS, SEARCH_FOR_RESERVATION,
			RETURN_TO_PREVIOUS_SCREEN };

	private static final String SEARCH_FOR_AVALIABLE_RESERVATION = "Search for Avalaible Reservation";
	private static final String[] RESERVATION_OPTIONS = { SEARCH_FOR_AVALIABLE_RESERVATION, RETURN_TO_PREVIOUS_SCREEN };


	BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);

		application.run();
	}
	private Menu menu;
	private ParkDAO parkDao;
	private CampgroundDAO campgroundDao;
	private ReservationDAO reservationDao;
	private SiteDAO siteDao;

	public CampgroundCLI(DataSource datasource) {
		menu = new Menu(System.in, System.out);
		parkDao = new JDBCParkDAO(datasource);
		campgroundDao = new JDBCCampgroundDAO(datasource);
		reservationDao = new JDBCReservationDAO(datasource);
		siteDao = new JDBCSiteDAO(datasource);
	}

	private static final String QUIT = "Quit";

	public static String readLine() {
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}

	public void run() throws IOException {
		while (true) {
			System.out.println("Select a Park for Further Details");
			List<Park> parks = parkDao.getAllParks();
			List<String> selections = new ArrayList<>();
			for (Park park : parks) {
				selections.add(park.getName());
			}
			selections.add(QUIT);
			String selection = (String) menu.getChoiceFromOptions(selections.toArray());

			if (QUIT.equals(selection)) {
				break;
			} else {
				for (Park park : parks) {
					if (park.getName().equals(selection)) {
						showParkInfo(park);
						while (true) {
							String choice = (String) menu.getChoiceFromOptions(PARK_OPTIONS);

							if (choice.equals(VIEW_CAMPGROUNDS)) {
								List<Campground> campgrounds = campgroundDao.viewCampgroundInfo(park.getParkId());
								System.out.println("***Campgrounds***");
								System.out.println("                      ");
								System.out.println("ID\tName\t\t\t\t\tOpen\tClose\tDaily Fee");
								
								for (Campground theCampground : campgrounds) {
									showCampgroundInfo(theCampground);
								}
							} else if (choice.equals(SEARCH_FOR_RESERVATION)) {
								List<Campground> campgrounds = campgroundDao.viewCampgroundInfo(park.getParkId());
								String reservationChoice = (String) menu.getChoiceFromOptions(RESERVATION_OPTIONS);
								while (true) {

									if (reservationChoice.equals(SEARCH_FOR_AVALIABLE_RESERVATION)) {

										System.out.println("**Search for Campground Reservation**");
										System.out.println("                                      ");
										System.out.println("ID\tName\t\t\t\t\tOpen\tClose\tDaily Fee");

										for (Campground theCampground : campgrounds) {
											showCampgroundInfo(theCampground);
										}

										System.out.println("Which campground (enter 0 to cancel)? ");
										String campgroundId = userInput.readLine();
										Long campgroundIdLg = Long.parseLong(campgroundId);
										

										System.out.println("What is the arrival date? (yyyy-mm-dd) ");
										String fromDateInput = userInput.readLine();
										String[] fromDateInputArray = fromDateInput.split("-");
										StringBuilder fromDateBuilder = new StringBuilder();
										for(int i = 0; i < fromDateInputArray.length; i++) {
											fromDateBuilder.append(fromDateInputArray[i]);
										}
										
										String fromDateInput2 = fromDateBuilder.toString();
										Integer fromDateInt = Integer.parseInt(fromDateInput2);
										DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

										LocalDate inputFromDate = LocalDate.parse(fromDateInput, formatter);
										System.out.println("What is your departure date? (yyyy-mm-dd) ");
										String toDateInput = userInput.readLine();
										String[] toDateInputArray = toDateInput.split("-");
										StringBuilder toDateBuilder = new StringBuilder();
										for(int i = 0; i < toDateInputArray.length; i++) {
											toDateBuilder.append(toDateInputArray[i]);
										}
										
										String toDateInput2 = toDateBuilder.toString();
										Integer toDateInt = Integer.parseInt(toDateInput2);
										LocalDate inputToDate = LocalDate.parse(toDateInput, formatter);

										List<Reservation> reservedSites = reservationDao.getAllReservedTimes(campgroundIdLg, inputFromDate, inputToDate);
										List<Campground> campgroundsList = campgroundDao.getDailyFeeFromId(campgroundIdLg);
										List<Site> avaliableSites = siteDao.topFiveAvaliableReservations(inputFromDate, inputToDate, campgroundIdLg);
										
											System.out.println("Results Matching Your Search Criteria");
											if (reservedSites.size() < 285) {
												
												System.out.println("SiteID\tSite No.\tMax Occup.\tAccesible\tMax RV Length\tUtility\t\tCost");
												for (Site theSite : avaliableSites) {
													for (Campground campground : campgroundsList) {
													showSiteInfo(theSite, campground);
													
													}
												}
													System.out.print("Which site would you like to reserve? (Please enter site ID)");
													String siteChoice = userInput.readLine();
													Long siteIdLg = Long.parseLong(siteChoice);
													List<Campground> dailyFeeFromID = campgroundDao.getDailyFeeFromId(campgroundIdLg);
													Campground dailyFee = dailyFeeFromID.get(0);
													BigDecimal dailyFeeNumber = dailyFee.getDailyFee();
													Integer dailyFeeInt = dailyFeeNumber.intValue();
													
										
													
													System.out.print("Please enter the name or family to hold the reservation for");
													
													String resName = userInput.readLine();
													LocalDate createDate = LocalDate.now();										
													reservationDao.createReservation(siteIdLg, resName, inputFromDate, inputToDate, createDate);
													System.out.print("\nSuccess!  Your reservation id is: " + reservationDao.getNextReservationId());
													Integer totalDays = toDateInt - fromDateInt;
													System.out.println("\n\nTotal days booked: "+totalDays);
													Integer daysToFees = totalDays*dailyFeeInt;
													System.out.println("\nTotal cost of days booked: "+daysToFees+" dollars");
												}}																																				
													break;
													}
											} else {
												System.out.println("The date you entered is unavaliable.");
												break;
											}
										}}
						}
						}
				}
			}
		
	

	private void showParkInfo(Park park) {
		System.out.println(park.getName() + " National Park");
		System.out.println("Location: " + park.getLocation());
		System.out.println("Established: " + park.getEstablished());
		System.out.println("Area: " + park.getArea() + " sq km");
		System.out.println("Annual Visitors: " + park.getAnnualVisitorCount());
		System.out.println(park.getDescription());

	}

	private void showSiteInfo(Site site, Campground campground) {
		System.out.println("#" + site.getSiteId() + "\t" + site.getSite_Number() + "\t\t" + site.getMaxOccupancy()
				+ "\t\t" + site.isAccessible() + "\t\t" + site.getMaxRvLength() + "\t\t" + site.isUtilities()+ "\t\t"+ "$"+campground.getDailyFee()+"0");

	}

	private void showCampgroundInfo(Campground campground) {
		if (campground.name.length() < 8) {
			System.out.println("#" + campground.getCampgroundId() + "\t" + campground.getName() + "\t\t\t\t\t"
					+ campground.getOpenFrom() + "\t" + campground.getOpenTo() + "\t" + "$" + campground.getDailyFee()
					+ "0");
		} else if (campground.name.length() > 25) {
			System.out.println(
					"#" + campground.getCampgroundId() + "\t" + campground.getName() + "\t\t" + campground.getOpenFrom()
							+ "\t" + campground.getOpenTo() + "\t" + "$" + campground.getDailyFee() + "0");	
		} else if (campground.name.length() > 20) {
			System.out.println(
					"#" + campground.getCampgroundId() + "\t" + campground.getName() + "\t\t\t" + campground.getOpenFrom()
							+ "\t" + campground.getOpenTo() + "\t" + "$" + campground.getDailyFee() + "0");
		} else if (campground.name.length() > 15) {
			System.out.println("#" + campground.getCampgroundId() + "\t" + campground.getName() + "\t\t\t"
					+ campground.getOpenFrom() + "\t" + campground.getOpenTo() + "\t" + "$" + campground.getDailyFee()
					+ "0");
		} else {
			System.out.println("#" + campground.getCampgroundId() + "\t" + campground.getName() + "\t\t\t\t"
					+ campground.getOpenFrom() + "\t" + campground.getOpenTo() + "\t" + "$" + campground.getDailyFee()
					+ "0");
		}
	}

}
