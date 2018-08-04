package com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork

import com.bigappcompany.medexpertz.shankar.model.FirebaseUserAuthModel
import com.medexpertz.medexpertzdoctor.shankar.Utilz.PreferenceName
import com.medexpertz.medexpertzdoctor.shankar.model.*

import java.util.ArrayList

import retrofit2.Call
import retrofit2.http.*

interface ApiRetrofitService {

    @Headers(API_KEY + ":" + API_VALUE )
    @POST(DOCTORTIMESLOT)
    fun setWorkTimeDetails(@Header(TOKEN_KEY) apikey: String, @Body worktiminfrequest: WorkTimingRequest): Call<Void>

    @Headers(API_KEY + ":" + API_VALUE )
    @GET(PATIENT_DOCUMENT+"/{patient_id}")
    fun getPatientDocument(@Header(TOKEN_KEY) apikey: String, @Path("patient_id") start_time: String): Call<ArrayList<PatientDocumentModel>>

    @Headers(API_KEY + ":" + API_VALUE)
    @POST(DOCTORTIMELIST_SLOT)
    fun getDoctorTimeList(@Header(TOKEN_KEY) apikey: String): Call<ArrayList<DoctorTimeLisrModel>>

    @Headers(API_KEY + ":" + API_VALUE)
    @GET(WEEKDAY)
    fun getWeekDays(@Header(TOKEN_KEY) apikey: String): Call<ArrayList<WeekdaysModel>>

    @Headers(API_KEY + ":" + API_VALUE)
    @POST(ADDDOCTORWEEKOFF)
    @FormUrlEncoded
    fun addDoctorWeekOff(@Header(TOKEN_KEY) apikey: String, @Field("day_id[]") id: ArrayList<String>): Call<Void>

    @Headers(API_KEY + ":" + API_VALUE)
    @GET(NOTIFICATIONNEW)
    fun getNewNotification(@Header(TOKEN_KEY) apikey: String): Call<ArrayList<NotificationNewModel>>

    @Headers(API_KEY + ":" + API_VALUE)
    @PUT(CHANGENOTIFICATIONSTATUS)
    @FormUrlEncoded
    fun changeNotificationStatus(@Header(TOKEN_KEY) apikey: String, @Field("notification_id") id: String): Call<NotificationStatusChangeModel>

    @POST(EXOTELCALLAPI)
    @FormUrlEncoded
    fun callExotel(@Field("From") from: String, @Field("To") to: String, @Field("CallerId") callerId: String): Call<Void>

    @Headers(API_KEY + ":" + API_VALUE)
    @POST(FORGOTPASSWORD)
    @FormUrlEncoded
    fun forotPassword(@Header(TOKEN_KEY) apikey: String, @Field("email") id: String): Call<Void>

    @GET(PreferenceName.FIREBASE_DOLCTORS_LIST_URL)
    fun getUserromFirebase(): Call<FirebaseUserAuthModel>


    private companion object {
        const val API_KEY = "x-api-key"
        const val API_VALUE = "LozC7H0rINg8kO1FMrm1RRBIzjw9AozsQwguKB3J"
        const val TOKEN_KEY = "Authorization"
        const val CID = "medexpertz"
        const val EXOTEL_USERNAME = "username"
        const val EXOTEL_PASSWORD = "password"
        const val EXOTELTOKEN = "35e8f6aa0440fc120fe6de7a8d5aed3ed577efa1"
        const val TOKEN_VALUE = "Bearer "
        const val DOCTORTIMESLOT = "doctor-time-slots"
        const val PATIENT_DOCUMENT = "patient-document"
        const val DOCTORTIMELIST_SLOT = "get-doctor-time-slots"
        const val WEEKDAY = "get-weekdays-list"
        const val ADDDOCTORWEEKOFF = "add-doctor-weekly-off"
        const val NOTIFICATIONNEW = "notifications-get"
        const val CHANGENOTIFICATIONSTATUS = "change-notification-status"
        const val EXOTELCALLAPI = "http://apextechies.com/apexschool/index.php/exotel_callapi"
        const val FORGOTPASSWORD = "forgot-password"




    }
//8884507593
}
