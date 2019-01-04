package com.resatisfy.push;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.resatisfy.android_lib.RSCrashReporting;
import com.resatisfy.android_lib.RSPush;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());
        RSPush.takeOff(getApplicationContext());


        RSCrashReporting.takeOff(getApplicationContext());


    }


    public void subjectClicked(View view){

        throw new RuntimeException("This is a crash");

    }

    public void subjectClicked3(View view){

        Intent intent = new Intent(this,ScndActivity.class);
        startActivity(intent);

    }



}






