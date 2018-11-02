package t08.ecse321.driverrideshare;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * A fragment representing a single myTrip detail screen.
 * This fragment is either contained in a {@link myTripListActivity}
 * in two-pane mode (on tablets) or a {@link myTripDetailActivity}
 * on handsets.
 */
public class myTripDetailFragment extends Fragment {

    //The fragment argument representing the item ID of this fragment.
    public static final String ARG_ITEM_ID = "item_id";

    // The trip content this fragment presents.
    private myTripContent.TripItem mItem;
    private LayoutInflater inflater;
    private ViewGroup container;

   //Mandatory empty constructor for the fragment manager to instantiate the
   // fragment (e.g. upon screen orientation changes).
    public myTripDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the trip content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            String id = getArguments().getString(ARG_ITEM_ID);
            mItem = myTripContent.ITEM_MAP.get(id);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mytrip_detail, container, false);

        this.inflater = inflater;
        this.container = container;

        if (mItem != null) {
            //Show the trip content as text in a TextView.
            ((TextView) rootView.findViewById(R.id.mytrip_detail)).setText(mItem.details);

            //Set the radio button for trip status to the current setting
            int status = mItem.tripStatus;
            switch (status) {
                case 0:
                    ((RadioButton) rootView.findViewById(R.id.rb_ongoing)).setChecked(true);
                    break;
                case 1:
                    ((RadioButton) rootView.findViewById(R.id.rb_plan)).setChecked(true);
                    break;
                case 2:
                    ((RadioButton) rootView.findViewById(R.id.rb_completed)).setChecked(true);
                    break;
            }
        }
        return rootView;
    }

    //Update the details when resumes view
    @Override
    public void onResume() {
        super.onResume();
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the trip content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            String id = getArguments().getString(ARG_ITEM_ID);
            mItem = myTripContent.ITEM_MAP.get(id);
        }
        View rootView = inflater.inflate(R.layout.mytrip_detail, container, false);
        if(mItem != null) {
            ((TextView) rootView.findViewById(R.id.mytrip_detail)).setText(mItem.details);
        }
    }
}
