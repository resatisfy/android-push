package com.resatisfy.android_lib.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.resatisfy.android_lib.R;
import com.resatisfy.android_lib.RSInAppMessage;
import com.resatisfy.android_lib.RSPush;
import com.resatisfy.android_lib.models.InAppData;
import com.resatisfy.android_lib.utilities.RSConfig;
import com.resatisfy.android_lib.utilities.RSHttpsInterface;

import org.json.JSONObject;

public class InAppController  implements RSHttpsInterface {


    public static void initializeView(Context context, InAppData inAppData){

        //start here
        InAppController.showAlert(context, inAppData);

    }



    private static void showAlert(final Context context, final InAppData inAppData){

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.in_app_msg, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        //add elemnts to view
        TextView rsMsgTitle = (TextView) popupView.findViewById(R.id.rsMsgTitle);
        rsMsgTitle.setText(inAppData.getMsgTitle());

        TextView rsMsgDes = (TextView) popupView.findViewById(R.id.rsMsgDes);
        rsMsgDes.setText(inAppData.getMsgDecsription());


        Button rsMsgCrossBtn = (Button) popupView.findViewById(R.id.rsMsgCrossBtn);
        rsMsgCrossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RSInAppMessage.shouldCall = true;
                popupWindow.dismiss();
            }
        });

        //bottom area
        String getShowButton = inAppData.getShowButton();
        LinearLayout rsButtonBottomArea = (LinearLayout) popupView.findViewById(R.id.rsButtonBottomArea);
        if(getShowButton.equals("yes")){
            rsButtonBottomArea.setVisibility(View.VISIBLE);
            Button rsInAppButtonAction = (Button) popupView.findViewById(R.id.rsInAppButtonAction);
            rsInAppButtonAction.setText(inAppData.getButtonText());
            rsInAppButtonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RSInAppMessage.shouldCall = true;
                    popupWindow.dismiss();
                    InAppController.inAppClickAction(context,inAppData);
                }
            });
        }else{
            rsButtonBottomArea.setVisibility(View.GONE);
        }


        //add to our rootview
        View rootView = ((Activity)context).getWindow().getDecorView().getRootView();
        popupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);

    }


    private static void inAppClickAction(Context context, InAppData inAppData){
        String channelId = RSPush.channelId(context);
        RSConfig getConfig = RSConfig.defaultConfig(context);

        String buttonActionType = inAppData.getButtonActionType();
        String inAppMsgId = inAppData.getInAppMsgId();
        String msgLink = inAppData.getMsgLink();

        if(!channelId.isEmpty() && !inAppMsgId.isEmpty() && getConfig.getAppKey() != null){ inAppSendReports(channelId,inAppMsgId,getConfig); }

        //give actions
        if(!buttonActionType.isEmpty() && !msgLink.isEmpty()){
            if(buttonActionType.equals("web")){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(msgLink));
                context.startActivity(browserIntent);
            }
            else if(buttonActionType.equals("share")){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, inAppData.getMsgTitle());
                i.putExtra(Intent.EXTRA_TEXT, msgLink);
                context.startActivity(Intent.createChooser(i, inAppData.getMsgTitle()));
            }
            else if(buttonActionType.equals("deep")){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(msgLink));
                context.startActivity(browserIntent);
            }
        }
    }


    private static void inAppSendReports(String channelId, String inAppMsgId, RSConfig rsConfig){
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("appKey", rsConfig.getAppKey());
        builder.appendQueryParameter("appSecret", rsConfig.getAppSecret());
        builder.appendQueryParameter("deviceType", "android");
        builder.appendQueryParameter("channelId",channelId);
        builder.appendQueryParameter("inAppMsgId",inAppMsgId);

        String postQuery = builder.build().getEncodedQuery();

        InAppController cBack = new InAppController();
        new RSHttpConnection("post-inappmessage-status", postQuery,cBack).execute();
    }



    @Override
    public void RSHttpsCompletion(JSONObject json) {
        try{
            String getStatus = json.getString("status");
            if(getStatus.equals("success")) {
//                String inAppMsgId = json.getString("inAppMsgId");
//                System.out.println(inAppMsgId);
            } else if (!json.getString("msg").isEmpty()){
                Log.e("RSInAppMessage : ",json.getString("msg") );
            }
        }catch (Exception e){}
    }


}
