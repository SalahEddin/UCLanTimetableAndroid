package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

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
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;
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
        final LocalDateTime currentTime = LocalDateTime.now();
        boolean pastEvent = currentTime.isAfter(ts.getEndTimeFormatted());

        // binding
        final TextView date = (TextView) view.findViewById(R.id.timetable_exam_list_item_date);
        final TextView time = (TextView) view.findViewById(R.id.timetable_exam_list_item_time);
        final TextView name = (TextView) view.findViewById(R.id.timetable_exam_list_item_name);
        final TextView room = (TextView) view.findViewById(R.id.timetable_exam_list_item_room);

        date.setText(String.format("%s %s", ts.getStartTimeFormatted().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), getDayOfMonthSuffixed(ts.getStartTimeFormatted().getDayOfMonth())));
        name.setText(String.format("%s - %s", ts.getModuleName(), ts.getModuleCode()));
        room.setText(String.format("Room: %s", ts.getRoomCode()));

        time.setText(Misc.formatStartToEndDate(ts.getStartTimeFormatted(), ts.getEndTimeFormatted()));

        if (pastEvent) {
            date.setTextColor(getContext().getResources().getColor(R.color.light_gray));
            date.setTypeface(null, Typeface.NORMAL);
            time.setTextColor(getContext().getResources().getColor(R.color.light_gray));
            name.setTextColor(getContext().getResources().getColor(R.color.light_gray));
            room.setTextColor(getContext().getResources().getColor(R.color.light_gray));
        }

        return view;
    }

    // adds st, nd, rd or th
    static String getDayOfMonthSuffixed(final int n) {
        if (n <= 1 && n >= 31) return n + "";

        if (n >= 11 && n <= 13) {
            return n + "th";
        }

        switch (n % 10) {
            case 1:
                return n + "st";
            case 2:
                return n + "nd";
            case 3:
                return n + "rd";
            default:
                return n + "th";
        }
    }

    static String getMonthForInt(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        //String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }
}