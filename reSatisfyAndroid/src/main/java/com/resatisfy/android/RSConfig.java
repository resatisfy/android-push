package com.resatisfy.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RSConfig {

    private String developmentAppKey; private String developmentAppSecret;
    private String productionAppKey; private String productionAppSecret;
    private String fcmSenderId;


    public static RSConfig defaultConfig(Context context){
        RSConfig myConfig = new RSConfig();
        try {
            myConfig.developmentAppKey = getProperty("developmentAppKey",context);
            myConfig.developmentAppSecret = getProperty("developmentAppSecret",context);
            myConfig.productionAppKey = getProperty("productionAppKey",context);
            myConfig.productionAppSecret = getProperty("productionAppSecret",context);
            myConfig.fcmSenderId = getProperty("fcmSenderId",context);
        } catch (IOException e) {
            Log.e("RSPush","rsconfig.properties not set up properly!");
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
        if(this.getAppMode()){
            return this.productionAppKey;
        }else{
            return this.developmentAppKey;
        }
    }

    public String getAppSecret(){
        if(this.getAppMode()){
            return this.productionAppSecret;
        }else{
            return this.developmentAppSecret;
        }
    }

    public String getSenderId(){
        return this.fcmSenderId;
    }

    private static Boolean getAppMode(){
        if (BuildConfig.DEBUG) {
            return false;
        }else{
            return true;
        }
    }




}
