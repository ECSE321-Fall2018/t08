package t08.ecse321.driverrideshare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    private static String eusername;
    private static String epassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Intent intent = getIntent();
        eusername = intent.getStringExtra("EXTRA_USERNAME");
        epassword = intent.getStringExtra("EXTRA_PASSWORD");
    }

    //When finish onActivityResult, returns here
    //If successful, resets password
    //This is currently used for update user
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                epassword=data.getStringExtra("EXTRA_PASSWORD");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    //When click My Trips button, goes to myTripListActivity
    public void myTrips(View view) {
        final Intent intent = new Intent(this, myTripListActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", eusername);
        extras.putString("EXTRA_PASSWORD", epassword);
        intent.putExtras(extras);

        myTripContent.clear(); //clears myTrip everytime login to avoid double counting
        startActivity(intent);
    }

    //When click Create Trip button, goes to findTrip Activity
    public void createTrip(View view) {
        final Intent intent = new Intent(this, CreateTrip.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", eusername);
        extras.putString("EXTRA_PASSWORD", epassword);
        intent.putExtras(extras);

        startActivity(intent);
    }

    //When click Update User button, goes to UpdaterUser Activity
    public void updateUser(View view) {
        final Intent intent = new Intent(this, UpdateUser.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", eusername);
        extras.putString("EXTRA_PASSWORD", epassword);
        intent.putExtras(extras);

        startActivityForResult(intent, 1);

    }

    //When click sign out, finishes activity
    public void signOut(View view) {
        finish();
    }
}
