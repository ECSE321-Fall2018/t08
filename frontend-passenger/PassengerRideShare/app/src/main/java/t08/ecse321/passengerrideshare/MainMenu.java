package t08.ecse321.passengerrideshare;

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

    //When finish onActivityResult, return here
    //If successful, reset password
    //Currently used to update user
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

    //My Trips button clicked --> go to myTripListActivity
    public void myTrips(View view) {
        final Intent intent = new Intent(this, myTripListActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", eusername);
        extras.putString("EXTRA_PASSWORD", epassword);
        intent.putExtras(extras);

        myTripContent.clear(); //clear myTrip every time login to avoid double counting
        startActivity(intent);
    }

    //Find Trip button clicked --> go to Search Trip Activity
    public void findTrip(View view) {
        final Intent intent = new Intent(this, SearchTrip.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME", eusername);
        extras.putString("EXTRA_PASSWORD", epassword);
        intent.putExtras(extras);

        startActivity(intent);
    }

    //Update User button clicked --> go to UpdaterUser Activity
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
