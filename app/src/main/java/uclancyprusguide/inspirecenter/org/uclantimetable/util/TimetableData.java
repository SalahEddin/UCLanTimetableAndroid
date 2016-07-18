package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

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
import uclancyprusguide.inspirecenter.org.uclantimetable.ui.TimetableGenericAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.ui.TimetableSessionAdapter;

/**
 * Created by salah on 06/07/16.
 */
public class TimetableData {
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

    public static void GetRerofitTimetableByStudent(final ArrayList<TimetableSession> arr, final TimetableGenericAdapter eventArrAdapter, final TimetableEventsType typeFilter, final SwipeRefreshLayout pullToRefresh) {
        Gson gson = new GsonBuilder()
                //.setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TimetableSystemAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        // prepare call in Retrofit 2.0
        TimetableSystemAPI api = retrofit.create(TimetableSystemAPI.class);

        // TODO: 14/07/16 change dates dynamically
        Call<List<JSONEvent>> getCall = api.getTimetableByStudent(TimetableSystemAPI.SECURITY_TOKEN, "622", "2016-01-01", "2016-08-08");
        getCall.enqueue(new Callback<List<JSONEvent>>() {
            @Override
            public void onResponse(Call<List<JSONEvent>> call, Response<List<JSONEvent>> response) {
                int code = response.code();
                if (code == 200) {
                    arr.clear();
                    List<JSONEvent> events = response.body();

                    for (JSONEvent event :
                            events
                            ) {
                        //Log.d("DEBUGGING", event.getSESSION_DESCRIPTION());
                        if (typeFilter.equals(TimetableEventsType.EXAMS) && event.getSESSION_DESCRIPTION().equals("Examination")) {
                            arr.add(new TimetableSession(event.getMODULE_CODE(), event.getMODULE_NAME(), event.getROOM_CODE(), event.getSTART_TIME_FORMATTED(), event.getEND_TIME_FORMATTED(),
                                    event.getDAY_OF_WEEK(), Integer.parseInt(event.getDURATION()), event.getLECTURER_NAME(), event.getSESSION_DESCRIPTION()));
                        } else if (!typeFilter.equals(TimetableEventsType.EXAMS)) {
                            arr.add(new TimetableSession(event.getMODULE_CODE(), event.getMODULE_NAME(), event.getROOM_CODE(), event.getSTART_TIME_FORMATTED(), event.getEND_TIME_FORMATTED(),
                                    event.getDAY_OF_WEEK(), Integer.parseInt(event.getDURATION()), event.getLECTURER_NAME(), event.getSESSION_DESCRIPTION()));
                        }
                    }
                    eventArrAdapter.notifyDataSetChanged();
                    if (pullToRefresh.isRefreshing()) pullToRefresh.setRefreshing(false);

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
