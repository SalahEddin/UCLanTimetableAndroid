package uclancyprusguide.inspirecenter.org.uclantimetable.data;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * @author Nearchos Paspallis
 * 22/11/2015.
 */
public class TimetableSession implements Serializable, Comparable {
    private String moduleCode;
    private String moduleName;
    private String roomCode;
    private String startTimeFormatted;
    private String endTimeFormatted;
    private String dayOfWeek;
    private int duration;
    private String lecturerName;
    private String sessionDescription;

    public TimetableSession(String moduleCode, String moduleName, String roomCode, String startTimeFormatted, String endTimeFormatted, String dayOfWeek, int duration, String lecturerName, String sessionDescription) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.roomCode = roomCode;
        this.startTimeFormatted = startTimeFormatted;
        this.endTimeFormatted = endTimeFormatted;
        this.dayOfWeek = dayOfWeek;
        this.duration = duration;
        this.lecturerName = lecturerName;
        this.sessionDescription = sessionDescription;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getStartTimeFormatted() {
        return startTimeFormatted;
    }

    public String getEndTimeFormatted() {
        return endTimeFormatted;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDuration() {
        return duration;
    }

    public String getLecturerName() {
        return lecturerName == null || lecturerName.isEmpty() ? "N/A" : lecturerName;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    @Override
    public int compareTo(@NonNull Object anotherTimetableSession) {
        return startTimeFormatted.compareTo(((TimetableSession) anotherTimetableSession).startTimeFormatted);
    }

    @Override
    public String toString() {
        return "TimetableSession{" +
                "moduleCode='" + moduleCode + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", roomCode='" + roomCode + '\'' +
                ", startTimeFormatted='" + startTimeFormatted + '\'' +
                ", endTimeFormatted='" + endTimeFormatted + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", duration=" + duration +
                ", lecturerName='" + lecturerName + '\'' +
                ", sessionDescription='" + sessionDescription + '\'' +
                '}';
    }
}