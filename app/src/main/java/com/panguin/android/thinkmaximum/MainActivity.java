package com.panguin.android.thinkmaximum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.panguin.android.thinkmaximum.notifications.MyFirebaseInstanceIDService;
import com.panguin.android.thinkmaximum.remote.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onResume(){
        super.onResume();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            MyFirebaseInstanceIDService MyFirebaseInstanceIDService = new MyFirebaseInstanceIDService();
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            MyFirebaseInstanceIDService.send_token(refreshedToken);
            finish();
            startActivity(new Intent(this, customersactivity.class));
        }else {


            MyFirebaseInstanceIDService MyFirebaseInstanceIDService = new MyFirebaseInstanceIDService();
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            MyFirebaseInstanceIDService.send_token(refreshedToken);

            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_main);
          if (SharedPrefManager.getInstance(this).isLoggedIn()) {
              finish();
              startActivity(new Intent(this, customersactivity.class));
          } else {


              Intent intent = new Intent(this, login.class);
              startActivity(intent);
          }
      }
    }


