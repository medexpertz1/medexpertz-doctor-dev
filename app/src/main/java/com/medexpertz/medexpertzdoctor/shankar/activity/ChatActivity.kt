package com.bigappcompany.medexpertz.shankar.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.androidquery.AQuery
import com.firebase.client.*
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.Utilz.PreferenceName
import com.medexpertz.medexpertzdoctor.shankar.Utilz.Utils
import com.medexpertz.medexpertzdoctor.shankar.Utilz.Utilz
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.model.UserDetails
import kotlinx.android.synthetic.main.activity_chatt.*
import kotlinx.android.synthetic.main.message_area.*
import kotlinx.android.synthetic.main.right_chat_box.*
import java.io.ByteArrayOutputStream
import java.util.ArrayList
import java.util.HashMap
import java.util.prefs.Preferences

class ChatActivity: AppCompatActivity() {
     var reference1: Firebase?= null
     var reference2:Firebase?= null
     var aq: AQuery?= null
     var queryRef1: Query?= null
     var queryRef2:Query?= null
     var childEventListener1: ChildEventListener?= null
     var childEventListener2:ChildEventListener?= null
     var arrayListIndicatorLL: ArrayList<LinearLayout> = ArrayList()
     var arrayListIndicatorIV: ArrayList<ImageView> = ArrayList()
     var isHidden = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatt)

        if (UserDetails.onlineStatus.equals(PreferenceName.ONLINE))
            statusImageView.setImageResource(R.drawable.online_circle_icon)
        else
            statusImageView.setImageResource(R.drawable.offline_circle_icon)

        aq = AQuery(this)
        if (UserDetails.chatWithImage != null)
            userImageLeft.setImageBitmap(UserDetails.chatWithImage)
        else
            userImageLeft.setImageResource(R.mipmap.app_icon)

        val url = PreferenceName.FIREBASE_DOLCTOR_URL

        reference1 = Firebase(url + "messages/" + ClsGeneral.getPreferences(this, PreferenceName.USER_MOBILE) + "_" + UserDetails.chatWith)
        reference2 = Firebase(url + "messages/" + UserDetails.chatWith + "_" + ClsGeneral.getPreferences(this, PreferenceName.USER_MOBILE))

        sendButton.setOnClickListener {
            if (Utilz.isInternetConnected(this)) {
                sendData()
            } else {
                Toast.makeText(this, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
        }
        initialiseChildEventListener()
        readIncomingMessageAndShowOnTimeline(25)

    }

    private fun readIncomingMessageAndShowOnTimeline(msgCount: Int) {
        queryRef1 = reference1!!.limitToLast(msgCount)//gives last 25 chatting message
        queryRef2 = reference2!!.limitToLast(msgCount)//gives last 25 chatting message
        queryRef1!!.addChildEventListener(childEventListener1)
        queryRef2!!.addChildEventListener(childEventListener2)
    }

    private fun initialiseChildEventListener() {
        childEventListener1 = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String) {
                val map = dataSnapshot.getValue(Map::class.java)
                updateData(map, false)
                if (map != null) {
                    var userPhone = ""
                    if (map.containsKey("userPhone"))
                        userPhone = map["userPhone"].toString()
                    if (userPhone == UserDetails.chatWith) {
                        dataSnapshot.ref.child("isSeen").setValue("true")
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {
                val map = dataSnapshot.getValue(Map::class.java)
                updateData(map, true)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) { }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {

            }

            override fun onCancelled(firebaseError: FirebaseError) {

            }
        }

        childEventListener2 = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String) {
                val map = dataSnapshot.getValue(Map::class.java)
                if (map != null) {
                    var userPhone = ""
                    if (map.containsKey("userPhone"))
                        userPhone = map["userPhone"].toString()
                    if (userPhone == UserDetails.chatWith) {
                        dataSnapshot.ref.child("isSeen").setValue("true")
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {
            }

            override fun onCancelled(firebaseError: FirebaseError) {
            }
        }
    }

    private fun sendData() {
        val messageText = editEmojicon.text.toString()
        //When sending only text or emoji
        if (messageText != "" ) {
            val map = HashMap<String, String>()
            val timeStr = Utils.getFirebaseCurrentDate()
            map["message"] = messageText
            map["time"] = timeStr
            map["userName"] = ClsGeneral.getPreferences(this, PreferenceName.USER_NAME)
            map["userPhone"] = ClsGeneral.getPreferences(this, PreferenceName.USER_NAME)
            map["userImage"] = "icon url"
            //            map.put("userImage", Utils.getEncodedImageToBase64(Preferences.getUserImage(mActivity)));
            map["isImage"] = "false"
            map["isSeen"] = "false"
            map["isNotfcnSent"] = "false"
            reference1!!.push().setValue(map)
            reference2!!.push().setValue(map)
            editEmojicon.setText("")
            if (scrollView != null)
                scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun updateData(map: Map<*, *>?, isToUpdateSeenStatus: Boolean) {
        var message = ""
        var time = ""
        var isImage = ""
        var sentImage = ""
        var userPhone = ""
        var isSeen = ""
        if (map != null) {
            if (noConversationTxt != null)
                noConversationTxt.visibility = View.GONE
            if (map.containsKey("message"))
                message = map["message"].toString()
            if (map.containsKey("time"))
                time = map["time"].toString()
            if (map.containsKey("isImage"))
                isImage = map["isImage"].toString()
            if (map.containsKey("sentImage"))
                sentImage = map["sentImage"].toString()
            if (map.containsKey("userPhone"))
                userPhone = map["userPhone"].toString()
            if (map.containsKey("isSeen"))
                isSeen = map["isSeen"].toString()
            if (userPhone == ClsGeneral.getPreferences(this, PreferenceName.USER_MOBILE)) {
                addMessageBox(time, message, sentImage, isImage, isSeen, 2, isToUpdateSeenStatus)
            } else {
                addMessageBox(time, message, sentImage, isImage, isSeen, 1, isToUpdateSeenStatus)
            }
        }
    }

    fun addMessageBox(time: String, message: String, sentImage: String, isImage: String, isSeen: String, type: Int, isToUpdateSeenStatus: Boolean) {
        val left = layoutInflater.inflate(R.layout.left_chat_box, null)
        val right = layoutInflater.inflate(R.layout.right_chat_box, null)
        var textViewTime: TextView? = null
        var sentImageView: ImageView? = null
        if (isToUpdateSeenStatus) {
            if (!TextUtils.isEmpty(isSeen) && isSeen.equals("true", ignoreCase = true)) {
                updateAlAsSeen(arrayListIndicatorLL, arrayListIndicatorIV)
            }
            updateSeenIcon(isSeen)
        } else {
            if (type == 1) {
                textViewTime = left.findViewById(R.id.txt_time)
                sentImageView = left.findViewById(R.id.sentImageView)
                layout1.addView(left)
            } else {
                textViewTime = right.findViewById(R.id.txt_time)
                sentImageView = right.findViewById(R.id.sentImageView)
                //Seen Status Start
                updateSeenIcon(isSeen)
                layout1.addView(right)
                arrayListIndicatorLL.add(isMsgSeenIndicatorLL)
                arrayListIndicatorIV.add(isMsgSeenIndicatorImage)
            }
            if (txtEmojicon != null && !TextUtils.isEmpty(message))
                txtEmojicon.setText(message)
            if (textViewTime != null && !TextUtils.isEmpty(time))
                textViewTime!!.text = Utils.getLastSeenDate(time)
            if (!TextUtils.isEmpty(isImage) && isImage.equals("true", ignoreCase = true) && sentImageView != null) {
                sentImageView!!.visibility = View.VISIBLE
                txtEmojicon.setVisibility(View.GONE)
            } else if (sentImageView != null && txtEmojicon != null) {
                sentImageView!!.visibility = View.GONE
                txtEmojicon.setVisibility(View.VISIBLE)
            }

            if (scrollView != null) {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                scrollView.postDelayed({ scrollView.fullScroll(ScrollView.FOCUS_DOWN) }, 100L)
            }

            updateSeenIcon(isSeen)
        }
    }

    private fun updateAlAsSeen(arrayListIndicatorLL: ArrayList<LinearLayout>?, arrayListIndicatorIV: ArrayList<ImageView>?) {
        if (arrayListIndicatorLL != null && arrayListIndicatorIV != null && arrayListIndicatorLL.size > 2) {
            for (pos in 1 until arrayListIndicatorLL.size) {
                val linearLayout = arrayListIndicatorLL[pos - 1]
                val imageView = arrayListIndicatorIV[pos - 1]
                if (imageView != null && linearLayout != null) {
                    imageView.setImageResource(R.drawable.seen)
                    linearLayout.setBackgroundResource(R.drawable.seen_border_line)
                }
            }
        }
    }

    private fun handleShowImageClick(mBitmap: Bitmap, sentImageView: ImageView) {
        sentImageView.setOnClickListener {
            //Convert to byte array
            val stream = ByteArrayOutputStream()
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

          /*  val intent = Intent(this, ParticularImageActivity::class.java)
            intent.putExtra("particularImage", byteArray)
            intent.putExtra("isBitmap", true)
            startActivity(intent)*/
        }
    }

    private fun updateSeenIcon(isSeen: String) {
        if (isMsgSeenIndicatorImage != null && isMsgSeenIndicatorLL != null) {
            if (!TextUtils.isEmpty(isSeen) && isSeen.equals("false", ignoreCase = true)) {
                isMsgSeenIndicatorImage.setImageResource(R.drawable.not_seen)
                isMsgSeenIndicatorLL.setBackgroundResource(R.drawable.not_seen_border_line)
            } else {
                isMsgSeenIndicatorImage.setImageResource(R.drawable.seen)
                isMsgSeenIndicatorLL.setBackgroundResource(R.drawable.seen_border_line)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // todo: goto back activity from here
                onBackPressed()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        Firebase.goOnline()
        UserDetails.isChatting = true
            Utils.updateUserStatus(this, PreferenceName.ONLINE)
        //Utils.showLog(TAG, "MessageDetectorService is stopped");
        //stopService(new Intent(this, MessageDetectorService.class));
    }

    override fun onStop() {
        super.onStop()
        UserDetails.isChatting = false
            Utils.updateUserStatus(this, Utils.getFirebaseCurrentDate())
            Firebase.goOffline()
        //Utils.showLog(TAG, "MessageDetectorService is started");
        //startService(new Intent(this, MessageDetectorService.class));
    }

}