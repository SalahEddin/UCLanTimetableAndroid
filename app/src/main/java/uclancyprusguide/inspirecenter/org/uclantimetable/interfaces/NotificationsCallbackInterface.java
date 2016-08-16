package uclancyprusguide.inspirecenter.org.uclantimetable.interfaces;

import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;

/**
 * Created by salah on 29/07/16.
 */
public interface NotificationsCallbackInterface {
    void onAllDownloaded(List<Notification> notifications);

    void onSingleDownloaded(Notification notifications);

    void onStatusChanged();
}
