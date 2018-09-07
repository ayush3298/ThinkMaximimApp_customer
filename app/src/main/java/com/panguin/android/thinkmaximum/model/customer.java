package com.panguin.android.thinkmaximum.model;

import com.google.gson.annotations.SerializedName;

public class customer {

        @SerializedName("name")
        private String name;

        @SerializedName("logedin")
        private String logedin;

        @SerializedName("status")
        private String status;



        public customer(String name, String logedin, String status) {
            this.name = name;
            this.logedin = logedin;
            this.status = status;
        }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getLogedin() {
        return logedin;
    }
}


