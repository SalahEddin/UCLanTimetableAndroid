package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringJoiner;

import org.threeten.bp.*;
import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;

/**
 * @author Salah Eddin Alshaal
 *         22/11/2015.
 */
public class TimetableNotificationAdapter extends TimetableGenericAdapter {

    public static final String TAG = "uclan-cy";

    private LayoutInflater layoutInflater;

    // Added second constructor to support ArrayLists
    public TimetableNotificationAdapter(final Context context, final ArrayList<TimetableSession> timetableSessions) {
        super(context, R.layout.timetable_notification_list_item, timetableSessions);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public static final SimpleDateFormat HOURS_AND_MINUTES = new SimpleDateFormat("HH:mm", Locale.US);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.timetable_notification_list_item, null);
        }

        final TimetableSession ts = getItem(position);
        final LocalTime currentTime = LocalTime.now();

        final TextView date = (TextView) view.findViewById(R.id.timetable_notify_list_item_date);
        date.setText(String.format("%s %s%s", ts.getStartTimeFormatted().getMonth().name(), ts.getStartTimeFormatted().getDayOfMonth(), getDayOfMonthSuffix(ts.getStartTimeFormatted().getDayOfMonth())));

        final TextView time = (TextView) view.findViewById(R.id.timetable_notify_list_item_time);
        time.setText("19:00");

        final TextView title = (TextView) view.findViewById(R.id.timetable_notify_list_item_name);
        title.setText(ts.getModuleName());

        final TextView desc = (TextView) view.findViewById(R.id.timetable_notify_list_item_desc);
        desc.setText(ts.getSessionDescription());

        final String linkText = String.format("<a href='%s'>More Details</a>", ts.getLink());
        final TextView link = (TextView) view.findViewById(R.id.timetable_notify_list_item_more_link);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(linkText));

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