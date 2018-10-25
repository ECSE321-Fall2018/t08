package t08.ecse321.passengerrideshare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of myTrips. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link myTripDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class myTripListActivity extends AppCompatActivity {

    private String error;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static String eusername;
    private static String epassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrip_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        Intent intent = getIntent();
        eusername = intent.getStringExtra("EXTRA_USERNAME");
        epassword = intent.getStringExtra("EXTRA_PASSWORD");

        if (findViewById(R.id.mytrip_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    //This method is called when the activity is brought back to the top of the stack and also after onCreate()
    @Override
    protected void onResume() {
        super.onResume();

        error = "";
        refreshErrorMessage();

        Intent intent =  getIntent();
        eusername = intent.getStringExtra("EXTRA_USERNAME");
        epassword = intent.getStringExtra("EXTRA_PASSWORD");
        myTripContent.clear();

        //Resets view if information changed (cancelled trip)
        List<Integer> intList = receiveUserTrips(eusername, epassword);
        View recyclerView = findViewById(R.id.mytrip_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private boolean populateUserTripsObj(List<Integer> tripsInt) {
        //Sends HTTP Post to get trip for each trip id that user is on
        //Will add each trip item to list and reset recycler each time new trip found
        for(Integer el: tripsInt) {
            HttpUtils.get("api/trip/trips/"+el, new RequestParams(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String cost = response.getString("costPerStop");
                        int startdate = response.getInt("startdate");
                        int enddate = response.getInt("enddate");
                        String startLoc = response.getString("startLocation");
                        String stops = response.getString("stops");
                        int status = response.getInt("status");

                        //creates new TripItem and adds it to Has map
                        myTripContent.TripItem item = new myTripContent.TripItem(el, status, cost, startdate, enddate, startLoc, stops);
                        myTripContent.addItem(item);

                        View recyclerView = findViewById(R.id.mytrip_list);
                        assert recyclerView != null;
                        setupRecyclerView((RecyclerView) recyclerView);

                        error = "";
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                    error = "Failure: ";
                    Log.e("MyApp", "Caught error", throwable); //This helps us to log our errors
                    try {
                        error +=  String.valueOf(el)+"Error retrieving data. Status " + String.valueOf(statusCode); //This case should not happen, may occur if backend server does not create json correctly
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
            });
        }
        return true;
    }

    //Obtains list of integer trip ids that user is on
    public List<Integer> receiveUserTrips(String username, String password) {
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("password", password);

        ArrayList<Integer> list = new ArrayList<Integer>();
        //Sends HTTP Post to find trip number of user
        HttpUtils.post("api/trip/usertrips", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONArray array = response.optJSONArray("data");
                    if(array == null) {
                        error = "No data.";
                    } else {
                        for(int i = 0; i < array.length(); i++) {
                            //For each integer, adds it to list
                            list.add(array.optInt(i));
                        }
                        //Will call method to now retrieve information for each trip
                        populateUserTripsObj(list);
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
                    error +=  "Error retrieving data. Status " + String.valueOf(statusCode); //This case should not happen, may occur if backend server does not create json correctly
                }
                 catch (Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
        return list;
    }

    public void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, myTripContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final myTripListActivity mParentActivity;
        private final List<myTripContent.TripItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTripContent.TripItem item = (myTripContent.TripItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(myTripDetailFragment.ARG_ITEM_ID, String.valueOf(item.tripid));

                    //Adds these extra parameters when goes to detail view
                    arguments.putString("EXTRA_USERNAME", eusername);
                    arguments.putString("EXTRA_PASSWORD", epassword);
                    myTripDetailFragment fragment = new myTripDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mytrip_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Bundle arguments = new Bundle();
                    Intent intent = new Intent(context, myTripDetailActivity.class);

                    arguments.putString(myTripDetailFragment.ARG_ITEM_ID, String.valueOf(item.tripid));

                    //Adds these extra parameters when goes to detail view
                    arguments.putString("EXTRA_USERNAME", eusername);
                    arguments.putString("EXTRA_PASSWORD", epassword);
                    intent.putExtras(arguments);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(myTripListActivity parent,
                                      List<myTripContent.TripItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mytrip_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(mValues.get(position).tripid));
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
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
}
