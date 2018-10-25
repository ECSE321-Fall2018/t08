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

import cz.msebera.android.httpclient.Header;

public class RegisterVehicle extends AppCompatActivity{

    private String error = null;
    public final String ROLE = "Driver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);
        refreshErrorMessage();
    }

    public String getError() {
        return error;
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

    //When click register vehicle button, will attempt to register
    //If registration successful, will switch to MyTripsActivity.class
    public void registerVehicleButton(View view) {
        //Creates new intent and gets information from text view
        // final Intent intent = new Intent(this, MyTripsActivity.class);

        error = "";

        final EditText vehicle_type_text = (EditText) findViewById(R.id.vehicle_type);
        final EditText vehicle_model_text = (EditText) findViewById(R.id.vehicle_model);
        final EditText vehicle_colour_text = (EditText) findViewById(R.id.vehicle_colour);
        final EditText vehicle_nb_of_seats_text = (EditText) findViewById(R.id.vehicle_nb_of_seats);

        final String driverUsername = ((EditText) findViewById(R.id.reg_username)).getText().toString();
        final String driverPassword = ((EditText) findViewById(R.id.reg_username)).getText().toString();

        final String vehicleType = vehicle_type_text.getText().toString();
        final String vehicleModel = vehicle_model_text.getText().toString();
        final String vehicleColour = vehicle_colour_text.getText().toString();
        final String vehicleNbOfSeats = vehicle_nb_of_seats_text.getText().toString();

        error = "";
        refreshErrorMessage();

        if(!checkRegisterVehicleType(vehicleType)) {
            vehicle_type_text.setText("");
            return;
        }
        if(!checkRegisterVehicleModel(vehicleModel)) {
            vehicle_model_text.setText("");
            return;
        }
        if (!checkRegisterVehicleColour(vehicleColour)) {
            vehicle_colour_text.setText("");
            return;
        }
        if (!checkRegisterVehicleNbOfSeats(Integer.parseInt(vehicleNbOfSeats))) {
            vehicle_nb_of_seats_text.setText("");
            return;
        }

        if(registerVehiclePost(driverUsername, driverPassword, vehicleNbOfSeats, vehicleColour, vehicleModel, vehicleType)) {
            vehicle_type_text.setText("");
            vehicle_model_text.setText("");
            vehicle_colour_text.setText("");
            vehicle_nb_of_seats_text.setText("");
        }
    }

    //Checks that all registration information correct
    public boolean checkRegisterVehicleType (String vehicleType) {
        if(vehicleType == null || vehicleType.equals("")) {
            error = "Please specify vehicle type.";
            refreshErrorMessage();
            return false;
        }
        return true;
    }

    public boolean checkRegisterVehicleModel (String vehicleModel) {
        if(vehicleModel == null || vehicleModel.equals("")) {
            error = "Please specify vehicle model.";
            refreshErrorMessage();
            return false;
        }
        return true;
    }

    public boolean checkRegisterVehicleColour (String vehicleColour) {
        if(vehicleColour == null || vehicleColour.equals("")) {
            error = "Please specify vehicle Colour.";
            refreshErrorMessage();
            return false;
        }
        return true;
    }

    public boolean checkRegisterVehicleNbOfSeats (int vehicleNbOfSeats) {
        /* NEED FURTHER SPECIFICATION */
        if(vehicleNbOfSeats < 2 || vehicleNbOfSeats > 4) {
            error = "Please specify an appropriate number of seats.";
            refreshErrorMessage();
            return false;
        }
        return true;
    }

    //Http Post method for registration
    public boolean registerVehiclePost(String driveruser, String driverpass, String nbOfSeats, String colour, String model, String vehicleType) {

        Bundle extras = new Bundle();
        extras.putString("EXTRA_DRIVERUSER", driveruser);
        extras.putString("EXTRA_DRIVERPASS", driverpass);

        //Creates HTTP params to authorize user according to rest model
        RequestParams params = new RequestParams();
        params.add("driveruser", driveruser);
        params.add("driverpass", driverpass);
        params.add("vehiclenumberofseats", nbOfSeats);
        params.add("colour", colour);
        params.add("model", model);
        params.add("vehicletype", vehicleType);
        params.add("role", ROLE);

        //Sends HTTP post method, if successful (response HTTP 200), switches to MyTripsActivity view), else, displays error
        HttpUtils.post("api/user/createVehicle", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();

                error = "SUCCESS";

                refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                error = "Failure: ";
                Log.e("MyApp", "Caught error", throwable); //This helps us to log our errors
                try {
                    error += "Vehicle may already exist.";
                } catch (Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
        return error == "";


    }

    public void cancelButton(View view) {
        error = "";
        refreshErrorMessage();
        finish();

    }

}
