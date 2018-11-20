package com.resatisfy.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

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


    public static void takeOff(Context context){
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


    static void registerDevice(Context context, RSConfig rsConfig, String token){

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
        postData.setChannelId(RSPush.createChannel());


        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        Call<RSDeviceRegModel> call = PostRest.post_register_device(postData);
        call.enqueue(new Callback<RSDeviceRegModel>() {
            @Override
            public void onResponse(Call<RSDeviceRegModel> call, Response<RSDeviceRegModel> response) {
                if(response.isSuccessful()){
                    RSDeviceRegModel getRes=response.body();
                    String getStatus=getRes.getStatus();
                    
                    System.out.println("/////////////////////////");
                    System.out.println(getStatus);
                    System.out.println(getRes.getChannelId());

                    if(getStatus.equals("success")) {

                    } else if (!getRes.getMsg().isEmpty()){
                        Log.e("RSPush",getRes.getMsg());
                    }
                }
            }
            @Override
            public void onFailure(Call<RSDeviceRegModel> call, Throwable t) {

            }
        });

    }

    private static String createChannel(){
        final String uuid =UUID.randomUUID().toString();
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
