package com.bigappcompany.medexpertz.shankar.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bigappcompany.medexpertz.shankar.activity.ChatActivity
import com.firebase.client.ChildEventListener
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.Utilz.Download_web
import com.medexpertz.medexpertzdoctor.shankar.Utilz.PreferenceName
import com.medexpertz.medexpertzdoctor.shankar.Utilz.Utilz
import com.medexpertz.medexpertzdoctor.shankar.adapter.UsersAdapter
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.interfaces.OnTaskCompleted
import com.medexpertz.medexpertzdoctor.shankar.model.UserDetails
import com.medexpertz.medexpertzdoctor.shankar.realm.RealmController
import kotlinx.android.synthetic.main.fragment_users.*
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.prefs.Preferences

class UsersFragment: Fragment() {
    internal var usersDetailArrayList = ArrayList<UserDetails>()
    var list = ArrayList<UserDetails>()
     var isToUpdateStatusOnly = false
     var isApiCalling = false
    var userAdapter: UsersAdapter?= null
     var totalUsers = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RealmController.with(this).refresh()
        userAdapter = UsersAdapter(activity, list)
        usersList.adapter = userAdapter

        usersList.setOnItemClickListener { parent, view, position, id ->
            UserDetails.chatWith = usersDetailArrayList[position].userPhone
            UserDetails.onlineStatus = usersDetailArrayList[position].status
            startActivity(Intent(activity, ChatActivity::class.java))
        }
        getAllUsersAPI()
        getAllUsersDetailWhenChiledUpdatedOrAdded()
    }

    private fun getAllUsersDetailWhenChiledUpdatedOrAdded() {

        val databaseReference = Firebase(PreferenceName.FIREBASE_PATIENT_URL)
        val onlineRef = databaseReference.child(PreferenceName.FIREBASE_PATIENTS_TABLE_NAME)
        onlineRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                isToUpdateStatusOnly = false
                getAllUsersDetailFromAPI()
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                isToUpdateStatusOnly = true
                getAllUsersDetailFromAPI()
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {}

            override fun onCancelled(firebaseError: FirebaseError) {}
        })
    }

    private fun getAllUsersAPI() {
            if (!Utilz.isInternetConnected(activity)) {
                usersList.visibility = View.GONE
            } else {
                usersList.visibility = View.VISIBLE

                getUsersDetailsFromLocalDB(false)
            }

    }

    private fun getUsersDetailsFromLocalDB(b: Boolean) {
        usersDetailArrayList.clear()
        val realmResults = RealmController.getInstance().getAllUserDetails("", "")
        if (realmResults != null && realmResults.size > 0) {
            isToUpdateStatusOnly = true
            for (i in realmResults.indices) {
                val userDetails = UserDetails()
                Log.e("response: ", "keyUserName : " + realmResults[i].userName + "\nkeyPhoneNo : " + realmResults[i].userPhone + "\nkeyImage : " + realmResults[i].userImage +
                        "\nkeyLoc : " + realmResults[i].userLocation + "\nkeyStatus : " + realmResults[i].status)
                if (!realmResults[i].userPhone.equals(ClsGeneral.getPreferences(activity, PreferenceName.USER_MOBILE))) {
                    userDetails.userName = realmResults[i].userName
                    userDetails.userPhone = realmResults[i].userPhone
                    userDetails.userImage = realmResults[i].userImage
                    userDetails.userLocation = realmResults[i].userLocation
                    userDetails.status = realmResults[i].status
                    usersDetailArrayList.add(userDetails)
                }
            }
            if (usersDetailArrayList.size < 1) {
                noUsersText.visibility = View.VISIBLE
                usersList.visibility = View.GONE
            } else {
                noUsersText.visibility = View.GONE
                usersList.visibility = View.VISIBLE
                if (b) {
                    refreshAdapter()
                } else {
                    refreshUsersStatus(true)
                }
            }
        } else {
            isToUpdateStatusOnly = false
            getAllUsersDetailFromAPI()
        }
    }

    private fun refreshAdapter() {
        if (userAdapter != null) {
            refreshUsersStatus(false)
            userAdapter!!.notifyDataSetChanged()
        }
    }

    private fun refreshUsersStatus(status: Boolean) {
        if (userAdapter != null) {
            userAdapter!!.notifyStatusChanged(status)
        }
    }




    fun getAllUsersDetailFromAPI() {
        isApiCalling = true

        var web = Download_web(activity, object : OnTaskCompleted {
            override fun onTaskCompleted(response: String?) {
                try {
                    val obj = JSONObject(response)
                    val i = obj.keys()
                    var keyPhoneNo = ""
                    var keyImage = ""
                    var keyLoc = ""
                    var keyUserName = ""
                    var keyStatus = ""
                    usersDetailArrayList.clear()
                    while (i.hasNext()) {
                        var jsonObject: JSONObject? = null
                        if (obj != null) {
                            keyPhoneNo = i.next().toString()
                            if (!TextUtils.isEmpty(keyPhoneNo) && obj.has(keyPhoneNo)) {
                                jsonObject = obj.optJSONObject(keyPhoneNo)
                            }
                            val userDetails = UserDetails()
                            if (jsonObject != null) {
                                if (jsonObject.has("name"))
                                    keyUserName = jsonObject.optString("name")
                                if (jsonObject.has("image"))
                                    keyImage = jsonObject.optString("image")
                                if (jsonObject.has("location"))
                                    keyLoc = jsonObject.optString("location")
                                if (jsonObject.has("status"))
                                    keyStatus = jsonObject.optString("status")
                                userDetails.userName = keyUserName
                                userDetails.userPhone = keyPhoneNo
                                userDetails.userImage = keyImage
                                userDetails.userLocation = keyLoc
                                userDetails.status = keyStatus
                            }
                            usersDetailArrayList.add(userDetails)

                            totalUsers++
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                if (totalUsers <= 1) {
                    noUsersText.visibility = View.VISIBLE
                    usersList.visibility = View.GONE
                } else {
                    try {
                        noUsersText.visibility = View.GONE
                        usersList.visibility = View.VISIBLE
                        RealmController.getInstance().insertUpdateUserDetails(usersDetailArrayList)
                        if (isToUpdateStatusOnly)
                            getUsersDetailsFromLocalDB(false)
                        else
                            getUsersDetailsFromLocalDB(true)
                    }
                    catch (e: Exception){
                        if (isToUpdateStatusOnly)
                            getUsersDetailsFromLocalDB(false)
                        else
                            getUsersDetailsFromLocalDB(true)
                    }
                }

                isApiCalling = false
            }
        })

        web.setReqType(true)
        web.execute(PreferenceName.FIREBASE_PATIENTS_LIST_URL)
    }
}