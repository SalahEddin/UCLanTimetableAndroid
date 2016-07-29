package uclancyprusguide.inspirecenter.org.uclantimetable.views;

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

        final TimetableSession timetableSession = getItem(position);
        final LocalDateTime currentTime = LocalDateTime.now();
        boolean pastEvent = currentTime.isAfter(timetableSession.getEndTimeFormatted());

        final TextView time = (TextView) view.findViewById(R.id.timetable_session_list_item_time);
        time.setText(Misc.formatStartToEndDate(timetableSession.getStartTimeFormatted(), timetableSession.getEndTimeFormatted()));

        final TextView name = (TextView) view.findViewById(R.id.timetable_session_list_item_name);
        name.setText(String.format("%s - %s", timetableSession.getModuleName(), timetableSession.getModuleCode()));
        name.setTypeface(null, pastEvent ? Typeface.NORMAL : Typeface.BOLD);

        final TextView description = (TextView) view.findViewById(R.id.timetable_session_list_item_description);
        description.setText(getContext().getString(R.string.session_type_with_lecturer, timetableSession.getSessionDescription(), timetableSession.getLecturerName()));
        // description.setTypeface(null, pastEvent ? Typeface.NORMAL : Typeface.BOLD);

        final TextView room = (TextView) view.findViewById(R.id.timetable_session_list_item_room);
        room.setText(timetableSession.getRoomCode());

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