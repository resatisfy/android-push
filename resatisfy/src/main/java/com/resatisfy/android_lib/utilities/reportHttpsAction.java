package com.resatisfy.android_lib.utilities;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class reportHttpsAction extends AsyncTask<Void, Void, Void> {

    public JSONObject responseobject;
    public reportHttpsInterface callback;
    public String reportString;



    public reportHttpsAction(String reportString, reportHttpsInterface callback) {
        this.reportString = reportString;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(RSSettings.getApiUrl() + "post-report");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(reportString);
            writer.flush();
            writer.close();
            os.close();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            responseobject = new JSONObject(bufferedReader.readLine());

            conn.disconnect();


        } catch (Exception e) {
            Log.e("RSCrashReporting : ", e.toString());
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callback.reportHttpsCompletion(responseobject);
    }




}



