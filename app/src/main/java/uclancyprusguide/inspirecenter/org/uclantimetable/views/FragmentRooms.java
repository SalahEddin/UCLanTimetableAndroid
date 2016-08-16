package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.TimetableSessionAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.JSONRoom;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.RoomCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.RoomDatePickerDialogDatePickerDialog;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;


public class FragmentRooms extends Fragment implements TimetableData.MyCallbackInterface, RoomCallbackInterface, DatePickerDialog.OnDateSetListener {

    // UI Binding
    private SwipeRefreshLayout pullToRefresh;
    private TextView selectedDateTextView;
    private Spinner roomSpinner;
    private Context context = null;
    // Data objects
    private LocalDate selectedDate = LocalDate.now();
    private ArrayList<TimetableSession> timetableSessions;
    private TimetableSessionAdapter eventArrAdapter;
    private List<JSONRoom> roomsList; // dictionary
    private ArrayList<String> roomCodeArrayList;
    private ArrayAdapter<String> roomSpinnerAdapter;


    public FragmentRooms() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);
        context = getActivity();

        // initialise dataset and adapter
        timetableSessions = new ArrayList<>();
        eventArrAdapter = new TimetableSessionAdapter(view.getContext(), timetableSessions);
        // bind the listView
        ListView eventsListView = (ListView) view.findViewById(R.id.roomEventsListView);
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
        // room selection UI
        roomSpinner = (Spinner) view.findViewById(R.id.room_codes_spinner);
        roomCodeArrayList = new ArrayList<>();
        roomSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, roomCodeArrayList);
        roomSpinner.setAdapter(roomSpinnerAdapter);
        TimetableData.GetRooms(this, context);

        roomSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View v,
                                               int selectedIndex, long arg3) {
                        reloadTimetable();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );
        //
        selectedDateTextView = (TextView) view.findViewById(R.id.selected_date_textView);
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
                RoomDatePickerDialogDatePickerDialog datePicker = new RoomDatePickerDialogDatePickerDialog(FragmentRooms.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                datePicker.show(ft, "Date Picker");
            }
        });
        // text view is clickable
        // date acan be clicked
        selectedDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launch date picker
                RoomDatePickerDialogDatePickerDialog datePicker = new RoomDatePickerDialogDatePickerDialog(FragmentRooms.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                datePicker.show(ft, "Date Picker");
            }
        });

        return view;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        try {
            selectedDate = LocalDate.of(year, month + 1, day);
            reloadTimetable();
        } catch (Exception ex) {
            Log.d("error", ex.toString());
        }
    }

    @Override
    public void onDownloadFinished(ArrayList<TimetableSession> result) {
        timetableSessions.clear();
        timetableSessions.addAll(result);
        eventArrAdapter.notifyDataSetChanged();
        if (pullToRefresh.isRefreshing()) pullToRefresh.setRefreshing(false);
    }

    @Override
    public void onDownload(List<JSONRoom> rooms) {
        roomsList = rooms;
        roomCodeArrayList.clear();
        for (JSONRoom room : rooms) {
            roomCodeArrayList.add(room.getROOM_CODE());
        }
        roomSpinnerAdapter.notifyDataSetChanged();
    }

    String getRoomIdFromName(String name) {
        String s = null;
        for (JSONRoom room : roomsList) {
            if (room.getROOM_CODE().equals(name)) {
                s = room.getROOM_ID();
                break;
            }
        }
        return s;
    }

    private void reloadTimetable() {
        // update textView
        selectedDateTextView.setText(Misc.formatDateByPattern(selectedDate, "dd MMM, yy"));
        String selectedDateFormatted = Misc.DateToAPIFormat(selectedDate);
        TimetableData.LoadTimetableEvents(selectedDateFormatted, selectedDateFormatted, getRoomIdFromName(roomSpinner.getSelectedItem().toString()), FragmentRooms.this, context, TimetableData.TimetableType.ROOM);
    }
}
