package com.resatisfy.push;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.resatisfy.android_lib.controllers.RSPushReciver;

import java.util.List;


public class MyPushReceiver extends RSPushReciver {


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


        if (isAppForground(context)) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("title",title);
            i.putExtra("msg",msg);
            context.startActivity(i);
        } else {
            // App is in Background
        }


    }




    public boolean isAppForground(Context mContext) {

        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }

        return true;
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