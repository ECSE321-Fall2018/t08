package t08.ecse321.passengerrideshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class SearchTrip extends AppCompatActivity {
    private String error = null;
    private String eusername;
    private String epassword;
    private final String initDateTime = "Click to Set";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        Intent intent = getIntent();
        eusername = intent.getStringExtra("EXTRA_USERNAME");
        epassword = intent.getStringExtra("EXTRA_PASSWORD");
    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    private Bundle getTimeFromLabel(String text) {
        if(text.equals(initDateTime)) {
            text = "12:00";
        }

        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        int hour = 12;
        int minute = 0;

        if (comps.length == 2) {
            hour = Integer.parseInt(comps[0]);
            minute = Integer.parseInt(comps[1]);
        }

        rtn.putInt("hour", hour);
        rtn.putInt("minute", minute);

        return rtn;
    }

    private Bundle getDateFromLabel(String text) {
        //Ensures
        if(text.equals(initDateTime)) {
            text = "01-01-2018";
        }

        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            month = Integer.parseInt(comps[0]);
            day = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month);
        rtn.putInt("year", year);

        return rtn;
    }

    public void showTimePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getTimeFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());
        args.putInt("viewid", R.layout.activity_search_trip);


        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());
        args.putInt("viewid", R.layout.activity_search_trip);

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setTime(int id, int h, int m) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", h, m));
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", m + 1, d, y));
    }

    //Ensures no errors in entering cost
    public double checkCost(String cost) {
        double verifiedCost = -1.0;
        if(cost != null && !cost.equals("")) {
            try {
                verifiedCost = Double.parseDouble(cost);
            } catch (Exception e) {
                error = "Only decimal numbers can be entered in costs (no $)";
                verifiedCost = -1.0;
            }
        }
        return verifiedCost;
    }

    //Returns unix time stamp in milliseconds
    public long getUnixStamp(String date, String time) {

        //Check to see if user entered any arguments for date or time
        if(date == null || date.equals("") || date.equals(initDateTime)) {
            return -1;
        }
        if(time == null || time.equals("") || time.equals(initDateTime)) {
            return -1;
        }

        SimpleDateFormat datetimeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        datetimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String convert = date + " " + time;
        try {
            Date dateObj = datetimeFormat.parse(convert);
            return dateObj.getTime();
        } catch (ParseException e) {
            error = "Incorrect date/time entered.";
            return -1;
        }
    }

    public void searchTripButton(View view) {
        error = "";
        refreshErrorMessage();

        //Gets all view variables
        final EditText startlocation_text = (EditText) findViewById(R.id.startlocation);
        final TextView startdate_text = (TextView) findViewById(R.id.startdatetv);
        final TextView starttime_text = (TextView) findViewById(R.id.starttimetv);
        final EditText destination_text = (EditText) findViewById(R.id.destination);
        final EditText cost_text = (EditText) findViewById(R.id.cost);
        final EditText vehicle_text = (EditText) findViewById(R.id.vehicle_type);

        //Converts all variables to string
        final String startlocation = startlocation_text.getText().toString();
        final String startdate = startdate_text.getText().toString();
        final String starttime = starttime_text.getText().toString();
        final String destination = destination_text.getText().toString();
        final String cost = cost_text.getText().toString();
        final String vehicletype = vehicle_text.getText().toString();

        String newcost;
        double doubcost = checkCost(cost);
        if(doubcost != -1) {
            newcost = String.valueOf(doubcost);
        } else {
            newcost = "";
        }

        //Gets unix time stamp and ensures validity
        long unixStartMilli = getUnixStamp(startdate, starttime);

        //Gets the unix start time in seconds and parses to string
        String unixStart;
        if(unixStartMilli != -1) {
            unixStart = String.format("%.0f", Double.valueOf(unixStartMilli / 1000));
        } else {
            unixStart = "";
        }
        refreshErrorMessage();
        searchTrip(startlocation, unixStart, newcost, destination, vehicletype);
    }

    //Searches trips and opens master detail flow view to display trips
    public List<Integer> searchTrip(String startLocation, String unixStart, String cost, String destination, String vehicletype) {
        //Creates HTTP params to authorize user according to rest model
        RequestParams params = new RequestParams();
        if(startLocation != null && !startLocation.equals("")) {
            params.add("startloc", startLocation);
        }
        if(destination != null && !destination.equals("")) {
            params.add("stop", destination);
        }
        if(unixStart != null && !unixStart.equals("")) {
            params.add("startdate", unixStart);
        }
        if(vehicletype != null && !vehicletype.equals("")) {
            params.add("vehtype", vehicletype);
        }
        if(cost != null && !cost.equals("") ) {
            params.add("maxcost", cost);
        }

        final Intent intent = new Intent(this, searchResultListActivity.class);
        ArrayList<Integer> list = new ArrayList<Integer>();

        //If HTTP successful, will start search result list activity, if failure, will display error
        HttpUtils.post("api/trip/find", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONArray array = response.optJSONArray("data");
                    if(array == null) {
                        error = "No trips found.";
                    } else {
                        for(int i = 0; i < array.length(); i++) {
                            //For each integer, adds it to list
                            list.add(array.optInt(i));
                        }
                        //Will call method to now retrieve information for each trip
                        Bundle extras = new Bundle();
                        extras.putString("EXTRA_USERNAME", eusername);
                        extras.putString("EXTRA_PASSWORD", epassword);
                        extras.putIntegerArrayList("triplist", list);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                } catch(Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                error = "Failure: ";
                Log.e("MyApp", "Caught error", throwable); //This helps us to log our errors
                try {
                    error +=  "No trips found with specified search.";
                }
                catch (Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
        return list;
    }

    //Resets fields when called
    public void resetView() {
        ((EditText)findViewById(R.id.startlocation)).setText("");
        ((TextView)findViewById(R.id.startdatetv)).setText(initDateTime);
        ((TextView)findViewById(R.id.starttimetv)).setText(initDateTime);
        ((EditText)findViewById(R.id.destination)).setText("");
        ((EditText)findViewById(R.id.cost)).setText("");
        ((EditText)findViewById(R.id.vehicle_type)).setText("");
    }

    public void resetFieldsButton(View view) {
        resetView();
    }

    public void cancelButton(View view) {
        error = "";
        refreshErrorMessage();
        finish();
    }
}
