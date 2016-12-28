package uclancyprusguide.inspirecenter.org.uclantimetable.interfaces;

import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.models.Attendance;

/**
 * Created by salah on 27/12/2016.
 */

public interface AttendanceCallbackInterface {

    void onAvgDownloaded(Attendance attendance);

    void onDetailedDownloaded(List<Attendance> attendance);
}
