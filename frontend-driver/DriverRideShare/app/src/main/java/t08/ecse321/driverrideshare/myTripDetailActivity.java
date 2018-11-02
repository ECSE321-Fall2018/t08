package t08.ecse321.driverrideshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * An activity representing a single myTrip detail screen.
 * Only used on narrow width devices (ie. phone).
 * On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link myTripListActivity}.
 */
public class myTripDetailActivity extends AppCompatActivity {

    private String eusername;
    private String epassword;
    private String tripid;
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrip_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        eusername = intent.getStringExtra("EXTRA_USERNAME");
        epassword = intent.getStringExtra("EXTRA_PASSWORD");
        tripid = intent.getStringExtra(myTripDetailFragment.ARG_ITEM_ID);

        error = "";
        refreshErrorMessage();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTrip(eusername, epassword, tripid);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        if(savedInstanceState == null) {
            setDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setDetails();
    }

    // Create the detail fragment,
    // add it to the activity using a fragment transaction.
    public void setDetails() {

        Bundle arguments = new Bundle();
        arguments.putString(myTripDetailFragment.ARG_ITEM_ID,
                getIntent().getStringExtra(myTripDetailFragment.ARG_ITEM_ID));
        arguments.putString("EXTRA_USERNAME", getIntent().getStringExtra("EXTRA_USERNAME"));
        arguments.putString("EXTRA_PASSWORD", getIntent().getStringExtra("EXTRA_PASSWORD"));
        myTripDetailFragment fragment = new myTripDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mytrip_detail_container, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent = new Intent(this, myTripListActivity.class);
            Bundle extras = new Bundle();
            extras.putString("EXTRA_USERNAME", eusername);
            extras.putString("EXTRA_PASSWORD", epassword);
            intent.putExtras(extras);
            navigateUpTo(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancelTrip(String username, String password, String tripid) {
        RequestParams params = new RequestParams();
        params.add("tripid", tripid);
        params.add("username", username);
        params.add("password", password);

        //If cancel trip successful,
        // return to main page (with finish()) and reload list
        HttpUtils.post("api/trip/cancel", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    error = "";
                    finish();

                } catch(Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                error = "Failure: ";
                Log.e("MyApp", "Caught error", throwable); //Error logging helper
                try {
                    error = json.getString("data");
                } catch (JSONException e1) {
                    error += e1.getMessage() + tripid;
                }
                refreshErrorMessage();
            }
        });
    }

    //Called when a radio button is clicked --> change the status of the trip
    public void onRadioButtonClicked(View view) {
        //Check if  button is clicked (checked)
        boolean checked = ((RadioButton) view).isChecked();

        //Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_plan:
                if (checked)
                    postStatus(1, eusername, epassword, tripid);
                    break;
            case R.id.rb_ongoing:
                if (checked)
                    postStatus(0, eusername, epassword, tripid);
                    break;
            case R.id.rb_completed:
                if (checked)
                    postStatus(2, eusername, epassword, tripid);
                    break;
        }
    }

    //Change the status of the trip
    public void postStatus(int status, String username, String password, String tripid) {
        RequestParams params = new RequestParams();
        params.add("tripstatus", String.valueOf(status));
        params.add("tripid", tripid);
        params.add("username", username);
        params.add("password", password);

        //If change trip status successful, temporarily update TripItem in myTripContent
        //After returning to main page, will do a true reload and get info from the server
        HttpUtils.post("api/trip/status", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    error = "";

                    //Resets Trip Item with new status
                    myTripContent.TripItem tempItem = myTripContent.ITEM_MAP.remove(tripid);
                    myTripContent.TripItem newItem = new myTripContent.TripItem(Integer.valueOf(tripid),
                            status, tempItem.costPerStop, tempItem.startdate, tempItem.enddate,
                            tempItem.startLocation, tempItem.stops, tempItem.passengerid);
                    myTripContent.ITEM_MAP.put(tripid, newItem);

                    //Refresh details
                    setDetails();

                } catch(Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                error = "Failure: ";
                Log.e("MyApp", "Caught error", throwable); //Error logging helper
                try {
                    error = json.getString("data");
                } catch (JSONException e1) {
                    error += e1.getMessage() + tripid;
                }
                refreshErrorMessage();
            }
        });
    }

    //Open Modify Trip page, set intents
    public void modifyTrip(View view) {
        Bundle arguments = new Bundle();
        Intent intent = new Intent(this, ModifyTrip.class);

        arguments.putString(myTripDetailFragment.ARG_ITEM_ID, String.valueOf(tripid));
        arguments.putString("EXTRA_USERNAME", eusername);
        arguments.putString("EXTRA_PASSWORD", epassword);
        intent.putExtras(arguments);
        startActivity(intent);
    }

    // Set the error message
    private void refreshErrorMessage() {
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
}
