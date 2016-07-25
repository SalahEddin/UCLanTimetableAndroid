package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import org.threeten.bp.*;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.UclanCyApplication;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableDatePickerDialogDatePickerDialog;

/*
* This fragment is responsible for showing the users' timetable
* */
public class FragmentTimetable extends Fragment implements DatePickerDialog.OnDateSetListener, TimetableData.MyCallbackInterface {

    private LocalDate selectedDate = null;
    private SwipeRefreshLayout pullToRefresh;
    private ArrayList<TimetableSession> timetableSessions;
    private TimetableSessionAdapter eventArrAdapter;

    Context context = null;
    ListView eventsListView = null;
    public FragmentTimetable() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        context = getActivity();
        final View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        // initialise dataset and adapter
        timetableSessions = new ArrayList<>();
        eventArrAdapter = new TimetableSessionAdapter(view.getContext(), timetableSessions);
        // bind the listView
        eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsListView.setAdapter(eventArrAdapter);
        //pull to refresh
        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.ALL, Misc.DateToAPIFormat(selectedDate), Misc.DateToAPIFormat(selectedDate), "622", FragmentTimetable.this, getActivity().getBaseContext());
            }
        });


        // spinner helps users pick a date quickly
        Spinner spinner = (Spinner) view.findViewById(R.id.date_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout (the spinner values are predetermined in resources)
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.dates_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);

        spinnerAdapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View v,
                                               int selectedIndex, long arg3) {
                        String selectedDateFormatted = null;
                        switch (selectedIndex) {
                            case 0:
                                selectedDate = LocalDate.now();
                                selectedDateFormatted = Misc.DateToAPIFormat(selectedDate);
                                TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.ALL, selectedDateFormatted, selectedDateFormatted, "622", FragmentTimetable.this, getActivity().getBaseContext());
                                break;
                            case 1:
                                selectedDate = LocalDate.now();
                                selectedDate.plusDays(1);
                                selectedDateFormatted = Misc.DateToAPIFormat(selectedDate);
                                TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.ALL, selectedDateFormatted, selectedDateFormatted, "622", FragmentTimetable.this, getActivity().getBaseContext());
                                break;
                            default:
                                TimetableDatePickerDialogDatePickerDialog datePicker = new TimetableDatePickerDialogDatePickerDialog(FragmentTimetable.this);
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                datePicker.show(ft, "Date Picker");

                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );
        TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.ALL, "2015-08-02", "2016-08-02", "622", FragmentTimetable.this, context);
        return view;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        selectedDate = LocalDate.of(year, month, day);
        String selectedDateFormatted = Misc.DateToAPIFormat(selectedDate);
        TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.ALL, selectedDateFormatted, selectedDateFormatted, "622", FragmentTimetable.this, context);
        Log.d("TAGS", selectedDate.toString());
    }

    @Override
    public void onDownloadFinished(ArrayList<TimetableSession> result) {

        timetableSessions.clear();
        timetableSessions.addAll(result);
        eventArrAdapter.notifyDataSetChanged();
        if (pullToRefresh.isRefreshing()) pullToRefresh.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        context = getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
