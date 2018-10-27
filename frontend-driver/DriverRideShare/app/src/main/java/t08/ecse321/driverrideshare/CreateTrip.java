package t08.ecse321.driverrideshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CreateTrip extends AppCompatActivity {
    private String error = null;
    private String eusername;
    private String epassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

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

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

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

    //Ensures no errors in entering costs
    public List<Double> checkCost(String cost1, String cost2, String cost3) {
        List<Double> doubleList = new ArrayList<Double>();

        if(cost1 == null || cost1.equals("")) {
            error = "Please enter a cost in cost 1.";
            return new ArrayList<Double>();
        } else {
            try {
                doubleList.add(Double.parseDouble(cost1));
            } catch(Exception e) {
                error = "Only decimal numbers can be entered in costs (no $)";
                return new ArrayList<Double>();
            }
        }

        if(cost2 != null && !cost2.equals("")) {
            try {
                doubleList.add(Double.parseDouble(cost2));
            } catch(Exception e) {
                error = "Only decimal numbers can be entered in costs (no $)";
                return new ArrayList<Double>();
            }
        }

        if(cost3 != null && !cost3.equals("")) {
            try {
                doubleList.add(Double.parseDouble(cost3));
            } catch(Exception e) {
                error = "Only decimal numbers can be entered in costs (no $)";
                return new ArrayList<Double>();
            }
        }
        return doubleList;
    }

    //Ensures no errors in entering stops
    public List<String> checkStop(String stop1, String stop2, String stop3) {
        List<String> stopList = new ArrayList<String>();

        if(stop1 == null || stop1.equals("")) {
            error = "Please enter a stop in stop 1.";
            return new ArrayList<String>();
        } else {
            stopList.add(stop1);
        }

        if(stop2 != null && !stop2.equals("")) {
            stopList.add(stop2);
        }

        if(stop3 != null && !stop3.equals("")) {
            stopList.add(stop3);
        }
        return stopList;
    }

    //Ensures each stop has a cost
    public boolean checkCostStopLists(List<String> stopList, List<Double> costList) {
        if(stopList.size() == costList.size()) {
            return true;
        }
        error = "Please enter a cost for each stop";
        return false;
    }

    //Returns unix time stamp in milliseconds
    public long getUnixStamp(String date, String time) {
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        String convert = date + " " + time;
        try {
            Date dateObj = datetimeFormat.parse(convert);
            return dateObj.getTime();
        } catch (ParseException e) {
            error = "Incorrect date/time entered.";
            return -1;
        }
    }

    public boolean checkTimeStamp(long unixStart, long unixEnd) {
        if(unixEnd < unixStart) {
            error = "End date/time must be after start date/time";
            return false;
        }
        return true;
    }

    public void resetView() {
        ((EditText)findViewById(R.id.cost1)).setText("");
        ((EditText)findViewById(R.id.cost2)).setText("");
        ((EditText)findViewById(R.id.cost3)).setText("");
        ((EditText)findViewById(R.id.stop1)).setText("");
        ((EditText)findViewById(R.id.stop2)).setText("");
        ((EditText)findViewById(R.id.stop3)).setText("");
        ((EditText)findViewById(R.id.startlocation)).setText("");
    }

    public void createTripButton(View view) {

        error = "";
        refreshErrorMessage();

        //Gets all view variables
        final EditText cost1_text = (EditText) findViewById(R.id.cost1);
        final EditText cost2_text = (EditText) findViewById(R.id.cost2);
        final EditText cost3_text = (EditText) findViewById(R.id.cost3);
        final EditText stop1_text = (EditText) findViewById(R.id.stop1);
        final EditText stop2_text = (EditText) findViewById(R.id.stop2);
        final EditText stop3_text = (EditText) findViewById(R.id.stop3);
        final EditText startlocation_text = (EditText) findViewById(R.id.startlocation);
        final TextView startdate_text = (TextView) findViewById(R.id.startdatetv);
        final TextView enddate_text = (TextView) findViewById(R.id.enddatetv);
        final TextView starttime_text = (TextView) findViewById(R.id.starttimetv);
        final TextView endtime_text = (TextView) findViewById(R.id.endtimetv);

        //Converts all variables to string
        final String cost1 = cost1_text.getText().toString();
        final String cost2 = cost2_text.getText().toString();
        final String cost3 = cost3_text.getText().toString();
        final String stop1 = stop1_text.getText().toString();
        final String stop2 = stop2_text.getText().toString();
        final String stop3 = stop3_text.getText().toString();
        final String startlocation = startlocation_text.getText().toString();
        final String startdate = startdate_text.getText().toString();
        final String enddate = enddate_text.getText().toString();
        final String starttime = starttime_text.getText().toString();
        final String endtime = endtime_text.getText().toString();

        if(startlocation == null || startlocation.equals("")) {
            error = "Please enter a start location.";
            refreshErrorMessage();
            return;
        }

        List<Double> costList = checkCost(cost1, cost2, cost3);
        if (costList.isEmpty()) {
            refreshErrorMessage();
            return;
        }

        List<String> stopList = checkStop(stop1, stop2, stop3);
        if (costList.isEmpty()) {
            refreshErrorMessage();
            return;
        }

        if (checkCostStopLists(stopList, costList) == false) {
            refreshErrorMessage();
            return;
        }

        //Gets unix time stamp and ensures validity
        long unixStartMilli = getUnixStamp(startdate, starttime);
        if(unixStartMilli == -1) {
            return;
        }
        long unixEndMilli = getUnixStamp(enddate, endtime);
        if(unixStartMilli == -1) {
            refreshErrorMessage();
            return;
        }

        //Checks end time after start time
        if (checkTimeStamp(unixStartMilli, unixEndMilli) == false) {
            refreshErrorMessage();
            return;
        }

        //Parses double cost list as new string list
        List<String> costStringList = new ArrayList<String>();
        for (Double cost : costList) {
            costStringList.add(String.valueOf(cost));
        }

        //Converts costs and stops into concatenated form that can be handled by database
        String costStr = ConcatToken.concatenator(costStringList, ";");
        String stopStr = ConcatToken.concatenator(stopList, ";");

        //Gets the unix start and end time in seconds and parses to string
        String unixStart = String.format("%.0f", Double.valueOf(unixStartMilli/1000));
        String unixEnd = String.format("%.0f", Double.valueOf(unixEndMilli/1000));


        //Finds the vehicle id for the driver
        findDriverPost(costStr, unixStart, unixEnd, startlocation, stopStr);
    }


    //First gets vehicle id according to driver, then posts trip
    public void findDriverPost(String costStr, String unixStart, String unixEnd, String startlocation, String stopStr) {
        //Creates HTTP params to authorize user according to rest model
        RequestParams params = new RequestParams();
        params.add("driverusername", eusername);
        params.add("driverpassword", epassword);


        //Sends HTTP post method, if successful (response != -1, continues to post trip), else, displays error
        HttpUtils.post("api/vehicle/finddriver", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int result = response.getInt("data");
                    if(result == -1) {
                        error = "Please create a vehicle before creating a trip.";
                    } else {
                        createTripPost(costStr, unixStart, unixEnd, startlocation, stopStr, result);
                    }
                } catch(Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                error = "Failure: trip data cannot be presented";
                Log.e("MyApp", "Caught error", throwable); //This helps us to log our errors
                try {
                    int result = json.getInt("data");
                    if(result == -1) {
                        error = "Please create a vehicle before creating a trip.";
                    } else {
                        error = "Internal Server Error.";
                    }
                } catch (Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
                resetView();
            }
        });
    }

    //Posts the trip to the server, if successful, returns to main view, if failure, refreshes and displays error
    public void createTripPost(String costStr, String unixStart, String unixEnd, String startlocation, String stopStr, int vehicleid) {
        //Creates HTTP params to authorize user according to rest model
        RequestParams params = new RequestParams();
        params.add("status", "1");
        params.add("cost", costStr);
        params.add("startDate", unixStart);
        params.add("endDate", unixEnd);
        params.add("startLocation", startlocation);
        params.add("stops", stopStr);
        params.add("vehicleid", String.valueOf(vehicleid));
        params.add("driveruser", eusername);
        params.add("driverpass", epassword);

        //Sends HTTP post method, if successful (response != -1, continues to post trip), else, displays error
        HttpUtils.post("api/trip/create", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error = "";
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                error = "Failure: Unable to create trip.";
                Log.e("MyApp", "Caught error", throwable); //This helps us to log our errors
                refreshErrorMessage();
                resetView();
            }
        });
    }

    public void cancelButton(View view) {
        error = "";
        refreshErrorMessage();
        finish();
    }
}
