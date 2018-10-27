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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);
    }

    public void openTripDetails(View view) {
        final Intent intent = new Intent(this, CreateTrip_Details.class);
        startActivity(intent);

        RequestParams params = new RequestParams();

        //Sends HTTP post method, if successful (response != -1, switches to MyTripsActivity view), else, displays error
        HttpUtils.post("api/user/authorize", params, new JsonHttpResponseHandler() {
           @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                error = "Failure: trip data cannot be presented";
                Log.e("MyApp", "Caught error", throwable); //This helps us to log our errors
                try {
                    error += "Failure: trip data cannot be presented";
                } catch (Exception e) {
                    error += e.getMessage();
                }
  //              refreshErrorMessage();
            }
        });

    }
}
