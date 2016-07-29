package uclancyprusguide.inspirecenter.org.uclantimetable.models;

import java.io.Serializable;

/**
 * Created by salah on 14/07/16.
 */
public class END_TIME implements Serializable {
    private String TotalMinutes;

    private String TotalHours;

    private String TotalDays;

    private String TotalSeconds;

    private String Seconds;

    private String Hours;

    private String Days;

    private String TotalMilliseconds;

    private String Ticks;

    private String Minutes;

    private String Milliseconds;

    public String getTotalMinutes() {
        return TotalMinutes;
    }

    public void setTotalMinutes(String TotalMinutes) {
        this.TotalMinutes = TotalMinutes;
    }

    public String getTotalHours() {
        return TotalHours;
    }

    public void setTotalHours(String TotalHours) {
        this.TotalHours = TotalHours;
    }

    public String getTotalDays() {
        return TotalDays;
    }

    public void setTotalDays(String TotalDays) {
        this.TotalDays = TotalDays;
    }

    public String getTotalSeconds() {
        return TotalSeconds;
    }

    public void setTotalSeconds(String TotalSeconds) {
        this.TotalSeconds = TotalSeconds;
    }

    public String getSeconds() {
        return Seconds;
    }

    public void setSeconds(String Seconds) {
        this.Seconds = Seconds;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String Hours) {
        this.Hours = Hours;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String Days) {
        this.Days = Days;
    }

    public String getTotalMilliseconds() {
        return TotalMilliseconds;
    }

    public void setTotalMilliseconds(String TotalMilliseconds) {
        this.TotalMilliseconds = TotalMilliseconds;
    }

    public String getTicks() {
        return Ticks;
    }

    public void setTicks(String Ticks) {
        this.Ticks = Ticks;
    }

    public String getMinutes() {
        return Minutes;
    }

    public void setMinutes(String Minutes) {
        this.Minutes = Minutes;
    }

    public String getMilliseconds() {
        return Milliseconds;
    }

    public void setMilliseconds(String Milliseconds) {
        this.Milliseconds = Milliseconds;
    }

    @Override
    public String toString() {
        return "ClassPojo [TotalMinutes = " + TotalMinutes + ", TotalHours = " + TotalHours + ", TotalDays = " + TotalDays + ", TotalSeconds = " + TotalSeconds + ", Seconds = " + Seconds + ", Hours = " + Hours + ", Days = " + Days + ", TotalMilliseconds = " + TotalMilliseconds + ", Ticks = " + Ticks + ", Minutes = " + Minutes + ", Milliseconds = " + Milliseconds + "]";
    }
}
