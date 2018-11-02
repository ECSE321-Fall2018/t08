package t08.ecse321.driverrideshare;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Helper class for providing sample content for user interfaces
 * created by Android template wizards.
 */
public class myTripContent {

    //An array of sample trip items.
    public static final List<TripItem> ITEMS = new ArrayList<TripItem>();

    //A map of sample trip items, by ID.
    public static final Map<String, TripItem> ITEM_MAP = new HashMap<String, TripItem>();


    public static void addItem(TripItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.tripid), item);
    }

    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }


    //A trip item representing a piece of content.
    public static class TripItem {
        public final String tripid; //ID MUST BE STRING!!!

        public final int tripStatus; // 0 for ongoing, 1 for planned, 2 for completed
        public final String costPerStop; // Contain ordered costs per stop (delimited by ';')
        public final long startdate; // Implement as Unix Time Stamp
        public final long enddate;
        public final String startLocation;
        public final String stops; // Contain all stops, delimited by ';'

        public final String content; //WHAT WILL BE DISPLAYED ON THE MENU (can later add other strings)
        public final String details;
        public final String passengerid;

        public TripItem(int tripid, int tripStatus, String costPerStop, long startdate,
                        long enddate, String startLocation, String stops, String passengerid) {
            this.tripid = String.valueOf(tripid);
            this.tripStatus = tripStatus;
            this.costPerStop = costPerStop;
            this.startdate = startdate;
            this.enddate = enddate;
            this.startLocation = startLocation;
            this.stops = stops;
            this.passengerid = passengerid;
            this.content = toString(startLocation, stops); //will be displayed on the menu
            //this.details will be displayed in the content
            this.details = createDetails(tripid, tripStatus,
                    costPerStop, startdate, enddate, startLocation, stops, passengerid);
        }

        /*
        Create the trip details
        @return text A String of said details
         */
        public String createDetails(int tripid, int tripStatus, String costPerStop, long startdate,
                                    long enddate, String startLocation, String stops, String passengerid) {
            String tripStatusStr;
            switch(tripStatus) {
                case 0:
                    tripStatusStr = "Ongoing";
                    break;
                case 1:
                    tripStatusStr = "Planned";
                    break;
                case 2:
                    tripStatusStr = "Completed";
                    break;
                default:
                    tripStatusStr = "Unknown";
            }
            java.util.Date startDate = new java.util.Date(startdate*1000);
            java.util.Date endDate = new java.util.Date(enddate*1000);
            List<String> stopList = ConcatToken.tokenizer(stops, ";");
            List<String> costList = ConcatToken.tokenizer(costPerStop, ";");
            List<String> passengeridlist = ConcatToken.tokenizer(passengerid, ";");

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            String text = "";
            text = text + "Trip ID: " + String.valueOf(tripid) +"\n";
            text = text + "Trip Status: " + tripStatusStr +"\n\n";
            text = text + "Start Location: " + startLocation +"\n";
            text = text + "Start Date: " + dateFormatter.format(startDate) +"\n\n";
            for(int i = 0; i < stopList.size(); i++) {
                text = text + "Stop: " + stopList.get(i);
                if(i <= costList.size()-1) {
                    text = text + " - Cost: " + costList.get(i);
                }
                text+="\n";
            }
            text = text + "\nEnd Date: " + dateFormatter.format(endDate) +"\n\n";
            text = text + "Passenger IDs: ";

            int iter = 0;
            for(String str: passengeridlist) {
                if(iter != 0) {
                    text += ", ";
                }
                text += str;
                iter++;
            }
            return text;
        }

        /*
        Display start and end location
        @return result A string of said locations
         */
        public String toString(String startLocation, String stops) {
            List<String> stopList = ConcatToken.tokenizer(stops, ";");

            String result = startLocation + " to ";

            int iter = 0;
            for(String str: stopList) {
                if(iter != 0) {
                    result += ", ";
                }
                result += str;
                iter++;
            }
            return result;
        }

        @Override
        public String toString() {
            return tripid;
        }
    }
}
