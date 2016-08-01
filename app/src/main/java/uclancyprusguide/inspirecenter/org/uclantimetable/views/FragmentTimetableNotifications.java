package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.DetailedNotificationActivity;
import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.TimetableNotificationAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.MyNotificationCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;

/**
 * Retrieves events with notification types (cancelled, room changed, etc.)
 */
public class FragmentTimetableNotifications extends Fragment implements MyNotificationCallbackInterface {
    // TODO: 01/08/16 filter
    private final ArrayList<Notification> allNotifications = new ArrayList<>();
    private TimetableNotificationAdapter notificationAdapter;
    private SwipeRefreshLayout pullToRefresh;
    private Context context = null;
    private ListView eventsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_timetable_notifications, container, false);
        context = getActivity();
        // set adapter
        notificationAdapter = new TimetableNotificationAdapter(context, allNotifications, this);

        // bind the listView
        eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsListView.setAdapter(notificationAdapter);
        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(context, DetailedNotificationActivity.class);
                myIntent.putExtra(context.getString(R.string.notificationKey), allNotifications.get(i));
                startActivity(myIntent);
            }
        });

        //pull to refresh
        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadNotifications();
            }
        });

        reloadNotifications();

        return view;
    }

    private void reloadNotifications() {
        String userId = Misc.loadUser(context).getUSER_ID();
        TimetableData.LoadNotifications(userId, this, context);
    }

    @Override
    public void onNotificationDownloadFinished(final List<Notification> notifications) {
        allNotifications.clear();
        allNotifications.addAll(notifications);
        // sort
        Collections.sort(allNotifications, new Comparator<Notification>() {
            @Override
            public int compare(Notification a1, Notification a2) {
                // String implements Comparable
                return (a1.getIMPORTANT()).compareTo(a2.getIMPORTANT());
            }
        });

        // update adapter
        notificationAdapter = new TimetableNotificationAdapter(context, allNotifications, this);
        eventsListView.setAdapter(notificationAdapter);
        if (pullToRefresh.isRefreshing()) pullToRefresh.setRefreshing(false);
    }

    @Override
    public void onStatusChanged() {
        reloadNotifications();
    }

    public FragmentTimetableNotifications() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadNotifications();
    }
}
