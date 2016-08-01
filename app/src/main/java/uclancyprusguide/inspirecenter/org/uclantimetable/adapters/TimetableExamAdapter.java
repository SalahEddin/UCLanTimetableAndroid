package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.Locale;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;

/**
 * @author Salah Eddin Alshaal
 *         22/11/2015.
 */
public class TimetableExamAdapter extends TimetableGenericAdapter {

    public static final String TAG = "uclan-cy";

    private LayoutInflater layoutInflater;

    // Added second constructor to support ArrayLists
    public TimetableExamAdapter(final Context context, final ArrayList<TimetableSession> timetableSessions) {
        super(context, R.layout.timetable_exam_list_item, timetableSessions);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.timetable_exam_list_item, null);
        }

        final TimetableSession ts = getItem(position);

        // binding
        final TextView date = (TextView) view.findViewById(R.id.timetable_exam_list_item_date);
        final TextView time = (TextView) view.findViewById(R.id.timetable_exam_list_item_time);
        final TextView name = (TextView) view.findViewById(R.id.timetable_exam_list_item_name);
        final TextView room = (TextView) view.findViewById(R.id.timetable_exam_list_item_room);

        date.setText(String.format("%s %s", ts.getStartTimeFormatted().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), Misc.getDayOfMonthSuffixed(ts.getStartTimeFormatted().getDayOfMonth())));
        name.setText(String.format("%s - %s", ts.getModuleCode(), ts.getModuleName()));
        room.setText(String.format("Room: %s", ts.getRoomCode()));

        time.setText(Misc.formatStartToEndDate(ts.getStartTimeFormatted(), ts.getEndTimeFormatted()));

        return view;
    }
}