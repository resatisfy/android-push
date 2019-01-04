package com.resatisfy.android_lib;

import android.content.Context;
import android.content.SharedPreferences;


import com.resatisfy.android_lib.controllers.RSExceptionHandler;
import com.resatisfy.android_lib.utilities.reportHttpsAction;
import com.resatisfy.android_lib.utilities.reportHttpsInterface;


import org.json.JSONObject;



public class RSCrashReporting implements reportHttpsInterface {

    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public static void takeOff(Context context){
        RSCrashReporting rsCrashReporting = new RSCrashReporting();
        rsCrashReporting.context = context;

        Thread.setDefaultUncaughtExceptionHandler(new RSExceptionHandler(context));



        rsCrashReporting.sharedPref = context.getSharedPreferences("RSCrashReporting_session", Context.MODE_PRIVATE);
        String RSCrashReporting_lastCrashData = rsCrashReporting.sharedPref.getString("RSCrashReporting_lastCrashData", "");

        if(!RSCrashReporting_lastCrashData.isEmpty()){
            rsCrashReporting.recordReports(RSCrashReporting_lastCrashData);
        }
    }

    public void recordReports(String sendData){
        editor = sharedPref.edit();
        editor.remove("RSCrashReporting_lastCrashData");
        editor.commit();

        //send data to server
        new reportHttpsAction(sendData,this).execute();

        //show dialog
        System.out.println("show dialog");
        //Context asjdh = this.context;
    }


    @Override
    public void reportHttpsCompletion(JSONObject jsonObject) {

        System.out.println("------------Crash Reporting Response--------------");
        System.out.println(jsonObject);

    }

}










