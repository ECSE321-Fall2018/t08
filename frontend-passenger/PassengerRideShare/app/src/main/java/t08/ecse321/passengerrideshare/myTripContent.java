package t08.ecse321.passengerrideshare;

import java.text.SimpleDateFormat;
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


    public static void addItem(TripItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.tripid), item);
    }

    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    /**
     * A trip item representing a piece of content.
     */
    public static class TripItem {
        public final String tripid; //ID MUST BE STRING!!!

        public final int tripStatus; // 0 for ongoing, 1 for planned, 2 for completed
        public final String costPerStop; // Contains all costs per stop, in order, separated by delimiter ';'
        public final long startdate; // Implemented as Unix Time Stamp
        public final long enddate;
        public final String startLocation;
        public final String stops; // Contains all stops, separated by delimiter ';'

        public final String content; //WHAT WILL BE DISPLAYED ON THE MENU, CAN LATER ADD OTHER STRINGS
        public final String details;

        public TripItem(int tripid, int tripStatus, String costPerStop, long startdate, long enddate, String startLocation, String stops) {
            this.tripid = String.valueOf(tripid);
            this.tripStatus = tripStatus;
            this.costPerStop = costPerStop;
            this.startdate = startdate;
            this.enddate = enddate;
            this.startLocation = startLocation;
            this.stops = stops;
            this.content = toString(startLocation, stops); //This will be displayed on the menu
            this.details = createDetails(tripid, tripStatus, costPerStop, startdate, enddate, startLocation, stops); //This will be displayed in the content
        }

        public String createDetails(int tripid, int tripStatus, String costPerStop, long startdate, long enddate, String startLocation, String stops) {
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
            java.util.Date startDate=new java.util.Date((long)startdate*1000);
            java.util.Date endDate=new java.util.Date((long)enddate*1000);
            List<String> stopList = ConcatToken.tokenizer(stops, ";");
            List<String> costList = ConcatToken.tokenizer(costPerStop, ";");

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");

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
            text = text + "\nEnd Date: " + dateFormatter.format(endDate) +"\n";
            return text;
        }

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
