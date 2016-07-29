package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.sql.Timestamp;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.User;

/**
 * Created by salah on 22/07/16.
 * Includes misc static methods
 */
public class Misc {

    public static boolean IsOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static LocalDateTime APITimestampToLocalDate(String strDate) {
        // remove parantheses
        String requiredString = strDate.substring(strDate.indexOf("(") + 1, strDate.indexOf(")"));

        //convert unix epoch timestamp (seconds) to milliseconds
        Long longTS = Long.parseLong(requiredString);
        Timestamp timestamp = new Timestamp(longTS);
        LocalDateTime dateTime = DateTimeUtils.toLocalDateTime(timestamp);
        return dateTime;
    }

    public static String DateToAPIFormat(LocalDate date) {
        final DateTimeFormatter simpleDateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
        return simpleDateFormat.format(date);
    }

    public static LocalDate APIFormatToLocalDate(String date) {
        final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDate.parse(date, dtf);
    }

    public static LocalDateTime parseHoursMinsDate(String hour, String min, String date) {
        LocalDateTime parsedDate = null;
        String minFixed = (min.length() == 1) ? "0" + min : min;
        String hourFixed = (hour.length() == 1) ? "0" + hour : hour;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String timeString = String.format("%s:%s %s", hourFixed, minFixed, date);
        parsedDate = LocalDateTime.parse(timeString, dtf);

        return parsedDate;
    }

    public static String formatStartToEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        String formattedDate1 = null;
        String formattedDate2 = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        formattedDate1 = dtf.format(startDate);
        formattedDate2 = dtf.format(endDate);
        return String.format("%s - %s", formattedDate1, formattedDate2);
    }

    public static User IsUserLoggedIn(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(context.getString(R.string.userId), null);
        User obj = gson.fromJson(json, User.class);
        return obj;

    }

    // adds st, nd, rd or th
    public static String getDayOfMonthSuffixed(final int n) {
        if (n <= 1 && n >= 31) return n + "";

        if (n >= 11 && n <= 13) {
            return n + "th";
        }

        switch (n % 10) {
            case 1:
                return n + "st";
            case 2:
                return n + "nd";
            case 3:
                return n + "rd";
            default:
                return n + "th";
        }
    }

    public static String getMonthForInt(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        //String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }
}
