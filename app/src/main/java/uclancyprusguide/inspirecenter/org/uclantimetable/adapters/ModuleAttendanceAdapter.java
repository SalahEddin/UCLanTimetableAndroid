package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.TimetableGenericAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Attendance;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;

/**
 * Created by salah on 27/12/2016.
 */

public class ModuleAttendanceAdapter extends ArrayAdapter<Attendance> {
    public static final String TAG = "uclan-cy";

    private LayoutInflater layoutInflater;
    private List<Attendance> mList;

    // Added second constructor to support ArrayLists
    public ModuleAttendanceAdapter(final Context context, final ArrayList<Attendance> attendances) {
        super(context, R.layout.attendance_listview_item, attendances);
        mList = attendances;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public Attendance getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.attendance_listview_item, null);
        }

        final Attendance item = getItem(position);

        // binding
        final TextView code = (TextView) view.findViewById(R.id.moduleCodeTextView);
        final TextView name = (TextView) view.findViewById(R.id.moduleNameTextView);
        final TextView absCount = (TextView) view.findViewById(R.id.absenceTextView);
        final TextView attCount = (TextView) view.findViewById(R.id.attendedTextView);
        final TextView critical = (TextView) view.findViewById(R.id.criticalMsg);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.attendanceProgressBar);

        if (item != null) {
            code.setText(String.valueOf(item.getMODULECODE()));
            name.setText(String.valueOf(item.getMODULENAME()));
            absCount.setText(String.format("Absent:%s", String.valueOf(item.getABSENSES())));
            attCount.setText(String.format("Attended:%s", String.valueOf(item.getATTENDANCES())));
            progressBar.setProgress(item.getATTENDANCEPERCENTAGE().intValue());
            if (item.getATTENDANCEPERCENTAGE() < 30.0) {
                critical.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }
}
