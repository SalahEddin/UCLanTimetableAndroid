package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.Locale;

import org.threeten.bp.*;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.TextStyle;


import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.NotificationsCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;

/**
 * @author Salah Eddin Alshaal
 *         22/11/2015.
 */
public class TimetableNotificationAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private NotificationsCallbackInterface callbackInterface;
    private ArrayList<Notification> mNotificationArrayList;

    public TimetableNotificationAdapter(Context mContext, ArrayList<Notification> dataset, NotificationsCallbackInterface initCallbackInterface) {
        this.mContext = mContext;
        this.mNotificationArrayList = dataset;
        this.callbackInterface = initCallbackInterface;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.timetable_notification_list_item, null);

        // cell content
        final Notification item = mNotificationArrayList.get(position);

        final LocalDateTime publishDate = Misc.APITimestampToLocalDate(item.getPUBLISH_DATE());
        // format date
        String dateFormatted = String.format("%s %s", publishDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), Misc.getDayOfMonthSuffixed(publishDate.getDayOfMonth()));
        // format Time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        String timeFormatted = dtf.format(publishDate);

        // UI Binding
        final TextView date = (TextView) v.findViewById(R.id.timetable_notify_list_item_date);
        date.setText(dateFormatted);

        final TextView time = (TextView) v.findViewById(R.id.timetable_notify_list_item_time);
        time.setText(timeFormatted);

        final TextView title = (TextView) v.findViewById(R.id.timetable_notify_list_item_name);
        title.setText(item.getNOTIFICATION_TITLE());

        final TextView desc = (TextView) v.findViewById(R.id.timetable_notify_list_item_desc);
        desc.setText(item.getNOTIFICATION_TYPE_NAME());

        final View ribbon = v.findViewById(R.id.isImportantView);
        // TODO: 29/07/16 change types of importance
        if (isImportant(item.getIMPORTANT())) {
            ribbon.setVisibility(View.VISIBLE);
        } else {
            ribbon.setVisibility(View.INVISIBLE);
        }

        if (item.isRead() || item.isArchived()) {
            date.setTextColor(mContext.getResources().getColor(R.color.light_gray));
            date.setTypeface(null, Typeface.NORMAL);
            time.setTextColor(mContext.getResources().getColor(R.color.light_gray));
            title.setTypeface(null, Typeface.NORMAL);
            desc.setTextColor(mContext.getResources().getColor(R.color.light_gray));
        }

        // id of notification
        final int ParsedNotifId = Integer.parseInt(item.getNOTIFICATION_ID());

        /////////////
        // swipe code
        SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));

        final View left_wrapper = swipeLayout.findViewById(R.id.left_wrapper);
        final View right_wrapper = swipeLayout.findViewById(R.id.right_wrapper);

        // delete button on left swipe
        final View deleteButton = v.findViewById(R.id.delete);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, left_wrapper);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Misc.IsOnline(mContext)) {
                    TimetableData.ChangeNotifStatus(TimetableData.NotifStatus.DELETED, ParsedNotifId, callbackInterface, mContext);
                    Toast.makeText(mContext, "Notification Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.network_connection_required, Toast.LENGTH_SHORT).show();
                }
            }
        });
        // right swipe
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, right_wrapper);

        final View markWrapperView = right_wrapper.findViewById(R.id.right_read_wrapper);
        final View archiveWrapperView = swipeLayout.findViewById(R.id.right_archive_wrapper);
        // TODO: 01/08/16 optimise (not process is read to begin with)
        // mark as View
        final TextView markTextView = (TextView) markWrapperView.findViewById(R.id.readText);
        final ImageButton markImageButton = (ImageButton) markWrapperView.findViewById(R.id.readBtn);
        if (item.isRead()) {
            // change option to Mark as unread
            markTextView.setText("mark as unread");
            markImageButton.setImageResource(R.drawable.mark_unread);
        } else {
            markTextView.setText("mark as read");
            markImageButton.setImageResource(R.drawable.mark_read);
        }
        //Archive View
        final TextView archiveTextView = (TextView) archiveWrapperView.findViewById(R.id.archiveText);
        final ImageButton archiveImageButton = (ImageButton) archiveWrapperView.findViewById(R.id.archiveBtn);

        if (item.isArchived()) {
            // hide mark as view
            markWrapperView.setVisibility(View.GONE);
            archiveTextView.setText("Unarchive");
            archiveImageButton.setImageResource(R.drawable.unarchive);
        } else {
            archiveTextView.setText("Archive");
            archiveImageButton.setImageResource(R.drawable.archive);
        }
        // setting listeners
        View.OnClickListener markListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update read status
                if (Misc.IsOnline(mContext)) {
                    TimetableData.ChangeNotifStatus(item.isRead() ? TimetableData.NotifStatus.UNREAD : TimetableData.NotifStatus.READ,
                            ParsedNotifId, callbackInterface, mContext);
                    Toast.makeText(mContext, item.isRead() ? "Notification marked as unread" : "Notification marked as read", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.network_connection_required, Toast.LENGTH_SHORT).show();
                }
            }
        };
        markWrapperView.setOnClickListener(markListener);
        markImageButton.setOnClickListener(markListener);

        View.OnClickListener archiveListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Misc.IsOnline(mContext)) {
                    TimetableData.ChangeNotifStatus(item.isArchived() ? TimetableData.NotifStatus.UNARCHIVED : TimetableData.NotifStatus.ARCHIVED, ParsedNotifId, callbackInterface, mContext);
                    Toast.makeText(mContext, item.isArchived() ? "Notification unarchived" : "Notification archived", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.network_connection_required, Toast.LENGTH_SHORT).show();
                }
            }
        };
        archiveWrapperView.setOnClickListener(archiveListener);
        archiveImageButton.setOnClickListener(archiveListener);

        return v;
    }

    public static boolean isImportant(String type) {
        return type.equals("1");
    }

    @Override
    public void fillValues(int position, View convertView) {
    }

    @Override
    public int getCount() {
        return mNotificationArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}