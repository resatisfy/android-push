package com.resatisfy.push;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class ScndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scnd_activity);



    }


    public void subjectClicked2(View view){
        System.out.println("yes ---------------");

        throw new RuntimeException("This is a crash");

    }




}
