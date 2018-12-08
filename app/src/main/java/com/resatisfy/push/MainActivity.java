package com.resatisfy.push;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.resatisfy.android_lib.RSPush;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());
        RSPush.takeOff(getApplicationContext());

//        System.out.println("---------------------------------AS");
//        System.out.println(RSPush.channelId(getApplicationContext()));

        //this.deactiveChannel(RSPush.channelId(getApplicationContext()));


    }




}
