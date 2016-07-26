package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONRoom;

/**
 * Created by salah on 26/07/16.
 */
public interface MyRoomCallbackInterface {
    void onRoomDownloadFinished(List<JSONRoom> rooms);
}
