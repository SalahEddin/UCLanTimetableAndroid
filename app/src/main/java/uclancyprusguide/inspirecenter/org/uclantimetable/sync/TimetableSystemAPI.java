package uclancyprusguide.inspirecenter.org.uclantimetable.sync;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONAuthenticationUser;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONEvent;
import uclancyprusguide.inspirecenter.org.uclantimetable.data.JSONRoom;

/**
 * Created by salah on 13/07/16.
 */
public interface TimetableSystemAPI {
    final String ENDPOINT = "https://cyprustimetable.uclan.ac.uk/TimetableAPI/TimetableWebService.asmx/";
    final String SECURITY_TOKEN = "e84e281d4c9b46f8a30e4a2fd9aa7058";

    @GET("app_getTimetableByStudent")
    Call<List<JSONEvent>> getTimetableByStudent(@Query("securityToken") String securityToken, @Query("STUDENT_ID") String studentId, @Query("START_DATE_TIME") String startDate, @Query("END_DATE_TIME") String endDate);

    @GET("app_login")
    Call<JSONAuthenticationUser> getAuthenticationData(@Query("securityToken") String securityToken, @Query("USERNAME") String username, @Query("PASSWORD") String hashedPass);

    @GET("app_getRooms")
    Call<List<JSONRoom>> listRooms(@Query("securityToken") String securityToken);

    @GET("app_getTimetableByRoom")
    Call<List<JSONEvent>> getRoomTimtable(@Query("securityToken") String securityToken, @Query("ROOM_ID") String roomCode, @Query("START_DATE_TIME") String startDate, @Query("END_DATE_TIME") String endDate);
}
