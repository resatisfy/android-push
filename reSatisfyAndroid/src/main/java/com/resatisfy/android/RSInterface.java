package com.resatisfy.android;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RSInterface {


    @Headers({"rs_token: " + RSSettings.rsToken, "Content-Type: application/json"})
    @POST("post-register-device")
    Call<RSDeviceRegModel> post_register_device(@Body RSDeviceRegModel data);

}
