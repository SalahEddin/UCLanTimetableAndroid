package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import uclancyprusguide.inspirecenter.org.uclantimetable.ui.FragmentTimetable;


/**
 * Created by salah on 01/07/16.
 */
public class TimetableDatePickerDialogDatePickerDialog extends DialogFragment{

    FragmentTimetable fragmentTimetable;

    public TimetableDatePickerDialogDatePickerDialog(FragmentTimetable fragmentTimetable) {
        this.fragmentTimetable = fragmentTimetable;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int year = c.get(java.util.Calendar.YEAR);
        int month = c.get(java.util.Calendar.MONTH);
        int day = c.get(java.util.Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), fragmentTimetable, year, month, day);
    }

//    @Override
//    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//        String selectedDate = day+"-"+month+"-"+year;
//        Log.d("TAG", selectedDate);
//    }
}
