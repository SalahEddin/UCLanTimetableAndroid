package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.NotificationsCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.RoomCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.UserCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.User;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.JSONEvent;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.JSONRoom;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.api.TimetableSystemAPI;

import org.threeten.bp.*;

/**
 * Created by salah on 06/07/16.
 */
public class TimetableData {

    //define callback interface
    public interface MyCallbackInterface {
        void onDownloadFinished(ArrayList<TimetableSession> result);
    }

    public enum TimetableType {
        STUDENT, ROOM, LECTURER, EXAM
    }

    public enum NotifStatus {
        READ, UNREAD, ARCHIVED, UNARCHIVED, DELETED
    }

    public static void ChangeNotifStatus(NotifStatus newStatus, int id, final NotificationsCallbackInterface callback, final Context context) {
        TimetableSystemAPI api = getDefaultUCLanAPI();
        int statusCode = getStatusInt(newStatus);

        Call<Void> getCall = api.updateNotificationStatus(TimetableSystemAPI.SECURITY_TOKEN, id, statusCode);
        getCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Handler mainHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            callback.onStatusChanged();
                        }
                    };
                    mainHandler.post(myRunnable);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private static int getStatusInt(NotifStatus status) {
        switch (status) {
            case UNARCHIVED:
                return 1;
            case READ:
                return 1;
            case UNREAD:
                return 0;
            case ARCHIVED:
                return 4;
            case DELETED:
                return 2;
            default:
                return 0;
        }
    }

    public static void LoadNotifications(String user_id, final NotificationsCallbackInterface myCallbackInterface, final Context context) {

        if (!Misc.IsOnline(context)) {
            // load events offline
            ArrayList<Notification> notifications = LoadNotificationsOffline(context);
            // callback
            myCallbackInterface.onAllDownloaded(notifications);

        } else {
            TimetableSystemAPI api = getDefaultUCLanAPI();

            Call<List<Notification>> getCall = api.getNotificationsByUser(TimetableSystemAPI.SECURITY_TOKEN, user_id);
            getCall.enqueue(new Callback<List<Notification>>() {
                @Override
                public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                    int code = response.code();
                    if (code == 200) {
                        final List<Notification> notifications = response.body();
                        Handler mainHandler = new Handler(context.getMainLooper());

                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {
                                myCallbackInterface.onAllDownloaded(notifications);
                            }
                        };
                        mainHandler.post(myRunnable);
                    }
                }

                @Override
                public void onFailure(Call<List<Notification>> call, Throwable t) {

                }
            });
        }
    }

    public static void GetRooms(final RoomCallbackInterface myCallbackInterface, final Context context) {

        TimetableSystemAPI api = getDefaultUCLanAPI();

        Call<List<JSONRoom>> getCall = api.listRooms(TimetableSystemAPI.SECURITY_TOKEN);

        getCall.enqueue(new Callback<List<JSONRoom>>() {
            @Override
            public void onResponse(Call<List<JSONRoom>> call, Response<List<JSONRoom>> response) {
                int code = response.code();
                if (code == 200) {
                    final List<JSONRoom> rooms = response.body();
                    Handler mainHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            myCallbackInterface.onDownload(rooms);
                        }
                    };
                    mainHandler.post(myRunnable);
                }
            }

            @Override
            public void onFailure(Call<List<JSONRoom>> call, Throwable t) {

            }
        });
    }

    public static void GetLoginData(final String email, final String pass, final Context context, final UserCallbackInterface myCallbackInterface) {

        TimetableSystemAPI api = getDefaultUCLanAPI();
// TODO: 26/07/16 hash pass
//        try {
//            String plaintext = "your text here";
//            MessageDigest m = MessageDigest.getInstance("MD5");
//            m.reset();
//            m.update(plaintext.getBytes());
//            byte[] digest = m.digest();
//            BigInteger bigInt = new BigInteger(1, digest);
//            String hashtext = bigInt.toString(16);
//// Now we need to zero pad it if you actually want the full 32 chars.
//            while (hashtext.length() < 32) {
//                hashtext = "0" + hashtext;
//            }
//        }
//        catch(Exception e){
//
//        }


//        byte[] thedigest = md.digest(bytesOfMessage);
//        String hashedPass = new String(thedigest);

        Call<User> getCall = api.getAuthenticationData(TimetableSystemAPI.SECURITY_TOKEN, email, pass);

        getCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int code = response.code();
                if (code == 200) {
                    final User userDetails = response.body();
                    Handler mainHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            myCallbackInterface.onDownloaded(userDetails);
                        }
                    };
                    mainHandler.post(myRunnable);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public static void LoadTimetableEvents(final String startDate, final String endDate,
                                           final String id, final MyCallbackInterface myCallbackInterface, final Context context, final TimetableType timetableType) {
        final ArrayList<TimetableSession> processedEvents = new ArrayList<>();

        // check if user is offline
        if (!Misc.IsOnline(context)) {
            // load events offline
            processedEvents.clear();
            processedEvents.addAll(LoadEventsOffline(startDate, context, timetableType));

            myCallbackInterface.onDownloadFinished(processedEvents);

        } else {
            // online request
            TimetableSystemAPI api = getDefaultUCLanAPI();
            // Choosing appropriate API
            Call<List<JSONEvent>> getCall;
            if (timetableType == TimetableType.STUDENT) {
                getCall = api.getTimetableByStudent(TimetableSystemAPI.SECURITY_TOKEN, id, startDate, endDate);
            } else if (timetableType == TimetableType.LECTURER) {
                getCall = api.getTimetableByLecturer(TimetableSystemAPI.SECURITY_TOKEN, id, startDate, endDate);
            } else if (timetableType == TimetableType.EXAM) {
                getCall = api.getUpcomingExam(TimetableSystemAPI.SECURITY_TOKEN, id);
            } else {
                getCall = api.getRoomTimtable(TimetableSystemAPI.SECURITY_TOKEN, id, startDate, endDate);
            }

            getCall.enqueue(new Callback<List<JSONEvent>>() {
                @Override
                public void onResponse(Call<List<JSONEvent>> call, Response<List<JSONEvent>> response) {
                    int code = response.code();
                    if (code == 200) {
                        processedEvents.clear();
                        List<JSONEvent> events = response.body();

                        for (JSONEvent event : events) {

                            LocalDateTime startDate = null;
                            LocalDateTime endDate = null;

                            startDate = Misc.parseHoursMinsDate(event.getSTART_TIME().getHours(), event.getSTART_TIME().getMinutes(), event.getSESSION_DATE_FORMATTED());
                            endDate = Misc.parseHoursMinsDate(event.getEND_TIME().getHours(), event.getEND_TIME().getMinutes(), event.getSESSION_DATE_FORMATTED());

                            processedEvents.add(new TimetableSession(event.getMODULE_CODE(), event.getMODULE_NAME(), event.getROOM_CODE(), startDate, endDate,
                                    event.getDAY_OF_WEEK(), Integer.parseInt(event.getDURATION()), event.getLECTURER_NAME(), event.getSESSION_DESCRIPTION()));

                        }
                        // Get a handler that can be used to post to the main thread
                        Handler mainHandler = new Handler(context.getMainLooper());

                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {
                                myCallbackInterface.onDownloadFinished(processedEvents);
                            }
                        };
                        mainHandler.post(myRunnable);


                    } else {
                        Log.d("DEBUGGING", response.raw().toString());
                        //Toast.makeText(getActivity(), "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<JSONEvent>> call, Throwable t) {
                    t.printStackTrace();
                    //Toast.makeText(getActivity(), "Nope", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public static void GetNotificationById(String id, final Context context, final NotificationsCallbackInterface callbackInterface) {
        TimetableSystemAPI api = getDefaultUCLanAPI();
        Call<Notification> getCall = api.getNotificationById(TimetableSystemAPI.SECURITY_TOKEN, id);
        getCall.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {

                int code = response.code();
                if (code == 200) {
                    final Notification notification = response.body();
                    Handler mainHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            callbackInterface.onSingleDownloaded(notification);
                        }
                    };
                    mainHandler.post(myRunnable);
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {

            }
        });
    }

    // prepare the API call instance and default GSon converter
    private static TimetableSystemAPI getDefaultUCLanAPI() {
        // JSON converter
        Gson gson = new GsonBuilder().create();
        // API library
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TimetableSystemAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(TimetableSystemAPI.class);
    }

    /////////////////////////////////
    // offline use

    // events
    public static ArrayList<TimetableSession> LoadEventsOffline(final String startDate, final Context context, final TimetableType timetableType) {

        // storing the processed events
        final ArrayList<TimetableSession> processedEvents = new ArrayList<>();

        final String fileName = context.getString(R.string.sessions_filename);
        ArrayList<JSONEvent> loadedEvents = null; // raw response objects

        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            loadedEvents = (ArrayList<JSONEvent>) is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            // no saved data found or file was deleted
            Misc.CreateNoDataFoundDialog(context);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (loadedEvents != null) {
            for (JSONEvent event : loadedEvents) {
                LocalDateTime start_date, end_date;

                start_date = Misc.parseHoursMinsDate(event.getSTART_TIME().getHours(), event.getSTART_TIME().getMinutes(), event.getSESSION_DATE_FORMATTED());
                end_date = Misc.parseHoursMinsDate(event.getEND_TIME().getHours(), event.getEND_TIME().getMinutes(), event.getSESSION_DATE_FORMATTED());

                LocalDate itemDate = Misc.parseHoursMinsDate(event.getSTART_TIME().getHours(), event.getSTART_TIME().getMinutes(), event.getSESSION_DATE_FORMATTED()).toLocalDate();
                LocalDate selectedDateParsed = Misc.APIFormatToLocalDate(startDate);

                // exams
                if ((timetableType.equals(TimetableType.EXAM) && event.getSESSION_DESCRIPTION().equals(context.getString(R.string.ExaminationTypeName)))) {
                    processedEvents.add(new TimetableSession(event.getMODULE_CODE(), event.getMODULE_NAME(), event.getROOM_CODE(), start_date, end_date,
                            event.getDAY_OF_WEEK(), Integer.parseInt(event.getDURATION()), event.getLECTURER_NAME(), event.getSESSION_DESCRIPTION()));
                }

                // if timetable, match date
                else if ((timetableType.equals(TimetableType.STUDENT) || timetableType.equals(TimetableType.LECTURER)) && itemDate.isEqual(selectedDateParsed)) {
                    processedEvents.add(new TimetableSession(event.getMODULE_CODE(), event.getMODULE_NAME(), event.getROOM_CODE(), start_date, end_date,
                            event.getDAY_OF_WEEK(), Integer.parseInt(event.getDURATION()), event.getLECTURER_NAME(), event.getSESSION_DESCRIPTION()));
                }
            }
        }
        return processedEvents;
    }

    public static void SaveEventsOffline(final String startDate, final String endDate, final String studentID, final Context context) {
        final String fileName = context.getString(R.string.sessions_filename);
        // prepare call in Retrofit 2.0
        TimetableSystemAPI api = getDefaultUCLanAPI();

        Call<List<JSONEvent>> getCall = api.getTimetableByStudent(TimetableSystemAPI.SECURITY_TOKEN, studentID, startDate, endDate);

        getCall.enqueue(new Callback<List<JSONEvent>>() {
            @Override
            public void onResponse(Call<List<JSONEvent>> call, Response<List<JSONEvent>> response) {
                int code = response.code();
                if (code == 200) {
                    List<JSONEvent> events = response.body();

                    try {
                        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                        ObjectOutputStream os = new ObjectOutputStream(fos);
                        os.writeObject(events);
                        os.close();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d("DEBUGGING", response.raw().toString());
                    //Toast.makeText(getActivity(), "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<JSONEvent>> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(getActivity(), "Nope", Toast.LENGTH_LONG).show();
            }
        });

    }

    // notification
    public static ArrayList<Notification> LoadNotificationsOffline(final Context context) {

        final String fileName = context.getString(R.string.sessions_filename);
        ArrayList<Notification> notifications = null; // raw response objects

        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            notifications = (ArrayList<Notification>) is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            // no saved data found or file was deleted
            Misc.CreateNoDataFoundDialog(context);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (notifications != null) {

        }
        return notifications;
    }

    public static void SaveNotificationsOffline(final String user_id, final Context context) {
        TimetableSystemAPI api = getDefaultUCLanAPI();
        final String fileName = context.getString(R.string.notifications_filename);

        Call<List<Notification>> getCall = api.getNotificationsByUser(TimetableSystemAPI.SECURITY_TOKEN, user_id);
        getCall.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                int code = response.code();
                if (code == 200) {
                    final List<Notification> notifications = response.body();

                    try {
                        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                        ObjectOutputStream os = new ObjectOutputStream(fos);
                        os.writeObject(notifications);
                        os.close();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {

            }
        });
    }
}
