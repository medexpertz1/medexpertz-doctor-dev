package com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork;


import com.medexpertz.medexpertzdoctor.shankar.model.DoctorTimeLisrModel;
import com.medexpertz.medexpertzdoctor.shankar.model.NotificationNewModel;
import com.medexpertz.medexpertzdoctor.shankar.model.NotificationStatusChangeModel;
import com.medexpertz.medexpertzdoctor.shankar.model.PatientDocumentModel;
import com.medexpertz.medexpertzdoctor.shankar.model.WeekdaysModel;
import com.medexpertz.medexpertzdoctor.shankar.model.WorkTimeModel;
import com.medexpertz.medexpertzdoctor.shankar.model.WorkTimingRequest;

import java.util.ArrayList;
import java.util.List;

public interface ServiceMethods {

    void timeSlot(String auth, WorkTimingRequest workTimingRequest, DownlodableCallback<Void> callback);
    void patientDocument(String auth, String patient_id, DownlodableCallback<ArrayList<PatientDocumentModel>> callback);
    void doctorTimeList(String auth, String date, DownlodableCallback<ArrayList<DoctorTimeLisrModel>> callback);
    void getWeekDays(String auth, DownlodableCallback<ArrayList<WeekdaysModel>> callback);
    void updateWeekOff(String auth,  ArrayList<String> id,DownlodableCallback<Void> callback);
    void getNotification(String auth,  DownlodableCallback<ArrayList<NotificationNewModel>> callback);
    void notificationStatusChange(String auth, String notification_id, DownlodableCallback<NotificationStatusChangeModel> callback);
    void callExotel(String from, String to, String callerId, DownlodableCallback<Void> callback);
    void forgotPassword(String auth, String email, DownlodableCallback<Void> callback);


}
