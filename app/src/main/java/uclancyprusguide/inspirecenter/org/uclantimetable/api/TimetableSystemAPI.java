package uclancyprusguide.inspirecenter.org.uclantimetable.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Notification;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.User;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.JSONEvent;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.JSONRoom;

/**
 * Created by salah on 13/07/16.
 */
public interface TimetableSystemAPI {
    String ENDPOINT = "https://cyprustimetable.uclan.ac.uk/TimetableAPI/TimetableWebService.asmx/";
    String SECURITY_TOKEN = "e84e281d4c9b46f8a30e4a2fd9aa7058";

    @GET("app_getTimetableByStudent")
    Call<List<JSONEvent>> getTimetableByStudent(@Query("securityToken") String securityToken, @Query("STUDENT_ID") String studentId, @Query("START_DATE_TIME") String startDate, @Query("END_DATE_TIME") String endDate);

    @GET("app_getTimetableByLecturer")
    Call<List<JSONEvent>> getTimetableByLecturer(@Query("securityToken") String securityToken, @Query("LECTURER_ID") String studentId, @Query("START_DATE_TIME") String startDate, @Query("END_DATE_TIME") String endDate);

    @GET("app_login")
    Call<User> getAuthenticationData(@Query("securityToken") String securityToken, @Query("USERNAME") String username, @Query("PASSWORD") String hashedPass);

    @GET("app_getRooms")
    Call<List<JSONRoom>> listRooms(@Query("securityToken") String securityToken);

    @GET("app_getTimetableByRoom")
    Call<List<JSONEvent>> getRoomTimtable(@Query("securityToken") String securityToken, @Query("ROOM_ID") String roomCode, @Query("START_DATE_TIME") String startDate, @Query("END_DATE_TIME") String endDate);

    @GET("app_getNotificationsByUser")
    Call<List<Notification>> getNotificationsByUser(@Query("securityToken") String securityToken, @Query("USER_ID") String user_id);

    @GET("app_getUpcomingExams")
    Call<List<JSONEvent>> getUpcomingExam(@Query("securityToken") String securityToken, @Query("STUDENT_ID") String studentId);

    @GET("app_getNotification")
    Call<Notification> getNotificationById(@Query("securityToken") String securityToken, @Query("NOTIFICATION_ID") String id);

    @GET("app_updateNotificationStatus")
    Call<Void> updateNotificationStatus(@Query("securityToken") String securityToken, @Query("NOTIFICATION_ID") int id, @Query("NOTIFICATION_STATUS") int newStatus);
}