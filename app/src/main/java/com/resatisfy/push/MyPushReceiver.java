package com.resatisfy.push;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.resatisfy.android_lib.controllers.RSPushReciver;

import java.util.List;


public class MyPushReceiver extends FirebaseMessagingService {
    public static final String TAG = "MsgFirebaseServ";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        RemoteMessage.Notification myNoti = remoteMessage.getNotification();
        System.out.println(myNoti.getTitle());

//        Intent intent = new Intent(this, indexController.class);
//        intent.putExtra("rsTitle",myNoti.getTitle());
//        intent.putExtra("rsMessage",myNoti.getBody());
//        startActivity(intent);
//
//        String user_email = remoteMessage.getData().get("user_email");
//        System.out.println(user_email);


    }





}
