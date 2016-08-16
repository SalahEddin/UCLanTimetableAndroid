package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.TextStyle;

import java.util.List;
import java.util.Locale;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.NotificationsCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;

public class DetailedNotificationActivity extends AppCompatActivity implements NotificationsCallbackInterface {

    private Notification notification;
    private ImageButton archiveBtn, deleteBtn, markBtn;
    private TextView archiveText, markText;

    int parsedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_notification);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get notification
        Intent myIntent = getIntent(); // gets the previously created intent
        notification = (Notification) myIntent.getSerializableExtra(this.getString(R.string.notificationKey)); // will return "notification"

        if (notification == null) {
            Toast.makeText(this, "sorry, can't show this notification at the moment", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (Misc.IsOnline(this)) {
            // mark as read
            parsedId = Integer.parseInt(notification.getNOTIFICATION_ID());
            TimetableData.ChangeNotifStatus(TimetableData.NotifStatus.READ, parsedId, this, this);
            notification.setNOTIFICATION_STATUS("1");
        }

        // UI Binding
        TextView title = (TextView) findViewById(R.id.title_textView);
        TextView type = (TextView) findViewById(R.id.type_textView);
        TextView date = (TextView) findViewById(R.id.publish_date_textView);
        TextView desc = (TextView) findViewById(R.id.description_textView);
        TextView url = (TextView) findViewById(R.id.url);
        archiveText = (TextView) findViewById(R.id.archiveText);
        archiveBtn = (ImageButton) findViewById(R.id.archiveBtn);
        markText = (TextView) findViewById(R.id.markText);
        markBtn = (ImageButton) findViewById(R.id.markBtn);
        deleteBtn = (ImageButton) findViewById(R.id.deleteBtn);

        // UI setup
        title.setText(notification.getNOTIFICATION_TITLE());
        final LocalDateTime publishDate = Misc.APITimestampToLocalDate(notification.getPUBLISH_DATE());

        // format date
        String dateFormatted = String.format("Publish Date: %s %s, %s",
                publishDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                Misc.getDayOfMonthSuffixed(publishDate.getDayOfMonth()),
                publishDate.getYear());
        // format Time
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
//        String timeFormatted = dtf.format(publishDate);
        date.setText(dateFormatted);
        type.setText(notification.getNOTIFICATION_TYPE_NAME());
        desc.setText(notification.getNOTIFICATION_TEXT());

        if (notification.getNOTIFICATION_URL() != null && !notification.getNOTIFICATION_URL().equals("")) {
            //url.setText(notification.getNOTIFICATION_URL());
        }

        //listeners
        archiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Misc.IsOnline(DetailedNotificationActivity.this)) {
                    changeNotificationStatus(notification.isArchived() ? TimetableData.NotifStatus.UNARCHIVED : TimetableData.NotifStatus.UNARCHIVED);
                    Toast.makeText(getBaseContext(), "Notification Archived", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DetailedNotificationActivity.this, R.string.network_connection_required, Toast.LENGTH_SHORT).show();
                }
            }
        });

        markBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Misc.IsOnline(DetailedNotificationActivity.this)) {
                    changeNotificationStatus(notification.isRead() ? TimetableData.NotifStatus.UNREAD : TimetableData.NotifStatus.READ);
                    Toast.makeText(getBaseContext(), "Notification Unread", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DetailedNotificationActivity.this, R.string.network_connection_required, Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Misc.IsOnline(DetailedNotificationActivity.this)) {
                    changeNotificationStatus(TimetableData.NotifStatus.DELETED);
                    Toast.makeText(getBaseContext(), "Notification Deleted", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(DetailedNotificationActivity.this, R.string.network_connection_required, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void handleRenaming() {
        if (notification.isDeleted()) {
            finish();
        } else {
            archiveText.setText(notification.isArchived() ? "Unarchive" : "Archive");
            archiveBtn.setImageResource(notification.isArchived() ? R.drawable.unarchive : R.drawable.archive);
            markText.setText(notification.isRead() ? "Mark as unread" : "Mark as read");
            markBtn.setImageResource(notification.isRead() ? R.drawable.mark_unread : R.drawable.mark_read);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void changeNotificationStatus(TimetableData.NotifStatus status) {
        TimetableData.ChangeNotifStatus(status, parsedId, this, this);
    }

    @Override
    public void onSingleDownloaded(Notification item) {
        this.notification = item;
        handleRenaming();
    }

    @Override
    public void onAllDownloaded(List<Notification> notifications) {

    }

    @Override
    public void onStatusChanged() {
        TimetableData.GetNotificationById(notification.getNOTIFICATION_ID(), this, this);
    }
}
