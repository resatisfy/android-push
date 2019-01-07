package com.resatisfy.android_lib;

import android.content.Context;
import com.resatisfy.android_lib.controllers.RSExceptionHandler;

public class RSCrashReporting {

    private Context context;

    public static void takeOff(Context context){

        RSCrashReporting rsCrashReporting = new RSCrashReporting();
        rsCrashReporting.context = context;

        Thread.setDefaultUncaughtExceptionHandler(new RSExceptionHandler(context));
    }












}










