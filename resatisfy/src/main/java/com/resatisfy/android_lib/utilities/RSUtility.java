package com.resatisfy.android_lib.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.resatisfy.android_lib.RSPush;
import com.resatisfy.android_lib.controllers.RSHttpConnection;

import org.json.JSONObject;


public class RSUtility implements RSHttpsInterface {

    public static void deactiveChannel(Context context){
        String channelId = RSPush.channelId(context);
        if(channelId.isEmpty()){
            Log.e("RSPush", "Channel Id is empty.");
        }else{
            RSConfig getConfig = RSConfig.defaultConfig(context);
            if(getConfig.getAppKey().isEmpty()){
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
                //
                System.out.println("yes success----------");
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
            if(getConfig.getAppKey().isEmpty()){
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
            if(getConfig.getAppKey().isEmpty()){
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







}
