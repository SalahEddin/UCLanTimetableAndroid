package uclancyprusguide.inspirecenter.org.uclantimetable.sync;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONEvent;

/**
 * Created by salah on 13/07/16.
 */
public interface TimetableSystemAPI {
    final String ENDPOINT = "https://cyprustimetable.uclan.ac.uk/TimetableAPI/TimetableWebService.asmx/";
    final String SECURITY_TOKEN = "e84e281d4c9b46f8a30e4a2fd9aa7058";

    @GET("getTimetableByStudent")
    Call<List<JSONEvent>> getTimetableByStudent(@Query("securityToken") String securityToken, @Query("STUDENT_ID") String studentId, @Query("START_DATE_TIME") String startDate, @Query("END_DATE_TIME") String endDate);
}
