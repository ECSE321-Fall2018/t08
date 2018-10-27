package t08.ecse321.passengerrideshare;

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
    private final String ROLE = "Passenger";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    //When click register button, will attempt to register
    //If registration successful, will switch to MainMenu.class
    public void registerButton(View view) {
        //Gets information from text view

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
        refreshErrorMessage();

        if(checkRegister(username, password, pass_confirm, fullname, email) == false) {
            password_text.setText("");
            confirm_pass_text.setText("");
            return;
        }
        if(registerPost(username, password, fullname, email)) {
            username_text.setText("");
            password_text.setText("");
            email_text.setText("");
            confirm_pass_text.setText("");
            fullname_text.setText("");
        } else {
            password_text.setText("");
            confirm_pass_text.setText("");
        }
    }

    //Checks that all registration information correct
    public boolean checkRegister(String username, String password, String pass_confirm, String fullname, String email) {
        if(!password.equals(pass_confirm)) {
            error = "Please ensure passwords match.";
            refreshErrorMessage();
            return false;
        }
        if(password.length() < 8) {
            error = "Password must be at least 8 characters.";
            refreshErrorMessage();
            return false;
        }

        if(username == null || username.equals("") || fullname == null || fullname.equals("") || email == null || email.equals("")) {
            error = "Please fill all fields.";
            refreshErrorMessage();
            return false;
        }
        return true;
    }

    //Http Post method for registration
    public boolean registerPost(String username, String password, String fullname, String email) {

        final Intent intent = new Intent(this, MainMenu.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", username);
        extras.putString("EXTRA_PASSWORD", password);
        intent.putExtras(extras);

        //Creates HTTP params to authorize user according to rest model
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("email", email);
        params.add("fullname", fullname);
        params.add("role", ROLE);
        params.add("password", password);

        //Sends HTTP post method, if successful (response HTTP 200), switches to MainMenu view), else, displays error
        HttpUtils.post("api/user/create", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error = "";
                startActivity(intent);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
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
        if(error != "") {
            return false;
        } else {
            return true;
        }
    }

    public void cancelButton(View view) {
        error = "";
        refreshErrorMessage();
        finish();
    }
}
