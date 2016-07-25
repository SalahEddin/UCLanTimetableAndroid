package uclancyprusguide.inspirecenter.org.uclantimetable.data;

import android.support.annotation.NonNull;

import org.threeten.bp.LocalDateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nearchos Paspallis
 *         22/11/2015.
 */
public class TimetableSession implements Serializable {
    private String moduleCode;
    private String moduleName;
    private String roomCode;
    private LocalDateTime startTimeFormatted;
    private LocalDateTime endTimeFormatted;
    private String dayOfWeek;
    private int duration;
    private String lecturerName;
    private String sessionDescription;
    private String link;

    // used for session
    public TimetableSession(String moduleCode, String moduleName, String roomCode, LocalDateTime startTimeFormatted, LocalDateTime endTimeFormatted, String dayOfWeek, int duration, String lecturerName, String sessionDescription) {
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

    //used for notification
    public TimetableSession(String title, String description, String link, Date date) {
        this.moduleName = title;
        this.sessionDescription = description;
        this.link = link;
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


    public String getLink() {
        return link;
    }

    public LocalDateTime getStartTimeFormatted() {
        return startTimeFormatted;
    }

    public LocalDateTime getEndTimeFormatted() {
        return endTimeFormatted;
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