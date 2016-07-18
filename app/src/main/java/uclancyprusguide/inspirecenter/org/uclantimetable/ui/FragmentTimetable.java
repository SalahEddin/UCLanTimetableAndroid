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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONEvent;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.sync.TimetableSystemAPI;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableDatePickerDialogDatePickerDialog;

/*
* This fragment is responsible for showing the users' timetable
* */
public class FragmentTimetable extends Fragment implements DatePickerDialog.OnDateSetListener {

    private Calendar selectedDate = Calendar.getInstance();
    private SwipeRefreshLayout pullToRefresh;

    public FragmentTimetable() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        final View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        // dummy array
        final ArrayList<TimetableSession> arr = new ArrayList<>();
        arr.add(new TimetableSession("Intro", "XX", "CYXX", "1:22", "1:22", "1:22", 1, "Near", "wow"));
        arr.add(new TimetableSession("Games Development", "XX34", "CYXX", "1:22", "1:22", "1:22", 1, "Near", "wow"));

        final TimetableSessionAdapter eventArrAdapter = new TimetableSessionAdapter(view.getContext(), arr);

        // bind the listView
        ListView eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsListView.setAdapter(eventArrAdapter);
        //pull to refresh
        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TimetableData.GetRerofitTimetableByStudent(arr, eventArrAdapter, TimetableData.TimetableEventsType.ALL, pullToRefresh);
            }
        });

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        } catch (NullPointerException ignored) {
        }

        LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflator.inflate(R.layout.actionbar_spinner, null);

        // spinner helps users pick a date quickly
        Spinner spinner = (Spinner) vi.findViewById(R.id.date_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout (the spinner values are predetermined in resources)
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(vi.getContext(),
                R.array.dates_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(vi);

        adapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View v,
                                               int selectedIndex, long arg3) {
                        switch (selectedIndex) {
                            case 0:
                                selectedDate = Calendar.getInstance();
                                break;
                            case 1:
                                selectedDate = Calendar.getInstance();
                                selectedDate.add(Calendar.DATE, 1);
                                break;
                            default:
                                TimetableDatePickerDialogDatePickerDialog datePicker = new TimetableDatePickerDialogDatePickerDialog();
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

        TimetableData.GetRerofitTimetableByStudent(arr, eventArrAdapter, TimetableData.TimetableEventsType.ALL, pullToRefresh);
        return view;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        selectedDate.set(year, month, day);
        Log.d("TAGS", selectedDate.toString());
    }
}
