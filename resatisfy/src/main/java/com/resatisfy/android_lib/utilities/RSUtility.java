package com.resatisfy.android_lib.utilities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.resatisfy.android_lib.RSPush;
import com.resatisfy.android_lib.controllers.RSHttpConnection;

import org.json.JSONObject;

import java.util.List;


public class RSUtility implements RSHttpsInterface {

    public static void deactiveChannel(Context context){
        String channelId = RSPush.channelId(context);
        if(channelId.isEmpty()){
            Log.e("RSPush", "Channel Id is empty.");
        }else{
            RSConfig getConfig = RSConfig.defaultConfig(context);
            if(getConfig.getAppKey() == null){
                Log.e("RSPush","config error!");
            }else{
                SharedPreferences sharedPref = context.getSharedPreferences("resatisfy_session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =  sharedPref.edit();
                editor.putString("rsChannelStatus", "inactive");
                editor.commit();
                deactiveChannelAction(channelId, getConfig);
            }
        }
    }

    private static void deactiveChannelAction(String channelId, RSConfig rsConfig){

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("appKey", rsConfig.getAppKey());
        builder.appendQueryParameter("appSecret", rsConfig.getAppSecret());
        builder.appendQueryParameter("deviceType", "android");
        builder.appendQueryParameter("channelId",channelId);

        String postQuery = builder.build().getEncodedQuery();

        RSUtility cBack = new RSUtility();
        new RSHttpConnection("deactive-channel", postQuery,cBack).execute();

    }

    @Override
    public void RSHttpsCompletion(JSONObject json) {
        try{
            String getStatus = json.getString("status");

            if(getStatus.equals("success")) {
                //System.out.println("yes success----------");
            } else if (!json.getString("msg").isEmpty()){
                Log.e("RSPush",json.getString("msg") );
            }
        }catch (Exception e){}
    }


    public static void activeChannel(Context context){
        String channelId = RSPush.channelId(context);
        if(channelId.isEmpty()){
            Log.e("RSPush", "Channel Id is empty.");
        }else{
            RSConfig getConfig = RSConfig.defaultConfig(context);
            if(getConfig.getAppKey() == null){
                Log.e("RSPush","config error!");
            }else {
                SharedPreferences sharedPref = context.getSharedPreferences("resatisfy_session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =  sharedPref.edit();
                editor.putString("rsChannelStatus", "active");
                editor.commit();
                activeChannelAction(channelId, getConfig);
            }
        }
    }



    private static void activeChannelAction(String channelId, RSConfig rsConfig){

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("appKey", rsConfig.getAppKey());
        builder.appendQueryParameter("appSecret", rsConfig.getAppSecret());
        builder.appendQueryParameter("deviceType", "android");
        builder.appendQueryParameter("channelId",channelId);

        String postQuery = builder.build().getEncodedQuery();

        RSUtility cBack = new RSUtility();
        new RSHttpConnection("active-channel", postQuery,cBack).execute();

    }



    public static void deleteChannel(Context context){
        String channelId = RSPush.channelId(context);
        if(channelId.isEmpty()){
            Log.e("RSPush", "Channel Id is empty.");
        }else{
            RSConfig getConfig = RSConfig.defaultConfig(context);
            if(getConfig.getAppKey() == null){
                Log.e("RSPush","config error!");
            }else {
                deleteChannelAction(channelId, getConfig);
            }
        }
    }

    private static void deleteChannelAction(String channelId, RSConfig rsConfig){

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("appKey", rsConfig.getAppKey());
        builder.appendQueryParameter("appSecret", rsConfig.getAppSecret());
        builder.appendQueryParameter("deviceType", "android");
        builder.appendQueryParameter("channelId",channelId);

        String postQuery = builder.build().getEncodedQuery();

        RSUtility cBack = new RSUtility();
        new RSHttpConnection("delete-channel", postQuery,cBack).execute();

    }


    public boolean isAppForgroundRS(Context mContext) {

        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }

        return true;
    }


    public static void RSPushProcess(Context context, Intent intent){
        Bundle getExtras = intent.getExtras();
        if (getExtras != null) {
            if (getExtras.containsKey("thisPushId") && getExtras.containsKey("thisPushType")) {
                String pushId = intent.getExtras().getString("thisPushId");
                String type = intent.getExtras().getString("thisPushType");
                postAppOpened(context,pushId,type);
            }
        }
    }

    private static void postAppOpened(Context context, String pushId, String type){
        String channelId = RSPush.channelId(context);
        if(channelId.isEmpty()){
            Log.e("RSPush", "Channel Id is empty.");
        }else{
            RSConfig getConfig = RSConfig.defaultConfig(context);
            if(getConfig.getAppKey() == null){
                Log.e("RSPush","config error!");
            }else {
                postAppOpenedAction(context, channelId, getConfig, pushId, type);
            }
        }
    }

    private static void postAppOpenedAction(Context context, String channelId, RSConfig rsConfig, String pushId, String type){

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("appKey", rsConfig.getAppKey());
        builder.appendQueryParameter("appSecret", rsConfig.getAppSecret());
        builder.appendQueryParameter("deviceType", "android");
        builder.appendQueryParameter("channelId",channelId);
        builder.appendQueryParameter("pushId",pushId);
        builder.appendQueryParameter("recordType",type);
        builder.appendQueryParameter("package",context.getPackageName());

        String postQuery = builder.build().getEncodedQuery();

        RSUtility cBack = new RSUtility();
        new RSHttpConnection("app-open-action", postQuery,cBack).execute();

    }







}
