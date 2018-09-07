package com.panguin.android.thinkmaximum.notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.panguin.android.thinkmaximum.model.Result;
import com.panguin.android.thinkmaximum.remote.ApiUtils;
import com.panguin.android.thinkmaximum.remote.SharedPrefManager;
import com.panguin.android.thinkmaximum.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            send_token(refreshedToken);
        }

    }

    public void send_token(String refreshedToken){
        Log.e("sending token","token sending");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        UserService service = retrofit.create(UserService.class);
        String key = "Token "+SharedPrefManager.getKey();

        Call call = service.send_token(refreshedToken,key);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog

                if(response.code() == 200){
                    Log.e("TOken",response.body().toString());





                }else if(response.code()==400){
                    Log.e("TOken",response.body().toString());


                }


            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.toString() );
            }
        });

    }
}