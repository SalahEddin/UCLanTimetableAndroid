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
        ArrayList<TimetableSession> arr = new ArrayList<>();
        arr.add(new TimetableSession("Intro to Programming","CO1232","CYXX","02-7-2014 11:48:37","1:22","1:22",1,"Near","wow",new Date()));
        arr.add(new TimetableSession("Games Development","XX34","CYXX","11:48","1:22","1:22",1,"Near","wow",new Date()));

        TimetableExamAdapter eventArrAdapter = new TimetableExamAdapter(view.getContext(), arr);

        // bind the listView
        ListView eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsListView.setAdapter(eventArrAdapter);
        //pull to refresh
        SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(()->{
            arr.add(new TimetableSession("Intross","XXd","CYXX","11:48","1:22","1:22",1,"Nearchos Paspallis","wow",new Date()));
            eventArrAdapter.notifyDataSetChanged();
            new Handler().postDelayed(() -> pullToRefresh.setRefreshing(false), 5000);
        });


        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
