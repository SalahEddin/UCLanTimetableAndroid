package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.app.Activity;
import android.content.Context;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by salah on 22/07/16.
 * Includes misc static methods
 */
public class Misc {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String DateToAPIFormat(LocalDate date) {
        final DateTimeFormatter simpleDateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
        return simpleDateFormat.format(date);
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
}
