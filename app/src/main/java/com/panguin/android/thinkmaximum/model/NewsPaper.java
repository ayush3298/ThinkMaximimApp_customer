package com.panguin.android.thinkmaximum.model;

import com.google.gson.annotations.SerializedName;

public class NewsPaper {
    @SerializedName("to")
    private String to;

    @SerializedName("by")
    private String by;

    @SerializedName("status")
    private String status;



    public NewsPaper(String to, String by, String status) {
        this.to = to;
        this.by = by;
        this.status = status;
    }

    public String getBy() {
        return by;
    }

    public String getStatus() {
        return status;
    }

    public String getTo() {
        return to;
    }
}
