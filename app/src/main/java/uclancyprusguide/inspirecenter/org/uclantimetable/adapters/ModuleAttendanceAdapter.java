package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
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
import uclancyprusguide.inspirecenter.org.uclantimetable.views.FragmentAttendance;

/**
 * Created by salah on 27/12/2016.
 */

public class ModuleAttendanceAdapter extends ArrayAdapter<Attendance> {
    public static final String TAG = "uclan-cy";

    private LayoutInflater layoutInflater;
    private List<Attendance> mList;
    private Context mContext;

    // Added second constructor to support ArrayLists
    public ModuleAttendanceAdapter(final Context context, final ArrayList<Attendance> attendances) {
        super(context, R.layout.attendance_listview_item, attendances);
        mList = attendances;
        mContext = context;
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
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.attendanceProgressBar);

        if (item != null) {
            code.setText(String.valueOf(item.getMODULECODE()) + ": " + item.getATTENDANCEPERCENTAGE().intValue() + "%");
            name.setText(String.valueOf(item.getMODULENAME()));
            absCount.setText(String.format("%s ", String.valueOf(item.getABSENSES())));
            attCount.setText(String.format("%s ", String.valueOf(item.getATTENDANCES())));
            progressBar.setProgress(item.getATTENDANCEPERCENTAGE().intValue());

            int col;

            if (item.getATTENDANCEPERCENTAGE().intValue() < 60) {
                col = mContext.getResources().getColor(R.color.att_poor);
                absCount.setText(String.format("%s", absCount.getText()));

            } else if (item.getATTENDANCEPERCENTAGE().intValue() < 80) {
                col = mContext.getResources().getColor(R.color.att_good);
            } else {
                col = mContext.getResources().getColor(R.color.att_excellent);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBar.setProgressTintList(ColorStateList.valueOf(col));
            } else {
                progressBar.getProgressDrawable().setColorFilter(
                        col, PorterDuff.Mode.SRC_IN);
            }
        }

        return view;
    }
}
