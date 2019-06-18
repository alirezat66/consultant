package greencode.ir.consulant.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface reqInterface {
    @FormUrlEncoded
    @POST("api/v1/counselor/authentication")
    Call<JsonObject> Authenticate(@Field("mobile") String mobile);
    @FormUrlEncoded
    @POST("api/v1/user/getCategories")
    Call<JsonObject> GetCats(@Field("token") String mobile, @Field("timestamp") long timestamp, @Field("adviserId") String adviserId);
    @FormUrlEncoded
    @POST("api/v1/counselor/getAdvocacyTypesName")
    Call<JsonObject> getAdvocacyTypesName(@Field("timestamp") long timestamp);


    @FormUrlEncoded
    @POST("api/v1/counselor/getDegreeTypeNames")
    Call<JsonObject> getDegreeTypeNames(@Field("timestamp") long timestamp);

    @FormUrlEncoded
    @POST("api/v1/counselor/getCountries")
    Call<JsonObject> getCountries(@Field("timestamp") long timestamp);

    @FormUrlEncoded
    @POST("api/v1/counselor/getBankNames")
    Call<JsonObject> getBankNames(@Field("timestamp") long timestamp);

    @FormUrlEncoded
    @POST("api/v1/counselor/getProvinces")
    Call<JsonObject> getProvinces(@Field("countryId") int countryId,@Field("timestamp") long timestamp);
@FormUrlEncoded
    @POST("api/v1/counselor/getCities")
    Call<JsonObject> getCities(@Field("provinceId") int countryId,@Field("timestamp") long timestamp);
@FormUrlEncoded
    @POST("api/v1/counselor/getAreas")
    Call<JsonObject> getAreas(@Field("cityId") int cityId,@Field("timestamp") long timestamp);
@FormUrlEncoded
    @POST("api/v1/counselor/IdentityConfirmationUser")
    Call<JsonObject> IdentityConfirmationUser(@Field("token") String token,
                                              @Field("userId") int userId,
                                              @Field("personalInfo")String personalInfo,
                                              @Field("jobInfo")String jobInfo,
                                              @Field("uploads")String uploads);

    @FormUrlEncoded
    @POST("api/v1/counselor/getActiveOffers")
    Call<JsonObject> getActiveOffers(@Field("token") String token);
 @FormUrlEncoded
    @POST("api/v1/counselor/getRequests")
    Call<JsonObject> getRequests(@Field("token") String token);
    @FormUrlEncoded
    @POST("api/v1/counselor/getWaitedRequestDetial")
    Call<JsonObject> getWaitedRequestDetial(@Field("token") String token,@Field("requestId")int requestId);
 @FormUrlEncoded
    @POST("api/v1/counselor/acceptRequest")
    Call<JsonObject> acceptRequest(@Field("token") String token,@Field("id")int requestId,@Field("adviserId") int adviserId);

     @FormUrlEncoded
    @POST("api/v1/counselor/getHistoryOffers")
    Call<JsonObject> getHistoryOffers(@Field("token") String token);
    @FormUrlEncoded
    @POST("api/v1/counselor/getReadyToAnswerRequestDetial")
    Call<JsonObject> getReadyToAnswerRequestDetial(@Field("token") String token,@Field("offerId") int offerId);
 @FormUrlEncoded
    @POST("api/v1/counselor/getCanceledRequestBeforeAssignedDetial")
    Call<JsonObject> getCanceledRequestBeforeAssignedDetial(@Field("token") String token,@Field("offerId") int offerId);


    @FormUrlEncoded
    @POST("api/v1/counselor/cancelOffer")
    Call<JsonObject> cancelOffer(@Field("token") String token,@Field("offerId") int offerId,@Field("adviserId") int adviserId);
    @FormUrlEncoded
    @POST("api/v1/counselor/getAssignedRequestDetail")
    Call<JsonObject> getAssignedRequestDetail(@Field("token") String token, @Field("offerId") int id);



    @FormUrlEncoded
    @POST("api/v1/user/createRequest")
     Call<JsonObject> createRequest(@Field("token") String token, @Field("userId") int userId, @Field("catId") int catId, @Field("documents") List<String> documents, @Field("desc") String desc);

    @FormUrlEncoded
    @POST("api/v1/counselor/createRequest")
    Call<JsonObject> createRequest(@Field("token") String token, @Field("userId") int userId, @Field("catId") int catId, @Field("documents") String documents, @Field("desc") String desc);

    @FormUrlEncoded
    @POST("api/v1/counselor/getAcceptedRequestDetail")
    Call<JsonObject> GetActivatedReqRequestDetial(@Field("token") String token, @Field("requestId") int id);


    @FormUrlEncoded
    @POST("api/v1/user/acceptAdviser")
    Call<JsonObject> acceptAdviser(@Field("token") String token, @Field("id") int id
            , @Field("adviserId") int adviserId, @Field("responseHours") String responseHourId, @Field("callTimestamp") long callTimestamp, @Field("hourCount") double hourCount);

    @FormUrlEncoded
    @POST("api/v1/user/cancelRequest")
    Call<JsonObject> cancelRequest(@Field("token") String token, @Field("id") int id
            , @Field("userId") int userId);
    @FormUrlEncoded
    @POST("api/v1/counselor/getUserInfo")
    Call<JsonObject> getProfileInfo(@Field("token") String token, @Field("id") int id);

    @FormUrlEncoded
    @POST("api/v1/counselor/getUserState")
    Call<JsonObject> getUserState(@Field("token") String token);
    @FormUrlEncoded
    @POST("api/v1/counselor/updateUser")
    Call<JsonObject> updateProfile(@Field("token") String token, @Field("id") int id, @Field("firstName") String firstName
            , @Field("lastName") String lastName, @Field("birthDate") long birthDate, @Field("sex") String sex, @Field("email") String email,
                                   @Field("userImg") String userImg, @Field("nationalCode") String nationalCode);
    @FormUrlEncoded
    @POST("api/v1/counselor/getDoneRequestDetial")
    Call<JsonObject> getDoneRequestDetial(@Field("token") String token, @Field("offerId") int offerId);
    @FormUrlEncoded
    @POST("api/v1/counselor/getHistoryRequests")
    Call<JsonObject> getMyRequest(@Field("token") String token);
    @FormUrlEncoded
    @POST("api/v1/counselor/updateCallStatus")
    Call<JsonObject> updateCall(@Field("token") String token,@Field("adviserId") int id,
                                @Field("callId") int callid,@Field("status") String status
                                ,@Field("time") long time,@Field("duration") int duration
                                ,@Field("desc") String desc);
    @FormUrlEncoded
    @POST("api/v1/counselor/sendFCMToken")
    Call<JsonObject> sendGcmToken(@Field("token") String token, @Field("FCMToken") String gcmToken
    );
    @FormUrlEncoded
    @POST("api/v1/counselor/logout")
    Call<JsonObject> logOut(@Field("token") String token
    );
    @FormUrlEncoded
    @POST("api/v1/counselor/checkActiveCallRequest")
    Call<JsonObject> checkCallState(@Field("token") String token,@Field("offerId")String offerId);
}
