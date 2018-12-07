package com.resatisfy.android_lib.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class RSPushReciver extends BroadcastReceiver {
    private static final String TAG = "RSPush : ";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.d(TAG, "Received intent null");
        } else {
            processPush(context, intent);
        }
    }

    private void processPush(Context context, Intent intent) {

        //process data
//        String title = intent.getExtras().getString("title");
//        String msg = intent.getExtras().getString("msg");
//
//        System.out.println("--------------------------");
//        System.out.println(title);
//        System.out.println(msg);

        //this.launchSomeActivity(context,title);
        //this.sendNotification(context,title);

    }







}