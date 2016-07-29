package uclancyprusguide.inspirecenter.org.uclantimetable.interfaces;

import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;

/**
 * Created by salah on 29/07/16.
 */
public interface MyNotificationCallbackInterface {
    void onNotificationDownloadFinished(List<Notification> notifications);

    void onStatusChanged();
//    void onMarkedAsRead(Notification item);
//
//    void onMarkedAsUnread(Notification item);
//
//    void onMarkedAsArchived(Notification item);
//
//    void onMarkedAsUnarchived(Notification item);
//
//    void onMarkedAsDeleted(Notification item);
}
