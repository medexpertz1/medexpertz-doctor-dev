package com.medexpertz.medexpertzdoctor.shankar.model;

import android.graphics.Bitmap;


import com.medexpertz.medexpertzdoctor.shankar.Utilz.PreferenceName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserDetails extends RealmObject {
    public static String onlineStatus = PreferenceName.ONLINE;
    public static String chatWith = "";
    public static Bitmap chatWithImage = null;
    public static boolean isChatting;
    private String userName;
    @PrimaryKey
    private String userPhone;
    private String userEmail;
    private String userLocation;
    private String status;
    private String userDOB;
    private String userGender;

    public UserDetails(String userName, String userPhone, String userEmail, String userLocation, String status, String userDOB, String userGender, String userImage) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userLocation = userLocation;
        this.status = status;
        this.userDOB = userDOB;
        this.userGender = userGender;
        this.userImage = userImage;
    }

    private String userImage;

    public UserDetails() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
