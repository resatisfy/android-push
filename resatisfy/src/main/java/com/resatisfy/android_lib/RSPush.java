package com.resatisfy.android_lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.resatisfy.android_lib.controllers.RSHttpConnection;
import com.resatisfy.android_lib.utilities.RSConfig;
import com.resatisfy.android_lib.utilities.RSHttpsInterface;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;



public class RSPush implements RSHttpsInterface {

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    private static String rsChannelId;

    public static void takeOff(Context context){
        RSConfig getConfig = RSConfig.defaultConfig(context);
        try {
            if(getConfig.getAppKey().length() > 0){
                channelIdCreationSteo(context);
                RSPush.registerForPushNotifications(context,getConfig);
            }
        } catch (Exception e) {
            Log.e("RSPush","rsconfig.properties not set up properly!");
        }
    }


    private static void channelIdCreationSteo(Context context){
        sharedPref = context.getSharedPreferences("resatisfy_session", Context.MODE_PRIVATE);
        editor =  sharedPref.edit();
        rsChannelId = sharedPref.getString("rsChannelIdSelimDevelopMentKey", "");
        if(rsChannelId.length() == 0){
            rsChannelId = RSPush.createChannel();
            editor.putString("rsChannelIdSelimDevelopMentKey",rsChannelId);
            editor.commit();
        }
    }


    public static String getProperty(String key,Context context) throws IOException {
        Properties properties = new Properties();;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("rsconfig.properties");
        properties.load(inputStream);

        return properties.getProperty(key);
    }


    static void registerDevice(final Context context, RSConfig rsConfig, String token){

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("appKey", rsConfig.getAppKey());
        builder.appendQueryParameter("appSecret", rsConfig.getAppSecret());
        builder.appendQueryParameter("deviceType", "android");
        builder.appendQueryParameter("deviceToken",token);
        builder.appendQueryParameter("fcmSenderId",rsConfig .getSenderId());
        builder.appendQueryParameter("iosAndroidId",context.getPackageName());
        builder.appendQueryParameter("channelId",rsChannelId);

        String postQuery = builder.build().getEncodedQuery();

        RSPush myPush = new RSPush();
        new RSHttpConnection("post-register-device", postQuery,myPush).execute();

    }


    @Override
    public void RSHttpsCompletion(JSONObject json) {
        try{
            String getStatus = json.getString("status");
            if(getStatus.equals("success")) {
                String getIsActive = json.getString("isActive");
                String rsChannelStatus = sharedPref.getString("rsChannelStatus", "");
                if(!rsChannelStatus.isEmpty() && getIsActive.equals(rsChannelStatus)){

                }else{
                    editor.putString("rsChannelStatus", getIsActive);
                    editor.commit();
                }
            } else if (!json.getString("msg").isEmpty()){
                Log.e("RSPush",json.getString("msg") );
            }
        }catch (Exception e){}
    }

    public static String channelId(Context context){
        channelIdCreationSteo(context);
        SharedPreferences sharedPref = context.getSharedPreferences("resatisfy_session", Context.MODE_PRIVATE);
        return sharedPref.getString("rsChannelIdSelimDevelopMentKey", "");
    }

    public static String getChannelStatus(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("resatisfy_session", Context.MODE_PRIVATE);
        String rsChannelStatus = sharedPref.getString("rsChannelStatus", "");

        if(rsChannelStatus.isEmpty()){
            return "active";
        }else{
            return rsChannelStatus;
        }
    }

    public static String getDeviceStatus(Context context){
        Boolean isNotificationenable = NotificationManagerCompat.from(context)
                .areNotificationsEnabled();

        if(isNotificationenable){
            return "active";
        }else{
            return "Push notification is disabled from your iPhone settings!";
        }
    }

    private static String createChannel(){
        final String uuid = UUID.randomUUID().toString();
        return uuid;
    }


    static void registerForPushNotifications(Context context, RSConfig rsConfig){

        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            if(!token.isEmpty()){
                RSPush.registerDevice(context,rsConfig,token);
            }
        } catch (Exception e) {
            Log.e("RSPush","rsconfig.properties not set up properly!");
        }

    }











}
