package com.resatisfy.android_lib;


import android.content.Context;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;


import com.resatisfy.android_lib.controllers.InAppController;
import com.resatisfy.android_lib.controllers.RSHttpConnection;
import com.resatisfy.android_lib.models.InAppData;
import com.resatisfy.android_lib.utilities.RSConfig;
import com.resatisfy.android_lib.utilities.RSHttpsInterface;


import org.json.JSONObject;



public class RSInAppMessage  implements RSHttpsInterface {

    public static Context context;

    public static void takeOff(final Context context) {
        RSInAppMessage.context = context;

        final Handler handler = new Handler();
        final int delay = 5000;
        handler.postDelayed(new Runnable(){
            public void run(){
                ////start here
                RSInAppMessage.startCheckingInAppMsg(context);
                handler.postDelayed(this, delay);
            }
        }, delay);


    }

    private static void  startCheckingInAppMsg(Context context){
        String channelId = RSPush.channelId(context);
        if(channelId.isEmpty()){
            Log.e("RSPush", "Channel Id is empty.");
        }else{
            RSConfig getConfig = RSConfig.defaultConfig(context);

            if(getConfig.getAppKey() == null){
                Log.e("RSInAppMessage","rsconfig error!");
            }else{
                checkInAppMsg(channelId, getConfig);
            }
        }
    }

    private static void checkInAppMsg(String channelId, RSConfig rsConfig){
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("appKey", rsConfig.getAppKey());
        builder.appendQueryParameter("appSecret", rsConfig.getAppSecret());
        builder.appendQueryParameter("deviceType", "android");
        builder.appendQueryParameter("channelId",channelId);

        String postQuery = builder.build().getEncodedQuery();

        RSInAppMessage cBack = new RSInAppMessage();
        new RSHttpConnection("post-get-inappmessage", postQuery,cBack).execute();

    }


    @Override
    public void RSHttpsCompletion(JSONObject json) {
        try{
            String getStatus = json.getString("status");
            if(getStatus.equals("success")) {
                String getInAppData = json.getString("inAppData");
                if(!getInAppData.isEmpty()){
                    try {
                        JSONObject obj = new JSONObject(getInAppData);
                        final InAppData inAppData = new InAppData();
                        inAppData.setInAppMsgId(obj.getString("inAppMsgId"));
                        inAppData.setMsgTitle(obj.getString("msgTitle"));
                        inAppData.setMsgDecsription(obj.getString("msgDecsription"));
                        inAppData.setShowButton(obj.getString("showButton"));
                        inAppData.setButtonText(obj.getString("buttonText"));
                        inAppData.setButtonActionType(obj.getString("buttonActionType"));
                        inAppData.setMsgLink(obj.getString("msgLink"));

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                InAppController.initializeView(context,inAppData);
                            }
                        }, 1000);

                    } catch (Throwable t) {
                        Log.e("RSInAppMessage : ", t.toString());
                    }
                }
            } else if (!json.getString("msg").isEmpty()){
                System.out.println("RSInAppMessage : " + json.getString("msg"));
            }
        }catch (Exception e){}
    }




}
