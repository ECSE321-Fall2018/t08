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

public class Register extends AppCompatActivity {
    private String error = null;
    private final String ROLE = "Driver";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    //When click login button, will attempt to login
    //If login successful, will switch to MyTripsActivity.class
    public void registerButton(View view) {
        //Creates new intent and gets username and password from text view
        final Intent intent = new Intent(this, MainDriver.class);
        final EditText username_text = (EditText) findViewById(R.id.reg_username);
        final EditText password_text = (EditText) findViewById(R.id.reg_password1);
        final EditText confirm_pass_text = (EditText) findViewById(R.id.reg_password2);
        final EditText fullname_text = (EditText) findViewById(R.id.reg_fullname);
        final EditText email_text = (EditText) findViewById(R.id.reg_email);


        final String username = username_text.getText().toString();
        final String password = password_text.getText().toString();
        final String pass_confirm = confirm_pass_text.getText().toString();
        final String fullname = fullname_text.getText().toString();
        final String email = email_text.getText().toString();
        error = "";

        if(!password.equals(pass_confirm)) {
            error = "Please ensure passwords match.";
            refreshErrorMessage();
            password_text.setText("");
            confirm_pass_text.setText("");
            return;
        }
        if(password.length() < 8) {
            error = "Password must be at least 8 characters.";
            refreshErrorMessage();
            password_text.setText("");
            confirm_pass_text.setText("");
            return;
        }

        if(username == null || username.equals("") || fullname == null || fullname.equals("") || email == null || email.equals("")) {
            error = "Please fill all fields.";
            refreshErrorMessage();
            return;
        }



        refreshErrorMessage();

        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", username);
        extras.putString("EXTRA_PASSWORD", password);
        //intent.putExtras(extras);

        //Creates HTTP params to authorize user according to rest model
        error = "";
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("email", email);
        params.add("fullname", fullname);
        params.add("role", ROLE);
        params.add("password", password);



        //Sends HTTP post method, if successful (response HTTP 200), switches to MyTripsActivity view), else, displays error
        HttpUtils.post("api/user/create", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                username_text.setText("");
                password_text.setText("");
                email_text.setText("");
                confirm_pass_text.setText("");
                fullname_text.setText("");

                //error = "SUCCESS";
                //error = "";
                startActivity(intent);
                refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                username_text.setText("");
                password_text.setText("");
                error = "Failure: ";
                Log.e("MyApp", "Caught error", throwable); //This helps us to log our errors
                try {
                    error += "User may already exist.";
                } catch (Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });


    }

    public void cancelButton(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
