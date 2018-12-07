package com.resatisfy.android_lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.resatisfy.android_lib.models.RSDeviceRegModel;
import com.resatisfy.android_lib.utilities.RSConfig;
import com.resatisfy.android_lib.utilities.RSInterface;
import com.resatisfy.android_lib.utilities.RSSettings;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RSPush {

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    private static String rsChannelId;

    public static void takeOff(Context context){
        sharedPref = context.getSharedPreferences("resatisfy_session", Context.MODE_PRIVATE);
        editor =  sharedPref.edit();
        rsChannelId = sharedPref.getString("rsChannelId", "");
        if(rsChannelId.length() == 0){
            rsChannelId = RSPush.createChannel();
            editor.putString("rsChannelId",rsChannelId);
            editor.commit();
        }

        RSConfig getConfig = RSConfig.defaultConfig(context);
        RSPush.registerForPushNotifications(context,getConfig);
    }


    public static String getProperty(String key,Context context) throws IOException {
        Properties properties = new Properties();;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("rsconfig.properties");
        properties.load(inputStream);

        return properties.getProperty(key);
    }


    static void registerDevice(final Context context, RSConfig rsConfig, String token){

        Retrofit retrfit=new Retrofit.Builder()
                .baseUrl(RSSettings.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RSInterface PostRest=retrfit.create(RSInterface.class);

        RSDeviceRegModel postData=new RSDeviceRegModel();
        postData.setAppKey(rsConfig.getAppKey());
        postData.setAppSecret(rsConfig.getAppSecret());
        postData.setDeviceToken(token);
        postData.setDeviceType("android");
        postData.setFcmSenderId(rsConfig.getSenderId());
        postData.setIosAndroidId(context.getPackageName());
        postData.setChannelId(rsChannelId);


        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        Call<RSDeviceRegModel> call = PostRest.post_register_device(postData);
        call.enqueue(new Callback<RSDeviceRegModel>() {
            @Override
            public void onResponse(Call<RSDeviceRegModel> call, Response<RSDeviceRegModel> response) {
                if(response.isSuccessful()){
                    RSDeviceRegModel getRes=response.body();
                    String getStatus=getRes.getStatus();
                    
                    if(getStatus.equals("success")) {
                        String getIsActive = getRes.getIsActive();
                        String rsChannelStatus = sharedPref.getString("rsChannelStatus", "");
                        if(!rsChannelStatus.isEmpty() && getIsActive.equals(rsChannelStatus)){

                        }else{
                            editor.putString("rsChannelStatus", getIsActive);
                            editor.commit();
                        }
                    } else if (!getRes.getMsg().isEmpty()){
                        Log.e("RSPush",getRes.getMsg());
                    }
                }
            }
            @Override
            public void onFailure(Call<RSDeviceRegModel> call, Throwable t) {
                Log.e("RSPush","api server error!");
            }
        });

    }

    public static String channelId(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("resatisfy_session", Context.MODE_PRIVATE);
        String rsChannelId = sharedPref.getString("rsChannelId", "");

        if(rsChannelId.isEmpty()){
            return "RSPush : wait until device registration completed!";
        }else{
            return rsChannelId;
        }
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
