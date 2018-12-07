package com.resatisfy.android_lib.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.resatisfy.android_lib.models.RSChannelModel;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RSUtility {

    public static void deactiveChannel(Context context,String channelId){
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
        Retrofit retrfit=new Retrofit.Builder()
                .baseUrl(RSSettings.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RSInterface PostRest=retrfit.create(RSInterface.class);

        RSChannelModel postData=new RSChannelModel();
        postData.setAppKey(rsConfig.getAppKey());
        postData.setAppSecret(rsConfig.getAppSecret());
        postData.setDeviceType("android");
        postData.setChannelId(channelId);


        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        Call<RSChannelModel> call = PostRest.post_deactive_channel(postData);
        call.enqueue(new Callback<RSChannelModel>() {
            @Override
            public void onResponse(Call<RSChannelModel> call, Response<RSChannelModel> response) {
                if(response.isSuccessful()){
                    RSChannelModel getRes=response.body();
                    String getStatus=getRes.getStatus();

                    if(getStatus.equals("success")) {
                        //Log.d("RSPush", "");
                    } else if (!getRes.getMsg().isEmpty()){
                        Log.e("RSPush",getRes.getMsg());
                    }
                }
            }
            @Override
            public void onFailure(Call<RSChannelModel> call, Throwable t) {
                Log.e("RSPush","api server error!");
            }
        });
    }


    public static void activeChannel(Context context,String channelId){
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
        Retrofit retrfit=new Retrofit.Builder()
                .baseUrl(RSSettings.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RSInterface PostRest=retrfit.create(RSInterface.class);

        RSChannelModel postData=new RSChannelModel();
        postData.setAppKey(rsConfig.getAppKey());
        postData.setAppSecret(rsConfig.getAppSecret());
        postData.setDeviceType("android");
        postData.setChannelId(channelId);


        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        Call<RSChannelModel> call = PostRest.post_active_channel(postData);
        call.enqueue(new Callback<RSChannelModel>() {
            @Override
            public void onResponse(Call<RSChannelModel> call, Response<RSChannelModel> response) {
                if(response.isSuccessful()){
                    RSChannelModel getRes=response.body();
                    String getStatus=getRes.getStatus();
                    if(getStatus.equals("success")) {
                        //Log.d("RSPush", "");
                    } else if (!getRes.getMsg().isEmpty()){
                        Log.e("RSPush",getRes.getMsg());
                    }
                }
            }
            @Override
            public void onFailure(Call<RSChannelModel> call, Throwable t) {
                Log.e("RSPush","api server error!");
            }
        });
    }














}
