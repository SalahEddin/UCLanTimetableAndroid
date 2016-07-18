package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;


/**
 * Retreives events of type exam from current date onwards
 */
public class FragmentExams extends Fragment {

    public FragmentExams() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_exams, container, false);

        // dummy array
        final ArrayList<TimetableSession> arr = new ArrayList<>();

        final TimetableExamAdapter eventArrAdapter = new TimetableExamAdapter(view.getContext(), arr);

        // bind the listView
        ListView eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsListView.setAdapter(eventArrAdapter);
        //pull to refresh
        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TimetableData.GetRerofitTimetableByStudent(arr, eventArrAdapter, TimetableData.TimetableEventsType.EXAMS, pullToRefresh);
            }
        });

        TimetableData.GetRerofitTimetableByStudent(arr, eventArrAdapter, TimetableData.TimetableEventsType.EXAMS, pullToRefresh);

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
