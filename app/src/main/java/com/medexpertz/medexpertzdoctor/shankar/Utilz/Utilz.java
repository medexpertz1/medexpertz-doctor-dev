package com.medexpertz.medexpertzdoctor.shankar.Utilz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Utilz {
    static ProgressDialog dialog;
    public static final int HTTP_TIMEOUT = 60 * 1000; // milliseconds
    private static HttpClient mHttpClient;

    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    public static String executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                    postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String executeHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isInternetConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    public static boolean isValidEmail1(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            // check if no view has focus:
            View view = activity.getCurrentFocus();
            if (view != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            // Ignore exceptions if any
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }


    public static int getDateFromString(String dateStr) {
        int date = 0;
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedDate = DATE_FORMAT.parse(dateStr);
            date = parsedDate.getDate();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String calculatedistanceinkm(double latti, double longi, String latitude, String longitude) {


        double distance = 0, dist = 0;
        try {


            double theta = longi - Double.parseDouble(longitude);
            dist = Math.sin(deg2rad(latti)) * Math.sin(deg2rad(Double.parseDouble(latitude))) + Math.cos(deg2rad(latti)) * Math.cos(deg2rad(Double.parseDouble(latitude))) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("%.2f", dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public static void showProgress(Context context, String message) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }

        dialog = ProgressDialog.show(context, null, message, true, true);

    }

    public static void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void displayMessageAlert(String Message, Context context) {
        try {
            new AlertDialog.Builder(context).setMessage(Message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            }).create().show();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public static void logDisplay(String Message, Context context) {
        if (Message != null) {
            if (Message.trim().length() > 0 && context != null) {
                Log.d("PJ", Message);
            }
        }
    }



    public static void toastDisplay(String Message, Context con) {
        Toast toast = Toast.makeText(con, Message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static void HideSoftkeyPad(EditText textfiled, Context c) {
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textfiled.getWindowToken(), 0);
    }






    public static void showDailog(Context c, String msg) {
        dialog = new ProgressDialog(c);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(msg);
        dialog.show();
    }

    public static void closeDialog() {
        if (dialog != null)
            dialog.cancel();
    }

    public static String getCurrentDate(Context askQuestion) {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentTime(Context askQuestion) {
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        String currentDateTimeString = sdf.format(d);
        return currentDateTimeString;
    }

    public static String getCurrentDateInDigit(Context timeTableActivity) {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String todaysday(Context timeTableActivity) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }
    private static Calendar c;
    private static List<String> output;

    public static List<String> getCalendar()
    {
        c = Calendar.getInstance();
        output =  new ArrayList<String>();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        //Get current Day of week and Apply suitable offset to bring the new calendar
        //back to the appropriate Monday, i.e. this week or next
        switch (c.get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.MONDAY:
                c.add(Calendar.DATE,-1);
                break;

            case Calendar.TUESDAY:
                c.add(Calendar.DATE,-2);
                break;

            case Calendar.WEDNESDAY:
                c.add(Calendar.DATE, -3);
                break;

            case Calendar.THURSDAY:
                c.add(Calendar.DATE,-4);
                break;

            case Calendar.FRIDAY:
                c.add(Calendar.DATE,-5);
                break;

            case Calendar.SATURDAY:
                c.add(Calendar.DATE,-6);
                break;
        }

        //Add the Monday to the output
       // output.add(c.getTime().toString());
        for (int x = 1; x <=6; x++)
        {
            //Add the remaining days to the output
            c.add(Calendar.DATE,1);
            output.add(df.format(c.getTime()));
        }
        return output;
    }

}

