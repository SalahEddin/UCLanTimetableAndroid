package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableDatePickerDialogDatePickerDialog;

/*
* This fragment is responsible for showing the users' timetable
* */
public class FragmentTimetable extends Fragment {

    public FragmentTimetable() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        Button dateBtn = (Button) view.findViewById(R.id.datepicker_button);
        dateBtn.setOnClickListener((v)->{
            TimetableDatePickerDialogDatePickerDialog datePicker = new TimetableDatePickerDialogDatePickerDialog(v);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            datePicker.show(ft, "Date Picker");
        });
        return view;
    }

}
