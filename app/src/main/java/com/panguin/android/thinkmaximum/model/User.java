package com.panguin.android.thinkmaximum.model;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String key;

    public User(String name, String email, String password, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public User(int id, String name, String email, String key){
        this.id = id;
        this.name = name;
        this.email = email;
        this.key = key;

    }

    public User(int id, String name, String email, String password, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

//    public User( String key,) {
//        this.key = key;
//
//    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getkey(){return key;}
}
