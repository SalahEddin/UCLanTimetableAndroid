package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author Nearchos Paspallis
 * 22/11/2015.
 */
public class TimetableSessionAdapter extends TimetableGenericAdapter {

    public static final String TAG = "uclan-cy";

    private LayoutInflater layoutInflater;

    // Added second constructor to support ArrayLists
    public TimetableSessionAdapter(final Context context, final ArrayList<TimetableSession> timetableSessions) {
        super(context, R.layout.timetable_session_list_item, timetableSessions);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public static final SimpleDateFormat HOURS_AND_MINUTES = new SimpleDateFormat("HH:mm", Locale.US);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.timetable_session_list_item, null);
        }

        final TimetableSession timetableSession = getItem(position);
        final String currentTime = HOURS_AND_MINUTES.format(new Date());
        boolean pastEvent = currentTime.compareTo(timetableSession.getStartTimeFormatted()) > 0;

        final TextView time = (TextView) view.findViewById(R.id.timetable_session_list_item_time);
        time.setText(String.format("%s -%5s", timetableSession.getStartTimeFormatted().trim(), timetableSession.getEndTimeFormatted()));
        time.setTextColor(pastEvent ? getContext().getResources().getColor(R.color.gray) : getContext().getResources().getColor(R.color.colorPrimaryDark));
        // time.setTypeface(null, pastEvent ? Typeface.NORMAL : Typeface.BOLD);

        final TextView name = (TextView) view.findViewById(R.id.timetable_session_list_item_name);
        name.setText(String.format("%s - %s", timetableSession.getModuleName(), timetableSession.getModuleCode()));
        name.setTypeface(null, pastEvent ? Typeface.NORMAL : Typeface.BOLD);

        final TextView description = (TextView) view.findViewById(R.id.timetable_session_list_item_description);
        description.setText(getContext().getString(R.string.session_type_with_lecturer, timetableSession.getSessionDescription(), timetableSession.getLecturerName()));
        // description.setTypeface(null, pastEvent ? Typeface.NORMAL : Typeface.BOLD);

        final TextView room = (TextView) view.findViewById(R.id.timetable_session_list_item_room);
        room.setText(timetableSession.getRoomCode());

        return view;
    }
}