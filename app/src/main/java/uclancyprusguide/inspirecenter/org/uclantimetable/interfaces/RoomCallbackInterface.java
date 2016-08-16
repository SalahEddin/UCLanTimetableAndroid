package uclancyprusguide.inspirecenter.org.uclantimetable.interfaces;

import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.models.JSONRoom;

/**
 * Created by salah on 26/07/16.
 */
public interface RoomCallbackInterface {
    void onDownload(List<JSONRoom> rooms);
}
