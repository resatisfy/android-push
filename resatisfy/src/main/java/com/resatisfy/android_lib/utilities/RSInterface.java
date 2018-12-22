package com.resatisfy.android_lib.utilities;

import com.resatisfy.android_lib.models.RSChannelModel;
import com.resatisfy.android_lib.models.RSDeviceRegModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RSInterface {

    @Headers({"rs_token: " + RSSettings.rsToken, "Content-Type: application/json"})
    @POST("post-register-device")
    Call<RSDeviceRegModel> post_register_device(@Body RSDeviceRegModel data);

    @Headers({"rs_token: " + RSSettings.rsToken, "Content-Type: application/json"})
    @POST("post-active-channel")
    Call<RSChannelModel> post_active_channel(@Body RSChannelModel data);

    @Headers({"rs_token: " + RSSettings.rsToken, "Content-Type: application/json"})
    @POST("post-deactive-channel")
    Call<RSChannelModel> post_deactive_channel(@Body RSChannelModel data);

    @Headers({"rs_token: " + RSSettings.rsToken, "Content-Type: application/json"})
    @POST("post-delete-channel")
    Call<RSChannelModel> post_delete_channel(@Body RSChannelModel data);

}
