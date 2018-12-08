package com.resatisfy.push;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.resatisfy.android_lib.RSPush;
import com.resatisfy.android_lib.utilities.RSConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());
        RSPush.takeOff(getApplicationContext());



//        RSConfig getConfig = RSConfig.defaultConfig(getApplicationContext());
//
//        System.out.println("---------------------------------AS");
//        System.out.println(getConfig.getAppKey());

        //this.deactiveChannel(RSPush.channelId(getApplicationContext()));


    }




}
