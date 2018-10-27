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

public class CreateTrip extends AppCompatActivity {
    private String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip__details);
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

    public void createATripButton(View view) {

        final EditText cost1_text = (EditText) findViewById(R.id.cost1);
        final EditText cost2_text = (EditText) findViewById(R.id.cost2);
        final EditText cost3_text = (EditText) findViewById(R.id.cost3);
        final EditText stop1_text = (EditText) findViewById(R.id.stop1);
        final EditText stop2_text = (EditText) findViewById(R.id.stop2);
        final EditText stop3_text = (EditText) findViewById(R.id.stop3);
        final EditText startlocation_text = (EditText) findViewById(R.id.startlocation);
        final EditText startdate_text = (EditText) findViewById(R.id.startdate);
        final EditText finishdate_text = (EditText) findViewById(R.id.finishdate);

        final List<String> stopsList = null;
        final List<String> costsList = null;

        final String cost1 = cost1_text.getText().toString();
        final String cost2 = cost2_text.getText().toString();
        final String cost3 = cost3_text.getText().toString();
        final String stop1 = stop1_text.getText().toString();
        final String stop2 = stop2_text.getText().toString();
        final String stop3 = stop3_text.getText().toString();

        stopsList.add(stop1);
        stopsList.add(stop2);
        stopsList.add(stop3);
        costsList.add(cost1);
        costsList.add(cost2);
        costsList.add(cost3);

        final String costs = ConcatToken.concatenator(costsList, ";");
        final String stops = ConcatToken.concatenator(stopsList, ";");
        final String startlocation = startlocation_text.getText().toString();
        final String startdate = startdate_text.getText().toString();
        final String finishdate = finishdate_text.getText().toString();
        error = "";
        refreshErrorMessage();

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

                startlocation_text.setText("");
                startdate_text.setText("");
                finishdate_text.setText("");
                stop1_text.setText("");
                stop2_text.setText("");
                stop3_text.setText("");
                cost1_text.setText("");
                cost2_text.setText("");
                cost3_text.setText("");

                startActivity(intent);
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

    public void cancelButton(View view) {

        Intent intent = new Intent(this, MainDriver.class);
        startActivity(intent);
    }
}
