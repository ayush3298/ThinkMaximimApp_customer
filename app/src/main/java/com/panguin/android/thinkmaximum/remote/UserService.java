package com.panguin.android.thinkmaximum.remote;

import com.google.gson.JsonObject;
import com.panguin.android.thinkmaximum.model.Result;
import com.panguin.android.thinkmaximum.model.customer;
import com.panguin.android.thinkmaximum.model.forgot;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    final String key = SharedPrefManager.getKey();


    @FormUrlEncoded
    @POST("api/rest-auth/login/")
    Call<Result> login(
            @Field("username") String username,
            @Field("password") String password);

    //    @FormUrlEncoded
    @POST("api/register/")
    Call<Result> register(@Body JsonObject person);

    //    @Headers("Authorization:Token "+ SharedPrefManager.getKey())
    @GET("customer/get-customer/")
    Call<customer> getMyJSON(@Header("Authorization") String key);


    @FormUrlEncoded
    @POST("api/set-notification-token/")
    Call<Result> send_token(@Field("notification_key") String refreshedToken, @Header("Authorization") String key);

    @FormUrlEncoded
    @POST("api/forgot-username/")
    Call<forgot> forgotusername(
            @Field("email") String email);

    @FormUrlEncoded
    @POST("api/forgot-password/")
    Call<forgot> forgotpassword(
            @Field("email") String email);
}