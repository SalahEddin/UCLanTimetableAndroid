package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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

    private final ArrayList<Notification> allNotifications = new ArrayList<>();
    private final ArrayList<Notification> selectedNotifications = new ArrayList<>();
    private TimetableNotificationAdapter notificationAdapter;
    private SwipeRefreshLayout pullToRefresh;
    private Context context = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_timetable_notifications, container, false);
        context = getActivity();
        // set adapter
        notificationAdapter = new TimetableNotificationAdapter(view.getContext(), selectedNotifications, this);

        // bind the listView
        ListView eventsListView = (ListView) view.findViewById(R.id.eventsListView);
        eventsListView.setAdapter(notificationAdapter);

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
    public void onNotificationDownloadFinished(List<Notification> notifications) {
        allNotifications.clear();
        allNotifications.addAll(notifications);
        // fill the viewed notifs
        selectedNotifications.clear();
        selectedNotifications.addAll(notifications);
        // update adapter
        notificationAdapter.notifyDataSetChanged();
        if (pullToRefresh.isRefreshing()) pullToRefresh.setRefreshing(false);
    }

    @Override
    public void onStatusChanged() {
        // todo reload notifications
        TimetableData.LoadNotifications("15", this, context);
    }

    public FragmentTimetableNotifications() {
        // Required empty public constructor
    }
}
