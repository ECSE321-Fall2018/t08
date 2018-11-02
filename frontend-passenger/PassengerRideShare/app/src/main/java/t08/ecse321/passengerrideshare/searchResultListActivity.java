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

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * An activity representing a list of searchResults. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link searchResultDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class searchResultListActivity extends AppCompatActivity {

    //Whether or not the activity is in two-pane mode,
    // i.e. running on a tablet device.
    private boolean mTwoPane;
    private static String eusername;
    private static String epassword;
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        Intent intent = getIntent();
        eusername = intent.getStringExtra("EXTRA_USERNAME");
        epassword = intent.getStringExtra("EXTRA_PASSWORD");
        List<Integer> triplist = intent.getIntegerArrayListExtra("triplist");

        if (findViewById(R.id.searchresult_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        populateSearchTripsObj(triplist);

        View recyclerView = findViewById(R.id.searchresult_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, searchResultContent.ITEMS, mTwoPane));
    }

    //Will create objects of type searchResultContent.SearchResultItem for each trip found
    private boolean populateSearchTripsObj(List<Integer> tripsInt) {
        searchResultContent.clear();

        //Send HTTP Post to get trip for each trip id that the search engine found
        //Add each trip item to list and reset recycler each time new trip found
        for(Integer el: tripsInt) {
            HttpUtils.get("api/trip/trips/"+el, new RequestParams(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String cost = response.getString("costPerStop");
                        long startdate = response.getInt("startdate");
                        long enddate = response.getInt("enddate");
                        String startLoc = response.getString("startLocation");
                        String stops = response.getString("stops");
                        int status = response.getInt("status");
                        int vehicleid = response.getInt("vehicleid");

                        //Send HTTP Post to get vehicle for each trip id that the search engine found
                        //Will add vehicle info to recycler view
                        //If vehicle not found, initialize constructor with not found values for vehicle
                        HttpUtils.get("api/vehicle/vehicles/"+vehicleid, new RequestParams(), new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject vehResponse) {
                                try {
                                    String vehColour = vehResponse.getString("colour");
                                    String model = vehResponse.getString("model");
                                    String vehicleType = vehResponse.getString("vehicleType");

                                    // Create new Search Item and adds it to map
                                    searchResultContent.SearchResultItem item = new searchResultContent.SearchResultItem
                                            (el, status, cost, startdate, enddate, startLoc, stops, vehicleType, vehColour, model);
                                    searchResultContent.addItem(item);

                                    View recyclerView = findViewById(R.id.searchresult_list);
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
                                Log.e("MyApp", "Caught error", throwable); //Error logging helper
                                try {
                                    // Create new Search Item and adds it to map
                                    searchResultContent.SearchResultItem item = new searchResultContent.SearchResultItem
                                            (el, status, cost, startdate, enddate, startLoc, stops, "Not Found",
                                                    "Not Found", "Not Found");
                                    searchResultContent.addItem(item);

                                    View recyclerView = findViewById(R.id.searchresult_list);
                                    assert recyclerView != null;
                                    setupRecyclerView((RecyclerView) recyclerView);
                                } catch (Exception e) {
                                    error += e.getMessage();
                                }
                                refreshErrorMessage();
                            }
                        });
                        error = "";
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                    error = "Failure: ";
                    Log.e("MyApp", "Caught error", throwable); //Error logging helper
                    try {
                        // Should not happen, may occur if backend server does not create json correctly
                        error +=  String.valueOf(el)+"Error retrieving data. Status " + String.valueOf(statusCode);
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
            });
        }
        return true;
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final searchResultListActivity mParentActivity;
        private final List<searchResultContent.SearchResultItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResultContent.SearchResultItem item = (searchResultContent.SearchResultItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(searchResultDetailFragment.ARG_ITEM_ID, item.tripid);

                    //Adds these extra parameters when goes to detail view
                    arguments.putString("EXTRA_USERNAME", eusername);
                    arguments.putString("EXTRA_PASSWORD", epassword);
                    searchResultDetailFragment fragment = new searchResultDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.searchresult_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Bundle arguments = new Bundle();
                    Intent intent = new Intent(context, searchResultDetailActivity.class);
                    arguments.putString(searchResultDetailFragment.ARG_ITEM_ID, String.valueOf(item.tripid));

                    //Add these extra parameters when goes to detail view
                    arguments.putString("EXTRA_USERNAME", eusername);
                    arguments.putString("EXTRA_PASSWORD", epassword);
                    intent.putExtras(arguments);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(searchResultListActivity parent,
                                      List<searchResultContent.SearchResultItem> items, boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.searchresult_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).tripid);
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

    // set the error message
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
