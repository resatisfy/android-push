package com.resatisfy.android_lib.controllers;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

public class RSPushReciver extends FirebaseMessagingService {
    public static final String TAG = "MsgFirebaseServ";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification myNoti = remoteMessage.getNotification();

        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");

        System.out.println("----------------------");
        System.out.println(myNoti.getTitle());

        Map myData = remoteMessage.getData();


        JSONObject json = new JSONObject(myData);
        System.out.println("----------------------");
        System.out.println("----------------------");



    }





}
