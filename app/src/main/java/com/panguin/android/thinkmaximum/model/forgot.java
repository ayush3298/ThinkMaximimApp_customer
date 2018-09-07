package com.panguin.android.thinkmaximum.model;

import com.google.gson.annotations.SerializedName;

public class forgot {

    @SerializedName("status")
    private String status;



    public forgot(String status) {

        this.status = status;
    }


    public String getStatus() {
        return status;
    }


}
