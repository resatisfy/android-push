package com.resatisfy.android_lib.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RSConfig {

    private String developmentAppKey; private String developmentAppSecret;
    private String productionAppKey; private String productionAppSecret;
    private String fcmSenderId; private Boolean isProduction;


    public static RSConfig defaultConfig(Context context){
        RSConfig myConfig = new RSConfig();
        SharedPreferences sharedPref = context.getSharedPreferences("RSFlight_session", Context.MODE_PRIVATE);
        String RSFlightAppKey = sharedPref.getString("RSFlightAppKey", "");
        if(RSFlightAppKey.length() == 0){
            try {
                myConfig.developmentAppKey = getProperty("developmentAppKey",context);
                myConfig.developmentAppSecret = getProperty("developmentAppSecret",context);
                myConfig.productionAppKey = getProperty("productionAppKey",context);
                myConfig.productionAppSecret = getProperty("productionAppSecret",context);
                myConfig.fcmSenderId = getProperty("fcmSenderId",context);
            } catch (IOException e) {
                Log.e("RSPush","rsconfig.properties not set up properly!");
            }
        }else{
            String RSFlightAppSecret = sharedPref.getString("RSFlightAppSecret", "");
            String RSFlightGSMSenderId = sharedPref.getString("RSFlightGSMSenderId", "");

            myConfig.developmentAppKey = RSFlightAppKey;
            myConfig.developmentAppSecret = RSFlightAppSecret;
            myConfig.productionAppKey = RSFlightAppKey;
            myConfig.productionAppSecret = RSFlightAppSecret;
            myConfig.fcmSenderId = RSFlightGSMSenderId;
        }


        boolean isDebuggable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        if(isDebuggable){
            myConfig.isProduction = false;
        }else{
            myConfig.isProduction = true;
        }

        return myConfig;
    }


    public static String getProperty(String key,Context context) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("rsconfig.properties");
        properties.load(inputStream);

        return properties.getProperty(key);
    }

    public String getAppKey(){
        if(this.isProduction){
            return this.productionAppKey;
        }else{
            return this.developmentAppKey;
        }
    }

    public String getAppSecret(){
        if(this.isProduction){
            return this.productionAppSecret;
        }else{
            return this.developmentAppSecret;
        }
    }

    public String getSenderId(){
        return this.fcmSenderId;
    }





}
