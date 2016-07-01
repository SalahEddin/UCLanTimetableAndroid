package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;


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
        return inflater.inflate(R.layout.fragment_timetable_notifications, container, false);
    }
}
