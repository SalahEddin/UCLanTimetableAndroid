package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import java.util.ArrayList;

import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;

/**
 * Created by salah on 06/07/16.
 */
public class TimetableData {
    public enum TimetableEventsType{
        ALL, EXAMS, NOTIFICATIONS
    }

    public ArrayList<TimetableSession> LoadEvents(TimetableEventsType eventType){
        // todo check login first

        ArrayList<TimetableSession> entries = new ArrayList<>();
        switch (eventType){
            case ALL:
                break;
            case EXAMS:
                break;
            case NOTIFICATIONS:
                break;
            default:
                break;
        }
        return entries;
    }

}
