package t08.ecse321.passengerrideshare;

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


        //Get username and password
        Intent intent = getIntent();
        eusername = intent.getStringExtra("EXTRA_USERNAME");
        epassword = intent.getStringExtra("EXTRA_PASSWORD");
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

    //When click Find Trip button, goes to findTrip Activity
    public void findTrip(View view) {
       /* final Intent intent = new Intent(this, myTripListActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", eusername);
        extras.putString("EXTRA_PASSWORD", epassword);
        intent.putExtras(extras);

        startActivity(intent); */
    }

    //When click Update User button, goes to updateUser Activity
    public void updateUser(View view) {
       /* final Intent intent = new Intent(this, myTripListActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", eusername);
        extras.putString("EXTRA_PASSWORD", epassword);
        intent.putExtras(extras);

        startActivity(intent); */
    }

    //When click sign out, finishes activity
    public void signOut(View view) {
       finish();
    }
}
