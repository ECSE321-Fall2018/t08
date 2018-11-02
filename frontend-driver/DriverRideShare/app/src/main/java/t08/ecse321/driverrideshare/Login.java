package t08.ecse321.driverrideshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class Login extends AppCompatActivity {

    private String error = null;
    public final String ROLE = "Driver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refreshErrorMessage();
    }

    //Set the error message
    private void refreshErrorMessage() {

        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    // Increase the menu
    // Allow items to be added items to the action bar if present.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //When click login button, will attempt to login
    //If successful, will switch to MyTripsActivity.class
    public void loginButton(View view) {
        //Create new intent and get username & password from text view
        final EditText username_text = (EditText) findViewById(R.id.username_text);
        final EditText password_text = (EditText) findViewById(R.id.password_text);
        final String username = username_text.getText().toString();
        final String password = password_text.getText().toString();

        login(username, password);
        password_text.setText("");
    }

    public boolean login(String username, String password) {
        error = "";
        refreshErrorMessage();

        //Create new intent and gets username and password from text view
        final Intent intent = new Intent(this, MainMenu.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", username);
        extras.putString("EXTRA_PASSWORD", password);
        intent.putExtras(extras);

        //Create HTTP params to authorize user according to rest model
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("password", password);
        params.add("role", ROLE);

        //Send HTTP post method
        // if successful (response != -1) switches to MainMenu view
        // else, displays error
        HttpUtils.post("api/user/authorize", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int result = response.getInt("data");
                    if(result == -1) {
                        error = "Username or password invalid.";
                    } else {
                        error = "";
                        startActivity(intent);
                    }
                } catch(Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                error = "Failure: ";
                Log.e("MyApp", "Caught error", throwable); //Error logging help
                try {
                    if(json.getInt("data") == -1) {
                        error += "Username or password invalid.";
                    } else if(!Integer.valueOf(json.getInt("data")).equals(-1)) {
                        //Should not happen, may occur if json not created correctly (backend server)
                        error += "Status " + String.valueOf(statusCode);
                    }
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

    //When click register button, switch to register activity
    public void registerButton(View view) {
        error = "";
        refreshErrorMessage();
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
