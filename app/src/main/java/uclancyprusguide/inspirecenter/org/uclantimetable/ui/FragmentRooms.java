package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONRoom;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.MyRoomCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.RoomDatePickerDialogDatePickerDialog;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;


public class FragmentRooms extends Fragment implements TimetableData.MyCallbackInterface, MyRoomCallbackInterface, DatePickerDialog.OnDateSetListener {

    // UI Binding
    private SwipeRefreshLayout pullToRefresh;
    private Context context = null;
    private Spinner roomSpinner;
    // Data objects
    private final String STUDENT_ID = "622";
    private LocalDate selectedDate = null;
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
                        String selectedDateFormatted = null;
                        String roomID = "x";
                        Log.d("x", "xxxx");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );

        // spinner helps users pick a date quickly
        Spinner dateSpinner = (Spinner) view.findViewById(R.id.date_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout (the spinner values are predetermined in resources)
        final ArrayAdapter<CharSequence> dateSpinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.dates_array, android.R.layout.simple_spinner_item);


        // Specify the layout to use when the list of choices appears
        dateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        dateSpinner.setAdapter(dateSpinnerAdapter);
        dateSpinnerAdapter.notifyDataSetChanged();
        dateSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View v,
                                               int selectedIndex, long arg3) {
                        String selectedDateFormatted = null;
                        String roomID = null;

                        switch (selectedIndex) {
                            case 0:
                                selectedDate = LocalDate.now();
                                if (roomSpinner.getSelectedItem() == null) break;
                                roomID = getRoomIdFromName(roomSpinner.getSelectedItem().toString());
                                if (roomID != null) {
                                    TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.ALL, Misc.DateToAPIFormat(selectedDate), Misc.DateToAPIFormat(selectedDate), roomID, FragmentRooms.this, context, TimetableData.TimetableType.ROOM);
                                }
                                break;
                            case 1:
                                selectedDate = LocalDate.now();
                                selectedDate.plusDays(1);
                                selectedDateFormatted = Misc.DateToAPIFormat(selectedDate);
                                roomID = getRoomIdFromName(roomSpinner.getSelectedItem().toString());
                                if (roomID != null) {
                                    TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.ALL, Misc.DateToAPIFormat(selectedDate), Misc.DateToAPIFormat(selectedDate), roomID, FragmentRooms.this, context, TimetableData.TimetableType.ROOM);
                                }
                                break;
                            default:
                                RoomDatePickerDialogDatePickerDialog datePicker = new RoomDatePickerDialogDatePickerDialog(FragmentRooms.this);
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

        return view;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        try {
            selectedDate = LocalDate.of(year, month + 1, day);

            TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.ALL, Misc.DateToAPIFormat(selectedDate), Misc.DateToAPIFormat(selectedDate), getRoomIdFromName(roomSpinner.getSelectedItem().toString()), FragmentRooms.this, context, TimetableData.TimetableType.ROOM);
            Log.d("TAGS", selectedDate.toString());
        } catch (Exception ex) {
            Log.d("error", ex.toString());
        }
    }

    @Override
    public void onDownloadFinished(ArrayList<TimetableSession> result) {
        timetableSessions.clear();
        timetableSessions.addAll(result);
        eventArrAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRoomDownloadFinished(List<JSONRoom> rooms) {
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
}
