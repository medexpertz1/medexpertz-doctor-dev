package com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork


import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.medexpertz.medexpertzdoctor.shankar.model.*
import java.util.concurrent.TimeUnit
import io.reactivex.annotations.NonNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import com.bigappcompany.medexpertz.shankar.model.FirebaseUserAuthModel
import org.json.JSONObject

 class RetrofitDataProvider(private val context: Context) : AppCompatActivity(), ServiceMethods {


     override fun notificationStatusChange(auth: String, notification_id: String, callback: DownlodableCallback<NotificationStatusChangeModel>) {
         createRetrofitService().changeNotificationStatus( auth, notification_id).enqueue(
                 object : Callback<NotificationStatusChangeModel> {
                     override fun onResponse(@NonNull call: Call<NotificationStatusChangeModel>, @NonNull response: Response<NotificationStatusChangeModel>) {
                         if (response.code() == 200) {
                             val mobileRegisterPojo = response.body()
                             if (mobileRegisterPojo != null) {
                                 callback!!.onSuccess(mobileRegisterPojo)
                             }


                         } else {
                             if (response.code() == 401) {
                                 callback!!.onUnauthorized(response.code())
                             } else {

                             }
                         }
                     }

                     override fun onFailure(@NonNull call: Call<NotificationStatusChangeModel>, @NonNull t: Throwable) {
                         Log.d("Result", "t" + t.message)
                         callback!!.onFailure(t.message!!)

                     }
                 }
         )

     }


     override fun updateWeekOff(auth: String, id: ArrayList<String>, callback: DownlodableCallback<Void>?) {
         createRetrofitService().addDoctorWeekOff( auth, id).enqueue(
                 object : Callback<Void> {
                     override fun onResponse(@NonNull call: Call<Void>, @NonNull response: Response<Void>) {
                         if (response.code() == 200) {
                             callback!!.onUnauthorized(response.code())


                         } else {
                             if (response.code() == 401) {
                                 callback!!.onUnauthorized(response.code())
                             } else {

                             }
                         }
                     }

                     override fun onFailure(@NonNull call: Call<Void>, @NonNull t: Throwable) {
                         Log.d("Result", "t" + t.message)
                         callback!!.onFailure(t.message!!)

                     }
                 }
         )
     }

     override fun getWeekDays(auth: String, callback: DownlodableCallback<java.util.ArrayList<WeekdaysModel>>) {
         createRetrofitService().getWeekDays(auth).enqueue(
                 object : Callback<ArrayList<WeekdaysModel>>{
                     override fun onFailure(call: Call<ArrayList<WeekdaysModel>>?, t: Throwable?) {
                         callback!!.onFailure(t!!.message!!)
                     }

                     override fun onResponse(call: Call<ArrayList<WeekdaysModel>>?, response: Response<ArrayList<WeekdaysModel>>?) {
                         if (response!!.code() == 200) {

                             val mobileRegisterPojo = response.body()
                             if (mobileRegisterPojo != null) {
                                 callback!!.onSuccess(mobileRegisterPojo)
                             }

                         } else {
                             if (response.code() == 401) {
                                 callback!!.onUnauthorized(response.code())
                             } else {

                             }
                         }
                     }
                 })
     }

     override fun doctorTimeList(auth: String, date: String, callback: DownlodableCallback<ArrayList<DoctorTimeLisrModel>>?) {
         createRetrofitService().getDoctorTimeList( auth).enqueue(
                 object : Callback<ArrayList<DoctorTimeLisrModel>>{
                     override fun onFailure(call: Call<ArrayList<DoctorTimeLisrModel>>?, t: Throwable?) {
                         callback!!.onFailure(t!!.message!!)
                     }

                     override fun onResponse(call: Call<ArrayList<DoctorTimeLisrModel>>?, response: Response<ArrayList<DoctorTimeLisrModel>>?) {
                         if (response!!.code() == 200) {

                             val mobileRegisterPojo = response.body()
                             if (mobileRegisterPojo != null) {
                                 callback!!.onSuccess(mobileRegisterPojo)
                             }

                         } else {
                             if (response.code() == 401) {
                                 callback!!.onUnauthorized(response.code())
                             } else {

                             }
                         }
                     }
                 })
     }

     override fun patientDocument(auth: String, patient_id: String, callback: DownlodableCallback<ArrayList<PatientDocumentModel>>?) {
        createRetrofitService().getPatientDocument( auth, patient_id).enqueue(
                object : Callback<ArrayList<PatientDocumentModel>>{
                    override fun onFailure(call: Call<ArrayList<PatientDocumentModel>>?, t: Throwable?) {
                        callback!!.onFailure(t!!.message!!)
                    }

                    override fun onResponse(call: Call<ArrayList<PatientDocumentModel>>?, response: Response<ArrayList<PatientDocumentModel>>?) {
                        if (response!!.code() == 200) {

                            val mobileRegisterPojo = response.body()
                            if (mobileRegisterPojo != null) {
                                callback!!.onSuccess(mobileRegisterPojo)
                            }

                        } else {
                            if (response.code() == 401) {
                                callback!!.onUnauthorized(response.code())
                            } else {

                            }
                        }
                    }
                })


                    }

    private fun createRetrofitService(): ApiRetrofitService {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()


        val retrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(ApiRetrofitService::class.java)

    }

    companion object {
        const val URL_BASE = "http://crm.medexpertz.com/api/"
    }

    override fun timeSlot(auth: String, workTimingRequest: WorkTimingRequest,  callback: DownlodableCallback<Void>) {
        createRetrofitService().setWorkTimeDetails( auth, workTimingRequest).enqueue(
                object : Callback<Void> {
                    override fun onResponse(@NonNull call: Call<Void>, @NonNull response: Response<Void>) {
                        if (response.code() == 200) {
                            callback.onUnauthorized(response.code())


                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code())
                            } else {

                            }
                        }
                    }

                    override fun onFailure(@NonNull call: Call<Void>, @NonNull t: Throwable) {
                        Log.d("Result", "t" + t.message)
                        callback.onFailure(t.message!!)

                    }
                }
        )
    }

     override fun getNotification(auth: String, callback: DownlodableCallback<ArrayList<NotificationNewModel>>?) {

         createRetrofitService().getNewNotification( auth).enqueue(
                 object : Callback<ArrayList<NotificationNewModel>> {
                     override fun onResponse(@NonNull call: Call<ArrayList<NotificationNewModel>>, @NonNull response: Response<ArrayList<NotificationNewModel>>) {
                         if (response.code() == 200) {
                             callback!!.onSuccess(response.body()!!)


                         } else {
                             if (response.code() == 401) {
                                 callback!!.onUnauthorized(response.code())
                             } else {

                             }
                         }
                     }

                     override fun onFailure(@NonNull call: Call<ArrayList<NotificationNewModel>>, @NonNull t: Throwable) {
                         Log.d("Result", "t" + t.message)
                         callback!!.onFailure(t.message!!)

                     }
                 }
         )

     }
     override fun callExotel(From: String, To: String, CallerId: String, callback: DownlodableCallback<Void>) {
         createRetrofitService().callExotel(From, To, CallerId).enqueue(
                 object : Callback<Void> {
                     override fun onResponse(@NonNull call: Call<Void>, @NonNull response: Response<Void>) {
                         if (response.code() == 200) {


                         } else {
                             if (response.code() == 401) {
                                 try {
                                     val jObjError = JSONObject(response.errorBody()!!.string())
                                     Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show()
                                 } catch (e: Exception) {
                                     Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                 }

                                 callback!!.onUnauthorized(response.code())
                             } else {

                             }
                         }
                     }

                     override fun onFailure(@NonNull call: Call<Void>, @NonNull t: Throwable) {
                         Log.d("Result", "t" + t.message)
                         callback!!.onFailure(t.message!!)

                     }
                 }
         )
     }
     override fun forgotPassword(auth: String,email: String, callback: DownlodableCallback<Void>?) {
         createRetrofitService().forotPassword(auth, email).enqueue(
                 object : Callback<Void> {
                     override fun onResponse(@NonNull call: Call<Void>, @NonNull response: Response<Void>) {
                         if (response.code() == 200) {

                             callback!!.onUnauthorized(response.code())
                         } else {
                             if (response.code() == 401) {
                                 try {
                                     val jObjError = JSONObject(response.errorBody()!!.string())
                                     Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show()
                                 } catch (e: Exception) {
                                     Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                 }

                                 callback!!.onUnauthorized(response.code())
                             } else {

                             }
                         }
                     }

                     override fun onFailure(@NonNull call: Call<Void>, @NonNull t: Throwable) {
                         Log.d("Result", "t" + t.message)
                         callback!!.onFailure(t.message!!)

                     }
                 }
         )
     }

     fun getUserFromFirebase(callback: DownlodableCallback<FirebaseUserAuthModel>) {
         createRetrofitService().getUserromFirebase().enqueue(
                 object : Callback<FirebaseUserAuthModel> {
                     override fun onResponse(@NonNull call: Call<FirebaseUserAuthModel>, @NonNull response: Response<FirebaseUserAuthModel>) {
                         if (response.code() == 200) {

                             callback.onUnauthorized(response.code())

                         } else {
                             if (response.code() == 401) {
                                 callback.onUnauthorized(response.code())
                             } else {

                             }
                         }
                     }

                     override fun onFailure(@NonNull call: Call<FirebaseUserAuthModel>, @NonNull t: Throwable) {
                         Log.d("Result", "t" + t.message)
                         callback.onFailure(t.message!!)

                     }
                 }
         )
     }


 }
