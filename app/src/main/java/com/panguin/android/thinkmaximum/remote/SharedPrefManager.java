package com.panguin.android.thinkmaximum.remote;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {


        private static SharedPrefManager mInstance;
        private static Context mCtx;

        private static final String SHARED_PREF_NAME = "simplifiedcodingsharedprefretrofit";

        private static final String KEY_USER_ID = "keyuserid";
        private static final String KEY_USER_NAME = "keyusername";
        private static final String KEY_USER_EMAIL = "keyuseremail";
        private static final String KEY_USER_GENDER = "keyusergender";
        private static final String Key = "none";
        private static final String Keys = "none";

        private SharedPrefManager(Context context) {
            mCtx = context;
        }

        public static synchronized SharedPrefManager getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new SharedPrefManager(context);
            }
            return mInstance;
        }

        public boolean userLogin(String key) {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(Key, key);

            editor.apply();


            return true;
        }


        public boolean isLoggedIn() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            if (sharedPreferences.getString(Keys, null) != null){
                return true;}else {
            return false;}
        }



        public boolean logout() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            return true;
        }

    public static String getKey() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String key = sharedPreferences.getString(Key, null);
        return key;
    }
}
