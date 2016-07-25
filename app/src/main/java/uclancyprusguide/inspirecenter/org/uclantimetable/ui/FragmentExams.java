package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;


/**
 * Retreives events of type exam from current date onwards
 */
public class FragmentExams extends Fragment implements TimetableData.MyCallbackInterface {

    private ArrayList<TimetableSession> sessions;
    private TimetableExamAdapter eventArrAdapter;
    private SwipeRefreshLayout pullToRefresh;
    public FragmentExams() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_exams, container, false);

        // dummy array
        sessions = new ArrayList<>();
        eventArrAdapter = new TimetableExamAdapter(view.getContext(), sessions);

        // bind the listView
        ListView eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsListView.setAdapter(eventArrAdapter);
        //pull to refresh
        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.EXAMS, "2015-08-01", "2016-08-01", "622", FragmentExams.this, getActivity().getBaseContext());
            }
        });

        TimetableData.LoadTimetableEvents(TimetableData.TimetableEventsType.EXAMS, "2015-08-01", "2017-08-01", "622", FragmentExams.this, getActivity().getBaseContext());

        return view;
    }

    @Override
    public void onDownloadFinished(ArrayList<TimetableSession> result) {
        sessions.clear();
        sessions.addAll(result);
        eventArrAdapter.notifyDataSetChanged();
        if (pullToRefresh.isRefreshing()) pullToRefresh.setRefreshing(false);
    }
}
