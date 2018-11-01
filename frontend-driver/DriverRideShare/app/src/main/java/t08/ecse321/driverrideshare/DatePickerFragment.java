package t08.ecse321.driverrideshare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        //Check which activity it came from
        if(getArguments().getInt("viewid") == R.layout.activity_create_trip) {
            CreateTrip myActivity = (CreateTrip) getActivity();
            myActivity.setDate(getArguments().getInt("id"), day, month, year);
        }
        if(getArguments().getInt("viewid") == R.layout.activity_modify_trip) {
            ModifyTrip myActivity = (ModifyTrip) getActivity();
            myActivity.setDate(getArguments().getInt("id"), day, month, year);
        }
    }
}