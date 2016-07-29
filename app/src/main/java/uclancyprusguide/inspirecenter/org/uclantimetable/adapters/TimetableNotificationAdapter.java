package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.MyNotificationCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;

/**
 * @author Salah Eddin Alshaal
 *         22/11/2015.
 */
public class TimetableNotificationAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private MyNotificationCallbackInterface callbackInterface;
    private ArrayList<Notification> mNotificationArrayList;

    public TimetableNotificationAdapter(Context mContext, ArrayList<Notification> dataset, MyNotificationCallbackInterface initCallbackInterface) {
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

        final LocalDateTime currentTime = LocalDateTime.now();
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
        desc.setText(item.getNOTIFICATION_TEXT());

        final View ribbon = v.findViewById(R.id.isImportantView);
        // TODO: 29/07/16 change types of importance
        if (isImportant(item.getNOTIFICATION_STATUS())) {
            ribbon.setVisibility(View.INVISIBLE);
        }

        if (item.isRead() || item.isArchived()) {
            date.setTextColor(mContext.getResources().getColor(R.color.light_gray));
            date.setTypeface(null, Typeface.NORMAL);
            time.setTextColor(mContext.getResources().getColor(R.color.light_gray));
            title.setTypeface(null, Typeface.NORMAL);
//            title.setTextColor(mContext.getResources().getColor(R.color.light_gray));
            desc.setTextColor(mContext.getResources().getColor(R.color.light_gray));
        }

        final int ParsedNotifId = Integer.parseInt(item.getNOTIFICATION_ID());
        // swipe code
        SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));

        // delete button
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.left_wrapper));
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
            }
        });

        if (!item.isDeleted()) {
            if (!item.isArchived()) {
                // the root wrapper linear layout
                View rightSwipeView = swipeLayout.findViewById(R.id.right_wrapper);
                swipeLayout.addDrag(SwipeLayout.DragEdge.Right, rightSwipeView);
                View markWrapperView = rightSwipeView.findViewById(R.id.right_read_wrapper);
                if (item.isRead()) {
                    // change option to Mark as unread
                    TextView markTextView = (TextView) markWrapperView.findViewById(R.id.readText);
                    ImageButton markImageView = (ImageButton) markWrapperView.findViewById(R.id.readBtn);
                    markTextView.setText("mark as unread");
                    markImageView.setImageResource(R.drawable.mark_unread);
                }
                // mark listener
                markWrapperView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // update read status
                        TimetableData.ChangeNotifStatus(item.isRead() ? TimetableData.NotifStatus.UNREAD : TimetableData.NotifStatus.READ,
                                ParsedNotifId, callbackInterface, mContext);
                        Toast.makeText(mContext, "click Mark as", Toast.LENGTH_SHORT).show();
                    }
                });
                //archive listener
                (rightSwipeView.findViewById(R.id.right_archive_wrapper)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimetableData.ChangeNotifStatus(TimetableData.NotifStatus.ARCHIVED, ParsedNotifId, callbackInterface, mContext);
                        Toast.makeText(mContext, "click Mark as", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                // archived, only unarchive option
                View rightSwipeView = swipeLayout.findViewById(R.id.right_archive_wrapper);
                ((TextView) rightSwipeView.findViewById(R.id.archiveText)).setText("unarchive");
                ((ImageButton) rightSwipeView.findViewById(R.id.archiveBtn)).setImageResource(R.drawable.unarchive);
                swipeLayout.addDrag(SwipeLayout.DragEdge.Right, rightSwipeView);
                rightSwipeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimetableData.ChangeNotifStatus(TimetableData.NotifStatus.UNARCHIVED, ParsedNotifId, callbackInterface, mContext);
                        Toast.makeText(mContext, "Unarchived", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

//        // mark
//        v.findViewById(R.id.right_archive_wrapper).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "click archive", Toast.LENGTH_SHORT).show();
//            }
//        });
//        // archive
//        v.findViewById(R.id.right_read_wrapper).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "click read", Toast.LENGTH_SHORT).show();
//            }
//        });
        return v;
    }

    public static boolean isImportant(String type) {
        return !type.toUpperCase().equals("ROOM CHANGE");
    }

    @Override
    public void fillValues(int position, View convertView) {
        // TextView t = (TextView)convertView.findViewById(R.id.position);
        //t.setText((position + 1) + ".");
    }

    @Override
    public int getCount() {
        int count = 0;
        for (int i = 0; i < mNotificationArrayList.size(); ++i) {
            if (!mNotificationArrayList.get(i).isDeleted()) count++;
        }
        return count;
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