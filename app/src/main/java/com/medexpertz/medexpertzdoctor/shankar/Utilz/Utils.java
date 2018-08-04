package com.medexpertz.medexpertzdoctor.shankar.Utilz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.medexpertz.medexpertzdoctor.etc.model.Preference;
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

	private Context _context;

	// constructor
	public Utils(Context context) {
		this._context = context;
	}
	public int getScreenWidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) _context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.x;
		return columnWidth;
	}

	@Nullable
	public static String getCurrentDate() {
		Date c = Calendar.getInstance().getTime();
		System.out.println("Current time => " + c);

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String formattedDate = df.format(c);

		return formattedDate;
	}

	public static String getFirebaseCurrentDate() {
		String currentDate = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat("MMMM dd, h:mm a");
			Date cal = new Date();
			currentDate = dateFormat.format(cal);
			Log.i("ChatActivity", "currentDate: " + currentDate);
			return currentDate;
		} catch (Exception e) {
			e.printStackTrace();
			return currentDate;
		}
	}

	public static void updateUserStatus(Activity activity, String currentStatus) {
		Firebase mObjFireBaseRef = new Firebase(PreferenceName.FIREBASE_PATIENT_URL+PreferenceName.FIREBASE_PATIENTS_TABLE_NAME);
		mObjFireBaseRef.child(PreferenceName.FIREBASE_PATIENTS_TABLE_NAME).child(ClsGeneral.getPreferences(activity, PreferenceName.USER_MOBILE)).child("status").setValue(currentStatus);
	}
	public static String getLastSeenDate(String dateAndTime) {
		if (!TextUtils.isEmpty(dateAndTime)) {
			if (dateAndTime.contains("January")) {
				dateAndTime = dateAndTime.replace("January", "Jan");
			} else if (dateAndTime.contains("February")) {
				dateAndTime = dateAndTime.replace("February", "Feb");
			} else if (dateAndTime.contains("March")) {
				dateAndTime = dateAndTime.replace("March", "Mar");
			} else if (dateAndTime.contains("April")) {
				dateAndTime = dateAndTime.replace("April", "Apr");
			} else if (dateAndTime.contains("May")) {
				dateAndTime = dateAndTime.replace("May", "May");
			} else if (dateAndTime.contains("June")) {
				dateAndTime = dateAndTime.replace("June", "Jun");
			} else if (dateAndTime.contains("July")) {
				dateAndTime = dateAndTime.replace("July", "Jul");
			} else if (dateAndTime.contains("August")) {
				dateAndTime = dateAndTime.replace("August", "Aug");
			} else if (dateAndTime.contains("September")) {
				dateAndTime = dateAndTime.replace("September", "Sep");
			} else if (dateAndTime.contains("October")) {
				dateAndTime = dateAndTime.replace("October", "Oct");
			} else if (dateAndTime.contains("November")) {
				dateAndTime = dateAndTime.replace("November", "Nov");
			} else if (dateAndTime.contains("December")) {
				dateAndTime = dateAndTime.replace("December", "Dec");
			}
		}
		return dateAndTime;
	}



}
