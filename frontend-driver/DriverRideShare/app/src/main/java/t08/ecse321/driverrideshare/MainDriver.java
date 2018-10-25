package t08.ecse321.driverrideshare;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static java.io.File.separator;

public class MainDriver extends AppCompatActivity {
    private String error = null;

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

    // method returns a string
    // put the separator will separate each element of the array in the string
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String concatenator(List<EditText> list, String separator) {
        return String.join(separator, (CharSequence) list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);
        refreshErrorMessage();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openTripDetails(View view) {
        final Intent intent = new Intent(this, CreateTrip_Details.class);
        startActivity(intent);

        refreshErrorMessage();

        //Put all three costs into one list.
        final EditText cost1_text = (EditText) findViewById(R.id.cost1);
        final EditText cost2_text = (EditText) findViewById(R.id.cost2);
        final EditText cost3_text = (EditText) findViewById(R.id.cost3);
        final List<EditText> costsList_text = null;
        costsList_text.add(cost1_text);
        costsList_text.add(cost2_text);
        costsList_text.add(cost3_text);

        //Put all three stops into one list.
        final EditText stop1_text = (EditText) findViewById(R.id.stop1);
        final EditText stop2_text = (EditText) findViewById(R.id.stop2);
        final EditText stop3_text = (EditText) findViewById(R.id.stop3);
        final List<EditText> stopsList_text = null;
        stopsList_text.add(stop1_text);
        stopsList_text.add(stop2_text);
        stopsList_text.add(stop3_text);

        final EditText startlocation_text = (EditText) findViewById(R.id.startlocation);
        final EditText startdate_text = (EditText) findViewById(R.id.startdate);
        final EditText finishdate_text = (EditText) findViewById(R.id.finishdate);

        final String costs = concatenator(costsList_text, ";");
        final String stops = concatenator(stopsList_text, ";");
        final String startlocation = startlocation_text.getText().toString();
        final String startdate = startdate_text.getText().toString();
        final String finishdate = finishdate_text.getText().toString();
        error = "";

        //Creates HTTP params to authorize user according to rest model
        error = "";
        RequestParams params = new RequestParams();
        params.add("costs", costs);
        params.add("stops", stops);
        params.add("startLocation", startlocation);
        params.add("startDate", startdate);
        params.add("endDate", finishdate);

        //Sends HTTP post method, if successful (response != -1, switches to MyTripsActivity view), else, displays error
        HttpUtils.post("api/user/authorize", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                startlocation_text.setText(startlocation);
                startdate_text.setText(startdate);
                finishdate_text.setText(finishdate);
                stop1_text.setText(stops.split(";")[0]);
                stop2_text.setText(stops.split(";")[1]);
                stop3_text.setText(stops.split(";")[2]);
                cost1_text.setText(costs.split(";")[0]);
                cost2_text.setText(costs.split(";")[1]);
                cost3_text.setText(costs.split(";")[2]);

                refreshErrorMessage();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {

                startlocation_text.setText("");
                startdate_text.setText("");
                finishdate_text.setText("");
                stop1_text.setText("");
                stop2_text.setText("");
                stop3_text.setText("");
                cost1_text.setText("");
                cost2_text.setText("");
                cost3_text.setText("");

                error = "Failure: trip data cannot be presented";
                Log.e("MyApp", "Caught error", throwable); //This helps us to log our errors
                try {
                    error += "Failure: trip data cannot be presented";
                } catch (Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });

    }
}
