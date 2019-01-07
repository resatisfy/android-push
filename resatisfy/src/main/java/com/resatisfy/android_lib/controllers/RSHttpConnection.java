package com.resatisfy.android_lib.controllers;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.resatisfy.android_lib.utilities.RSHttpsInterface;
import com.resatisfy.android_lib.utilities.RSSettings;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RSHttpConnection extends AsyncTask<Void, Void, Void> {

    public JSONObject responseobject;
    public RSHttpsInterface callback;
    public String postUrl;
    public String postQuery;

    public RSHttpConnection(String postUrl, String postQuery, RSHttpsInterface callback) {
        this.callback = callback;
        this.postUrl = postUrl;
        this.postQuery = postQuery;
    }



    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(RSSettings.getApiUrl() + this.postUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(this.postQuery);
            writer.flush();
            writer.close();
            os.close();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            responseobject = new JSONObject(bufferedReader.readLine());

            conn.disconnect();
        }catch (Exception e){
            Log.e("RSPush : ", e.toString());
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        callback.RSHttpsCompletion(responseobject);
    }









}
