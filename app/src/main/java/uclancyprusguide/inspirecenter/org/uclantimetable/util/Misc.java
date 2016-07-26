package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONAuthenticationUser;

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

    public static JSONAuthenticationUser IsUserLoggedIn(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(context.getString(R.string.userId), null);
        JSONAuthenticationUser obj = gson.fromJson(json, JSONAuthenticationUser.class);
        return obj;

    }
}
