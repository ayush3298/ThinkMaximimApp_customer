package com.panguin.android.thinkmaximum.model;

import com.google.gson.annotations.SerializedName;

public class Cane_count {
    @SerializedName("user")
    private int user;

    @SerializedName("total_can")
    private int total_can;

    @SerializedName("curr_can")
    private int curr_can;


    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getTotal_can() {
        return total_can;
    }

    public void setTotal_can(int total_can) {
        this.total_can = total_can;
    }

    public int getCurr_can() {
        return curr_can;
    }

    public void setCurr_can(int curr_can) {
        this.curr_can = curr_can;
    }

    public Cane_count(int user, int total_can, int curr_can) {
        this.user = user;
        this.total_can = total_can;
        this.curr_can = curr_can;

    }
}
