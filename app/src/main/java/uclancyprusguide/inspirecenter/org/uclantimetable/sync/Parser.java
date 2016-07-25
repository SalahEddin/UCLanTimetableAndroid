package uclancyprusguide.inspirecenter.org.uclantimetable.sync;

import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Nearchos Paspallis
 * 22/11/2015
 */
public class Parser {

//    static public String extractJSON(final String xml) {
//        int begin = xml.indexOf('[');
//        int end = xml.indexOf(']') + 1;
//        return xml.substring(begin, end);
//    }
//
//    static public TimetableSession [] getRoomTimetableSessions(final String json)  throws JSONException {
//        final JSONArray allSessions = new JSONArray(json);
//        final TimetableSession [] timetableSessions = new TimetableSession[allSessions.length()];
//        for(int i = 0; i < timetableSessions.length; i++) {
//            timetableSessions[i] = getTimetableSession(allSessions.getJSONObject(i));
//        }
//        return timetableSessions;
//    }
//
//    static public TimetableSession getTimetableSession(final JSONObject session) throws JSONException {
//        return new TimetableSession(
//                session.getString("MODULE_CODE"),
//                session.getString("MODULE_NAME"),
//                session.getString("ROOM_CODE"),
//                session.getString("START_TIME_FORMATTED"),
//                session.getString("END_TIME_FORMATTED"),
//                session.getString("DAY_OF_WEEK"),
//                session.getInt("DURATION"),
//                session.getString("LECTURER_NAME"),
//                session.getString("SESSION_DESCRIPTION"));
//    }
}