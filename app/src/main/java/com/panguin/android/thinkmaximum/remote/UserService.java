package com.panguin.android.thinkmaximum.remote;

import com.google.gson.JsonObject;
import com.panguin.android.thinkmaximum.model.Cane_count;
import com.panguin.android.thinkmaximum.model.NewsPaper;
import com.panguin.android.thinkmaximum.model.Payment_model;
import com.panguin.android.thinkmaximum.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    final String key = SharedPrefManager.getKey();


    @FormUrlEncoded
    @POST("rest-auth/login/")
    Call <Result>login(
            @Field("username") String username,
            @Field("password") String password);

//    @FormUrlEncoded
    @POST("register/")
    Call <Result>register(@Body JsonObject person);
//    @Headers("Authorization:Token "+ SharedPrefManager.getKey())
    @GET("get_customers")
    Call <List> getMyJSON(@Header("Authorization") String auth);


    @GET("customer/add_can/{id}")
    Call <Cane_count> add_cane(@Path("id") int customer_id, @Header("Authorization") String auth);

    @GET("customer/remove_can/{id}")
    Call <Cane_count> remove_cane(@Path("id") int customer_id, @Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("set-notification-token/")
    Call <Result> send_token(@Field("notification_key") String refreshedToken, @Header("Authorization") String key);

    @FormUrlEncoded
    @POST("cash-recived/")
    Call<Payment_model> pay(
            @Field("id" )int id,
            @Field("ammount") int value,
            @Header("Authorization") String key);

    @GET("customer/add_newspaper/{id}")
    Call <NewsPaper> add_newspaper(@Path("id") int customer_id, @Header("Authorization") String auth);


}