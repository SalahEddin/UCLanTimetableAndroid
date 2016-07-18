package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;


public class FragmentAttendance extends Fragment {

    public FragmentAttendance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi = inflater.inflate(R.layout.fragment_attendance, container, false);

        return vi;
    }
}
