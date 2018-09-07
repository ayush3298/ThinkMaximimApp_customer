package com.panguin.android.thinkmaximum.model;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    @SerializedName("key")
    private String key;

    @SerializedName("username")
    private String username;

    public Result(Boolean error, String message, User user, String key) {
        this.error = error;
        this.message = message;
        this.user = user;
        this.key = key;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public String getKey(){return key;}

    public String getUsername(){return username;}

}