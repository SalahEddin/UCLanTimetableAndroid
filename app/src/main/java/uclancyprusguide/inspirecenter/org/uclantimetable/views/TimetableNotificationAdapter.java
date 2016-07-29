package uclancyprusguide.inspirecenter.org.uclantimetable.views;

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
import java.util.Locale;

import org.threeten.bp.*;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.TextStyle;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;

/**
 * @author Salah Eddin Alshaal
 *         22/11/2015.
 */
public class TimetableNotificationAdapter extends ArrayAdapter<Notification> {

    public static final String TAG = "uclan-cy";

    private LayoutInflater layoutInflater;

    // Added second constructor to support ArrayLists
    public TimetableNotificationAdapter(final Context context, final ArrayList<Notification> notifications) {
        super(context, R.layout.timetable_notification_list_item, notifications);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.timetable_notification_list_item, null);
        }

        final Notification item = getItem(position);
        final LocalDateTime currentTime = LocalDateTime.now();
        final LocalDateTime publishDate = Misc.APITimestampToLocalDate(item.getPUBLISH_DATE());
        // format date
        String dateFormatted = String.format("%s %s", publishDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), Misc.getDayOfMonthSuffixed(publishDate.getDayOfMonth()));
        // format Time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        String timeFormatted = dtf.format(publishDate);


        final TextView date = (TextView) view.findViewById(R.id.timetable_notify_list_item_date);
        date.setText(dateFormatted);

        final TextView time = (TextView) view.findViewById(R.id.timetable_notify_list_item_time);
        time.setText(timeFormatted);

        final TextView title = (TextView) view.findViewById(R.id.timetable_notify_list_item_name);
        title.setText(item.getNOTIFICATION_TITLE());

        final TextView desc = (TextView) view.findViewById(R.id.timetable_notify_list_item_desc);
        desc.setText(item.getNOTIFICATION_TEXT());

        final View ribbon = view.findViewById(R.id.isImportantView);
        if (!item.getNOTIFICATION_TYPE_NAME().toUpperCase().equals("ROOM CHANGE")) {
            ribbon.setVisibility(View.INVISIBLE);
        }
//        final String linkText = String.format("<a href='%s'>More Details</a>", item.getLink());
//        final TextView link = (TextView) view.findViewById(R.id.timetable_notify_list_item_more_link);
//        link.setMovementMethod(LinkMovementMethod.getInstance());
//        link.setText(Html.fromHtml(linkText));

        return view;
    }
}