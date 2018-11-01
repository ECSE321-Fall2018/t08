package t08.ecse321.driverrideshare;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default time in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //Check which activity it came from
        if(getArguments().getInt("viewid") == R.layout.activity_create_trip) {
            CreateTrip myActivity = (CreateTrip) getActivity();
            myActivity.setTime(getArguments().getInt("id"), hourOfDay, minute);
        }
        if(getArguments().getInt("viewid") == R.layout.activity_modify_trip) {
            ModifyTrip myActivity = (ModifyTrip) getActivity();
            myActivity.setTime(getArguments().getInt("id"), hourOfDay, minute);
        }
    }
}