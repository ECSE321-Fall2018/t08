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
public class searchResultContent {

    //An array of sample search result items.
    public static final List<SearchResultItem> ITEMS = new ArrayList<SearchResultItem>();

    //A map of sample search result items, by ID.
    public static final Map<String, SearchResultItem> ITEM_MAP = new HashMap<String, SearchResultItem>();

    public static void addItem(SearchResultItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.tripid), item);
    }

    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    //A search result item representing a piece of content.
    public static class SearchResultItem {
        public final String tripid; //ID MUST BE STRING!!!

        public final int tripStatus; // 0 for ongoing, 1 for planned, 2 for completed
        public final String costPerStop; // Contain all ordered costs per stop, delimited by ';'
        public final long startdate; // Implemented as Unix Time Stamp
        public final long enddate;
        public final String startLocation;
        public final String stops; // Contain all stops, delimited by ';'
        public final String vehicle_type;
        public final String vehicle_colour;
        public final String vehicle_model;

        public final String content; //WHAT WILL BE DISPLAYED ON THE MENU, CAN LATER ADD OTHER STRINGS
        public final String details;

        public SearchResultItem(int tripid, int tripStatus, String costPerStop, long startdate,
                                long enddate, String startLocation, String stops,
                                String vehicle_type, String vehicle_colour, String vehicle_model) {
            this.tripid = String.valueOf(tripid);
            this.tripStatus = tripStatus;
            this.costPerStop = costPerStop;
            this.startdate = startdate;
            this.enddate = enddate;
            this.startLocation = startLocation;
            this.stops = stops;
            this.vehicle_type = vehicle_type;
            this.vehicle_colour = vehicle_colour;
            this.vehicle_model = vehicle_model;
            this.content = toString(startLocation, stops); //Will be displayed on the menu
            // this.details will be displayed in the content
            this.details = createDetails(tripid, tripStatus, costPerStop, startdate,
                    enddate, startLocation, stops, vehicle_type, vehicle_colour, vehicle_model);
        }

        public String createDetails(int tripid, int tripStatus, String costPerStop, long startdate,
                                    long enddate, String startLocation, String stops, String vehicle_type,
                                    String vehicle_colour, String vehicle_model) {
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
            text = text + "\n Vehicle Type: " + vehicle_type;
            text = text + "\n Vehicle Model: " + vehicle_model;
            text = text + "\n Vehicle Color: " + vehicle_colour;

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
