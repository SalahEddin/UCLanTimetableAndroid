package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;

import java.util.ArrayList;

import org.threeten.bp.*;

/**
 * @author Nearchos Paspallis
 *         22/11/2015.
 */
public class TimetableSessionAdapter extends TimetableGenericAdapter {

    public static final String TAG = "uclan-cy";

    private LayoutInflater layoutInflater;

    // Added second constructor to support ArrayLists
    public TimetableSessionAdapter(final Context context, final ArrayList<TimetableSession> timetableSessions) {
        super(context, R.layout.timetable_session_list_item, timetableSessions);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.timetable_session_list_item, null);
        }

        final TimetableSession ts = getItem(position);
        final LocalDateTime currentTime = LocalDateTime.now();
        boolean pastEvent = currentTime.isAfter(ts.getEndTimeFormatted());

        final TextView time = (TextView) view.findViewById(R.id.timetable_session_list_item_time);
        time.setText(Misc.formatStartToEndDate(ts.getStartTimeFormatted(), ts.getEndTimeFormatted()));

        final TextView name = (TextView) view.findViewById(R.id.timetable_session_list_item_name);
        name.setText(String.format("%s - %s", ts.getModuleCode(), ts.getModuleName()));
        name.setTypeface(null, pastEvent ? Typeface.NORMAL : Typeface.BOLD);
        if (ts.getModuleName().equals("N/A") || ts.getModuleName().equals("") || ts.getModuleName() == null) {
            // use description
            name.setText(ts.getSessionDescription());
        }

        final TextView description = (TextView) view.findViewById(R.id.timetable_session_list_item_description);
        description.setText(getContext().getString(R.string.session_type_with_lecturer, ts.getSessionDescription(), ts.getLecturerName()));
        // description.setTypeface(null, pastEvent ? Typeface.NORMAL : Typeface.BOLD);

        final TextView room = (TextView) view.findViewById(R.id.timetable_session_list_item_room);
        room.setText(ts.getRoomCode());

        if (pastEvent) {
            description.setTextColor(getContext().getResources().getColor(R.color.light_gray));
            name.setTypeface(null, Typeface.NORMAL);
            time.setTextColor(getContext().getResources().getColor(R.color.light_gray));
            name.setTextColor(getContext().getResources().getColor(R.color.light_gray));
            room.setTextColor(getContext().getResources().getColor(R.color.light_gray));
        }

        return view;
    }
}