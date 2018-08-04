package com.medexpertz.medexpertzdoctor.shankar.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.util.Constants;
import com.medexpertz.medexpertzdoctor.R;
import com.medexpertz.medexpertzdoctor.shankar.Utilz.PreferenceName;
import com.medexpertz.medexpertzdoctor.shankar.Utilz.Utils;
import com.medexpertz.medexpertzdoctor.shankar.model.UserDetails;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Welcome on 8/18/2017.
 */

public class UsersAdapter extends BaseAdapter {
    ArrayList<UserDetails> user_list;
    Context mContext;
    AQuery aq;
    boolean isToUpdateStatusOnly = false;

    public UsersAdapter(Context context, ArrayList<UserDetails> user_list) {
//        super(context,0,list);
        this.mContext = context;
        this.user_list = user_list;
        aq = new AQuery(context);
        isToUpdateStatusOnly = false;
    }

    @Override
    public int getCount() {
        return user_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.firebaseuser_list_item, parent, false);
            mHolder.userName = (TextView) convertView.findViewById(R.id.userName);
            mHolder.userStatus = (TextView) convertView.findViewById(R.id.userStatus);
            mHolder.statusImageView = (ImageView) convertView.findViewById(R.id.statusImageView);
            mHolder.userImage = (CircleImageView) convertView.findViewById(R.id.userImage);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if (user_list != null && user_list.size() > 0) {
            String user_name = "", user_image = "", userStatus = "", user_phone = "";
            if (isToUpdateStatusOnly) {
                user_name = user_list.get(position).getUserName();
                user_phone = user_list.get(position).getUserPhone();
                userStatus = user_list.get(position).getStatus();
                mHolder.userName.setText(user_name);
                if (!TextUtils.isEmpty(userStatus) && !userStatus.equalsIgnoreCase(PreferenceName.ONLINE)) {
                    userStatus = Utils.getLastSeenDate(userStatus);
                }
                if (!TextUtils.isEmpty(user_phone))
                    user_phone = user_phone.substring(6);
                mHolder.userStatus.setText("*******" + user_phone + " | " + userStatus);
                if (userStatus != null && userStatus.equalsIgnoreCase(PreferenceName.ONLINE)) {
                    mHolder.userStatus.setText(Html.fromHtml("*******" + user_phone + "<font color=#FF4081> | </font>" + "<font color=#05e101> " + userStatus + "</font>"));
                    mHolder.statusImageView.setImageResource(R.drawable.online_circle_icon);
                } else
                    mHolder.statusImageView.setImageResource(R.drawable.offline_circle_icon);
            } else {
                user_name = user_list.get(position).getUserName();
                user_image = user_list.get(position).getUserImage();
                user_phone = user_list.get(position).getUserPhone();
                userStatus = user_list.get(position).getStatus();
                Log.i("Adapter", "keyUserName : " + user_name + "\nkeyPhoneNo : " + user_phone + "\nkeyImage : " + user_image + "\nkeyStatus : " + userStatus);

                if (!TextUtils.isEmpty(userStatus) && !userStatus.equalsIgnoreCase(PreferenceName.ONLINE)) {
                    userStatus = Utils.getLastSeenDate(userStatus);
                }
                if (!TextUtils.isEmpty(user_phone))
                    user_phone = user_phone.substring(6);
                mHolder.userName.setText(user_name);
                mHolder.userStatus.setText("*******" + user_phone + " | " + userStatus);
                mHolder.userImage.setImageResource(R.mipmap.app_icon);
                if (!TextUtils.isEmpty(user_image)) {
                    if (user_image.contains("http://graph.facebook.com/"))
                        aq.id(mHolder.userImage).image(user_image, true, true, 0, R.mipmap.app_icon, null, Constants.FADE_IN);
                    else{}
                      //  Utils.decodeImageFromBase64(user_image, mHolder.userImage);
                } else {
                    mHolder.userImage.setImageResource(R.mipmap.app_icon);
                }

                if (userStatus != null && userStatus.equalsIgnoreCase(PreferenceName.ONLINE)) {
                    mHolder.userStatus.setText(Html.fromHtml("*******" + user_phone + "<font color=#FF4081> | </font>" + "<font color=#05e101> " + userStatus + "</font>"));
                    mHolder.statusImageView.setImageResource(R.drawable.online_circle_icon);
                } else
                    mHolder.statusImageView.setImageResource(R.drawable.offline_circle_icon);

                final String finalUser_image = user_image;
               /* mHolder.userImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(finalUser_image)) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            Bitmap mBitmap = Utils.getUserImageBitmap(finalUser_image);
                            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            Intent intent = new Intent(mContext, ParticularImageActivity.class);
                            intent.putExtra("particularImage", byteArray);
                            intent.putExtra("isBitmap", true);
                            mContext.startActivity(intent);
                        }
                    }
                });*/
            }
        }
        return convertView;
    }

    public void notifyStatusChanged(boolean isToUpdateStatusOnly) {
        this.isToUpdateStatusOnly = isToUpdateStatusOnly;
    }


    class ViewHolder {
        TextView userName, userStatus;
        CircleImageView userImage;
        ImageView statusImageView;
    }
}
