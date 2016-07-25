package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONEvent;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.sync.TimetableSystemAPI;

import org.threeten.bp.*;

/**
 * Created by salah on 06/07/16.
 */
public class TimetableData {

    //define callback interface
    public interface MyCallbackInterface {

        void onDownloadFinished(ArrayList<TimetableSession> result);
    }

    public enum TimetableEventsType {
        ALL, EXAMS, NOTIFICATIONS
    }

    public ArrayList<TimetableSession> LoadEvents(TimetableEventsType eventType) {
        // todo check login first

        ArrayList<TimetableSession> entries = new ArrayList<>();
        switch (eventType) {
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

    public static void LoadTimetableEvents(final TimetableEventsType typeFilter,
                                           final String startDate, final String endDate,
                                           final String studentID, final MyCallbackInterface myCallbackInterface, final Context context) {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TimetableSystemAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        TimetableSystemAPI api = retrofit.create(TimetableSystemAPI.class);

        Call<List<JSONEvent>> getCall = api.getTimetableByStudent(TimetableSystemAPI.SECURITY_TOKEN, studentID, startDate, endDate);

        // array of events
        final ArrayList<TimetableSession> arr = new ArrayList<>();

        getCall.enqueue(new Callback<List<JSONEvent>>() {
            @Override
            public void onResponse(Call<List<JSONEvent>> call, Response<List<JSONEvent>> response) {
                int code = response.code();
                if (code == 200) {
                    arr.clear();
                    List<JSONEvent> events = response.body();

                    for (JSONEvent event : events) {


                        LocalDateTime startDate = null;
                        LocalDateTime endDate = null;

                        startDate = Misc.parseHoursMinsDate(event.getSTART_TIME().getHours(), event.getSTART_TIME().getMinutes(), event.getSESSION_DATE_FORMATTED());
                        endDate = Misc.parseHoursMinsDate(event.getEND_TIME().getHours(), event.getEND_TIME().getMinutes(), event.getSESSION_DATE_FORMATTED());

                        if ((typeFilter.equals(TimetableEventsType.EXAMS) && event.getSESSION_DESCRIPTION().equals("Examination"))
                                || (typeFilter.equals(TimetableEventsType.ALL))) {
                            arr.add(new TimetableSession(event.getMODULE_CODE(), event.getMODULE_NAME(), event.getROOM_CODE(), startDate, endDate,
                                    event.getDAY_OF_WEEK(), Integer.parseInt(event.getDURATION()), event.getLECTURER_NAME(), event.getSESSION_DESCRIPTION()));
                        }
                    }
                    // Get a handler that can be used to post to the main thread
                    Handler mainHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            myCallbackInterface.onDownloadFinished(arr);
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
