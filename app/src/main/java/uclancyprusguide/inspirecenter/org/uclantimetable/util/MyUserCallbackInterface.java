package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import java.util.ArrayList;

import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONAuthenticationUser;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;

/**
 * Created by salah on 26/07/16.
 */
public interface MyUserCallbackInterface {
    void onUserDownloadFinished(JSONAuthenticationUser user);
}
