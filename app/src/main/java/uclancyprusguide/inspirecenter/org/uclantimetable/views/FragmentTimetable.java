package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import org.threeten.bp.*;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.TimetableSessionAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableDatePickerDialogDatePickerDialog;

/*
* This fragment is responsible for showing the users' timetable
* */
public class FragmentTimetable extends Fragment implements DatePickerDialog.OnDateSetListener, TimetableData.MyCallbackInterface {

    // UI Binding
    private SwipeRefreshLayout pullToRefresh;
    private TextView selectedDateTextView;
    // adapters
    private ArrayList<TimetableSession> timetableSessions;
    private TimetableSessionAdapter eventArrAdapter;
    private Context context = null;
    // Data objects
    // TODO: 31/07/16 dynamicity
    private String STUDENT_ID;
    private LocalDate selectedDate = LocalDate.now();

    public FragmentTimetable() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        context = getActivity();
        final View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        // initialise dataset and adapter
        timetableSessions = new ArrayList<>();
        eventArrAdapter = new TimetableSessionAdapter(view.getContext(), timetableSessions);
        // bind the listView
        ListView eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsListView.setAdapter(eventArrAdapter);
        //pull to refresh
        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadTimetable();
            }
        });
        // selected date textView
        selectedDateTextView = (TextView) view.findViewById(R.id.selected_date_textView);
        selectedDateTextView.setText(Misc.formatDateByPattern(selectedDate, "dd MMM, yy"));
        // next/prev day buttons
        Button nextDayButton = (Button) view.findViewById(R.id.next_day_button);
        Button prevDayButton = (Button) view.findViewById(R.id.prev_day_button);

        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusDays(1);
                reloadTimetable();
            }
        });
        prevDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusDays(1);
                reloadTimetable();
            }
        });
        // date puickker button
        Button datePickerButton = (Button) view.findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launch date picker
                TimetableDatePickerDialogDatePickerDialog datePicker = new TimetableDatePickerDialogDatePickerDialog(FragmentTimetable.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                datePicker.show(ft, "Date Picker");
            }
        });
        // date acan be clicked
        selectedDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launch date picker
                TimetableDatePickerDialogDatePickerDialog datePicker = new TimetableDatePickerDialogDatePickerDialog(FragmentTimetable.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                datePicker.show(ft, "Date Picker");
            }
        });
        // update user offline timetable

        if (Misc.IsOnline(context)) {
            String todayDate = Misc.DateToAPIFormat(LocalDate.now());
            String OfflineDateLimit = Misc.DateToAPIFormat(LocalDate.now().plusMonths(3));
            TimetableData.SaveTimetableEventsForOffline(todayDate, OfflineDateLimit, STUDENT_ID, context);
        } else {
            // user is offline
            new AlertDialog.Builder(context)
                    .setTitle("No Network connection")
                    .setMessage("you're in offline mode, you will not receive new changes to the timetable")
                    .setCancelable(false)
                    .setPositiveButton("continue in offline mode", null).create().show();
        }

        return view;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        // add one to month because of zero-based indexing
        selectedDate = LocalDate.of(year, month + 1, day);
        reloadTimetable();
        Log.d("TAGS", selectedDate.toString());
    }

    @Override
    public void onDownloadFinished(ArrayList<TimetableSession> result) {
        timetableSessions.clear();
        timetableSessions.addAll(result);
        eventArrAdapter.notifyDataSetChanged();
        if (pullToRefresh.isRefreshing()) pullToRefresh.setRefreshing(false);
    }

    private void reloadTimetable() {
        // update textView
        STUDENT_ID = Misc.loadUser(getActivity()).getACCOUNT_ID();
        selectedDateTextView.setText(Misc.formatDateByPattern(selectedDate, "dd MMM, yy"));
        String selectedDateFormatted = Misc.DateToAPIFormat(selectedDate);
        String type = Misc.loadUser(context).getACCOUNT_TYPE_ID();
        if (type.equals("5")) {
            TimetableData.LoadTimetableEvents(selectedDateFormatted, selectedDateFormatted, STUDENT_ID, FragmentTimetable.this, context, TimetableData.TimetableType.STUDENT);
        } else {
            TimetableData.LoadTimetableEvents(selectedDateFormatted, selectedDateFormatted, STUDENT_ID, FragmentTimetable.this, context, TimetableData.TimetableType.LECTURER);
        }
    }
}
