package t08.ecse321.passengerrideshare;

import android.app.Activity;
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

public class UpdateUser extends AppCompatActivity {

    private String error = null;
    private String eusername;
    private String epassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        //Get username and password
        Intent intent = getIntent();
        eusername = intent.getStringExtra("EXTRA_USERNAME");
        epassword = intent.getStringExtra("EXTRA_PASSWORD");

        error = "";
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

    //When click update user button, will attempt to update user
    //If update user successful, will switch to MainMenu.class
    public void updateButton(View view) {
        //Gets information from text view

        final EditText email_text = (EditText) findViewById(R.id.up_email);
        final EditText fullname_text = (EditText) findViewById(R.id.up_fullname);
        final EditText curpass_text = (EditText) findViewById(R.id.up_curpass);
        final EditText newpass1_text = (EditText) findViewById(R.id.up_newpass1);
        final EditText newpass2_text = (EditText) findViewById(R.id.up_newpass2);


        final String email = email_text.getText().toString();
        final String fullname = fullname_text.getText().toString();
        final String curpass = curpass_text.getText().toString();
        final String newpass1 = newpass1_text.getText().toString();
        final String newpass2 = newpass2_text.getText().toString();
        error = "";
        refreshErrorMessage();

        if(checkUpdateUser(email, fullname, curpass, newpass1, newpass2) == false) {
            curpass_text.setText("");
            newpass1_text.setText("");
            newpass2_text.setText("");
            return;
        }
        if(updatePost(email, fullname, curpass, newpass1)) {
            email_text.setText("");
            fullname_text.setText("");
            curpass_text.setText("");
            newpass1_text.setText("");
            newpass2_text.setText("");
        } else {
            curpass_text.setText("");
            newpass1_text.setText("");
            newpass2_text.setText("");
        }
    }

    //Checks that all update user information correct, note that fields may be left empty if not updated
    public boolean checkUpdateUser(String email, String fullname, String curpass, String newpass1, String newpass2) {
        if(curpass == null || curpass.equals("")) {
            error = "Please enter your current password.";
            refreshErrorMessage();
            return false;
        }
        if(!newpass1.equals(newpass2)) {
            error = "New passwords must match.";
            refreshErrorMessage();
            return false;
        }
        if(newpass1 != null && !newpass1.equals("")) {
            if(newpass1.length() < 8) {
                error = "Password must be at least 8 characters.";
                refreshErrorMessage();
                return false;
            }
        }

        return true;
    }

    //Http Post method for update user
    public boolean updatePost(String email, String fullname, String curpass, String newpass1) {

        //Creates HTTP params to authorize user according to rest model
        RequestParams params = new RequestParams();
        params.add("username", eusername);
        if(email!= null && !email.equals("")) {
            params.add("email", email);
        }
        if(fullname!= null && !fullname.equals("")) {
            params.add("name", fullname);
        }
        if(newpass1!= null && !newpass1.equals("")) {
            params.add("newpass", newpass1);
        }
        params.add("oldpass", curpass);

        //Sends HTTP post method, if successful (response HTTP 200), switches to MainMenu view), else, displays error
        HttpUtils.post("api/user/update", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error = "";

                //Resets password in intent and returns it for result
                Intent newintent = new Intent();
                newintent.putExtra("EXTRA_PASSWORD", newpass1);
                setResult(Activity.RESULT_OK, newintent);
                finish();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                error = "Failure: ";
                Log.e("MyApp", "Caught error", throwable); //This helps us to log our errors
                try {
                    error += "Error updating user. Current password may be incorrect.";
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
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
