package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;

/**
 * @author Salah Eddin Alshaal
 *         22/11/2015.
 */
public class TimetableExamAdapter extends ArrayAdapter<TimetableSession> {

    public static final String TAG = "uclan-cy";

    private LayoutInflater layoutInflater;

    // Added second constructor to support ArrayLists
    public TimetableExamAdapter(final Context context, final ArrayList<TimetableSession> timetableSessions) {
        super(context, R.layout.timetable_exam_list_item, timetableSessions);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public TimetableExamAdapter(final Context context, final TimetableSession[] timetableSessions) {
        super(context, R.layout.timetable_exam_list_item, timetableSessions);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public static final SimpleDateFormat HOURS_AND_MINUTES = new SimpleDateFormat("HH:mm", Locale.US);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.timetable_exam_list_item, null);
        }

        final TimetableSession timetableSession = getItem(position);
        final String currentTime = HOURS_AND_MINUTES.format(new Date());
        boolean pastEvent = currentTime.compareTo(timetableSession.getStartTimeFormatted()) > 0;

        Calendar cal = Calendar.getInstance();
        cal.setTime(timetableSession.getStartDate());

        final TextView date = (TextView) view.findViewById(R.id.timetable_exam_list_item_date);
        date.setText(String.format("%s %s%s", getMonthForInt(cal.MONTH), cal.DAY_OF_MONTH, getDayOfMonthSuffix(cal.DAY_OF_MONTH)));
        // date.setTextColor(pastEvent ? getContext().getResources().getColor(R.color.gray) : getContext().getResources().getColor(R.color.colorPrimaryDark));

        final TextView time = (TextView) view.findViewById(R.id.timetable_exam_list_item_time);
        time.setText(String.format("%s -%5s", timetableSession.getStartTimeFormatted().trim(), timetableSession.getEndTimeFormatted()));
        //time.setTextColor(pastEvent ? getContext().getResources().getColor(R.color.gray) : getContext().getResources().getColor(R.color.colorPrimaryDark));

        final TextView name = (TextView) view.findViewById(R.id.timetable_exam_list_item_name);
        name.setText(String.format("%s - %s", timetableSession.getModuleName(), timetableSession.getModuleCode()));
        //name.setTypeface(null, pastEvent ? Typeface.NORMAL : Typeface.BOLD);

        final TextView room = (TextView) view.findViewById(R.id.timetable_exam_list_item_room);
        room.setText(String.format("Room: %s", timetableSession.getRoomCode()));

        return view;
    }

    static String getDayOfMonthSuffix(final int n) {
        if (n >= 1 && n <= 31) return "";

        if (n >= 11 && n <= 13) {
            return "th";
        }

        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    static String getMonthForInt(int month) {
        //String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }
}