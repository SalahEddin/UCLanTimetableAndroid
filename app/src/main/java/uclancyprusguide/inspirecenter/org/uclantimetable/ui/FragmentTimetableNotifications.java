package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
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
 * Retrieves events with notification types (cancelled, room changed, etc.)
 *
 */
public class FragmentTimetableNotifications extends Fragment {

    public FragmentTimetableNotifications() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_timetable_notifications, container, false);
        // dummy array
        final ArrayList<TimetableSession> arr = new ArrayList<>();
        arr.add(new TimetableSession("Room Change",getResources().getString(R.string.lerom_ipsum),"http://www.google.com",new Date()));
        arr.add(new TimetableSession("Room Change",getResources().getString(R.string.lerom_ipsum),"http://www.Facebook.com",new Date()));

        final TimetableNotificationAdapter eventArrAdapter = new TimetableNotificationAdapter(view.getContext(), arr);

        // bind the listView
        ListView eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsListView.setAdapter(eventArrAdapter);
        //pull to refresh
        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arr.add(new TimetableSession("Room Change", FragmentTimetableNotifications.this.getResources().getString(R.string.lerom_ipsum), "http://www.Facebook.com", new Date()));
                eventArrAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefresh.setRefreshing(false);
                    }
                }, 5000);
            }
        });

        return view;
    }
}
