package com.resatisfy.app;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.resatisfy.android.RSPushReciver;


public class MyPushReceive extends RSPushReciver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.d("RSPush : ", "Received intent null");
        } else {
            processPush(context, intent);
        }
    }

    private void processPush(Context context, Intent intent) {

        //process data
        String title = intent.getExtras().getString("title");
        String msg = intent.getExtras().getString("msg");

        System.out.println(title);
        System.out.println(msg);

        //this.launchSomeActivity(context,title);


    }



    private void launchSomeActivity(Context context, String datavalue) {
        Intent pupInt = new Intent(context, MainActivity.class);
        pupInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pupInt.putExtra("data", datavalue);
        context.getApplicationContext().startActivity(pupInt);
    }


    private void triggerBroadcastToActivity(Context context, String datavalue) {
        Intent intent = new Intent();
        intent.putExtra("data", datavalue);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }












}