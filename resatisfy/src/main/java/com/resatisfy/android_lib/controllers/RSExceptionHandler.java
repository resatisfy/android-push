package com.resatisfy.android_lib.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;




public class RSExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Context context;
    private Thread.UncaughtExceptionHandler defaultUEH;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    public RSExceptionHandler(Context ctx) {
        sharedPref = ctx.getSharedPreferences("RSCrashReporting_session", Context.MODE_PRIVATE);
        context = ctx;
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread tread, Throwable error) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        error.printStackTrace(printWriter);
        printWriter.close();


        /////save reports
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("appKey","BuCkZk2onZ76jGQn7TGU79DtrHwVCFEf");
        builder.appendQueryParameter("appSecret","Busm9Skgx5DkYEHt6WtfQKwlTm1p2Q4A");
        builder.appendQueryParameter("deviceType","android");
        builder.appendQueryParameter("cause",error.getCause().toString());
        builder.appendQueryParameter("message",error.getMessage().toString());
        builder.appendQueryParameter("stackTrace",result.toString());


        editor=sharedPref.edit();
        editor.putString("RSCrashReporting_lastCrashData", builder.build().getEncodedQuery());
        editor.commit();

        defaultUEH.uncaughtException(tread, error);

    }







}



