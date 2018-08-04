package com.medexpertz.medexpertzdoctor.shankar.realm;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;


import com.medexpertz.medexpertzdoctor.shankar.Utilz.PreferenceName;
import com.medexpertz.medexpertzdoctor.shankar.model.UserDetails;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {
    String TAG = RealmController.class.getSimpleName();
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {
        realm.beginTransaction();
        realm.clear(UserDetails.class);
        realm.commitTransaction();
    }

    //find all objects in the UserDetails.class
    public RealmResults<UserDetails> getUserDetails() {
        return realm.where(UserDetails.class).findAll();
    }

    //query a single item with the given id
    public UserDetails getUserDetails(String userPhone) {
        return realm.where(UserDetails.class).equalTo(PreferenceName.USER_MOBILE, userPhone).findFirst();
    }

    //check if UserDetails.class is empty
    public boolean isAlreadyExists(UserDetails userDetails) {
        return !realm.allObjects(UserDetails.class).contains(userDetails);
    }

    //check if UserDetails.class is empty
    public boolean hasUsersDetail() {
        return !realm.allObjects(UserDetails.class).isEmpty();
    }

    //query example
    public RealmResults<UserDetails> getAllUserDetails(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            if (hasUsersDetail()) {
                return realm.where(UserDetails.class)
                        .contains(key, value)
                        .findAll();
            } else {
                return null;
            }
        } else {
            return realm.allObjects(UserDetails.class);
        }
    }

    public boolean insertUpdateUserDetails(ArrayList<UserDetails> userDetailsArrayList) {
        boolean isUserDetailsSaved = false;
        try {
            for (int i = 0; i < userDetailsArrayList.size(); i++) {
                if (userDetailsArrayList != null) {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(userDetailsArrayList.get(i));
                    realm.commitTransaction();
                    Log.i(TAG, "userDetailsArrayList not null - Inserting : "+userDetailsArrayList.get(i).getUserPhone());
                } else {
                    Log.i(TAG, "userDetailsArrayList is null - Failed Inserting");
                }
            }
            isUserDetailsSaved = true;
        } catch (Exception e) {
        }
        return isUserDetailsSaved;
    }
}