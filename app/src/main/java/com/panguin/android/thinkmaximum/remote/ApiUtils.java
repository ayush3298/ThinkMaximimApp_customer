package com.panguin.android.thinkmaximum.remote;

public class ApiUtils {

//    public static final String BASE_URL = "https://thinkmaximum.herokuapp.com/";
    public static final String BASE_URL = "http://192.168.43.48:5000/";
    //public static final String BASE_URL = "https://www.zebapi.com/api/v1/market/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}