package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.TimetableExamAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;


/**
 * Retreives events of type exam from current date onwards
 */
public class FragmentExams extends Fragment implements TimetableData.MyCallbackInterface {

    private ArrayList<TimetableSession> sessions;
    private TimetableExamAdapter eventArrAdapter;
    private SwipeRefreshLayout pullToRefresh;

    private LocalDate today = LocalDate.now();
    private final String todayApi = Misc.DateToAPIFormat(today);
    private final String tillDateApi = Misc.DateToAPIFormat(today.plusMonths(3));

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
                String account_id = Misc.loadUser(getActivity()).getACCOUNT_ID();
                TimetableData.LoadTimetableEvents(todayApi, tillDateApi, account_id, FragmentExams.this, getActivity().getBaseContext(), TimetableData.TimetableType.STUDENT);
            }
        });
        String account_id = Misc.loadUser(getActivity()).getACCOUNT_ID();
        TimetableData.LoadTimetableEvents(todayApi, tillDateApi, account_id, FragmentExams.this, getActivity().getBaseContext(), TimetableData.TimetableType.STUDENT);

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
