package t08.ecse321.passengerrideshare;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    /*
    Use the current time as the default time in the picker
    @return A new instance of TimePickerDialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    //Check which activity it came from
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(getArguments().getInt("viewid") == R.layout.activity_search_trip) {
            SearchTrip myActivity = (SearchTrip) getActivity();
            myActivity.setTime(getArguments().getInt("id"), hourOfDay, minute);
        }
    }
}