package t08.ecse321.passengerrideshare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class myTripContent {

    /**
     * An array of sample trip items.
     */
    public static final List<TripItem> ITEMS = new ArrayList<TripItem>();

    /**
     * A map of sample trip items, by ID.
     */
    public static final Map<String, TripItem> ITEM_MAP = new HashMap<String, TripItem>();

    private static final int COUNT = 25;


    private static final int TRIP_ID = -1;
    private static final int TRIP_ID2 = -5;
    private static final int TRIP_ID3 = -10;
    private static final int TRIP_STATUS = 1;
    private static final Double MAX_COST = 5.00;
    private static final String COST_PER_STOP = "5.00;8.00";
    private static final int START_DATE = 934238908;
    private static final int END_DATE = 934238918;
    private static final String START_LOCATION = "Montreal";
    private static final String STOPS = "Ottawa;Toronto";
    private static final String TEST_STOP = "Ottawa";
    private static final String TEST_FAKE_STOP = "Vancouver";
    private static final int VEHICLE_ID = -2;
    private static final String PASSENGER_ID = "1;2;3;4";
    private static final String NONEXISTING_PASSENGER_ID = "5;6;7;8";
    private static final int DRIVER_ID = 1;
    private static final int NONEXSITING_DRIVER_ID = 5;
    private static final String DRIVER_USERNAME = "drivertest";
    private static final String DRIVER_PASSWORD = "driverpass";
    private static final String ADMIN_USERNAME = "admintest";
    private static final String ADMIN_PASSWORD = "adminpass";
    private static final String PASSENGER_USERNAME = "passengertest";
    private static final String PASSENGER_PASSWORD = "passengerpass";
    private static final String VEH_TYPE = "VEH_TYPE";
    private static final int NON_EXISTING_TRIP_ID = -3;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i, TRIP_STATUS, COST_PER_STOP, START_DATE, END_DATE, START_LOCATION, STOPS, VEHICLE_ID, PASSENGER_ID, DRIVER_ID));
        }
    }

    private static void addItem(TripItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.tripid), item);
    }

    private static TripItem createDummyItem(int tripid, int tripStatus, String costPerStop, int startdate, int enddate, String startLocation, String stops, int vehicleid, String passengerid, int driverid) {
        return new TripItem(tripid, tripStatus, costPerStop, startdate, enddate, startLocation, stops, vehicleid, passengerid, driverid);
    }


    /**
     * A trip item representing a piece of content.
     */
    public static class TripItem {
        public final String tripid; //ID MUST BE STRING!!!

        public final int tripStatus; // 0 for ongoing, 1 for planned, 2 for completed
        public final String costPerStop; // Contains all costs per stop, in order, separated by delimiter ';'
        public final int startdate; // Implemented as Unix Time Stamp
        public final int enddate;
        public final String startLocation;
        public final String stops; // Contains all stops, separated by delimiter ';'
        public final int vehicleid;
        public final String passengerid; // Contains all passenger ids, separated by delimiter ';'
        public final int driverid;
        public final String content; //WHAT WILL BE DISPLAYED ON THE MENU, CAN LATER ADD OTHER STRINGS
        public final String details;

        public TripItem(int tripid, int tripStatus, String costPerStop, int startdate, int enddate, String startLocation, String stops, int vehicleid, String passengerid, int driverid) {
            this.tripid = String.valueOf(tripid);
            this.tripStatus = tripStatus;
            this.costPerStop = costPerStop;
            this.startdate = startdate;
            this.enddate = enddate;
            this.startLocation = startLocation;
            this.stops = stops;
            this.vehicleid = vehicleid;
            this.passengerid = passengerid;
            this.driverid = driverid;
            this.content = startLocation; // THIS WILL BE DISPLAYED ON THE MENU
            this.details = stops;
        }






        @Override
        public String toString() {
            return startLocation;
        }
    }
}
