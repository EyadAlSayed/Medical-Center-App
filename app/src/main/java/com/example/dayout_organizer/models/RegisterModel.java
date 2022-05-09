package com.example.dayout_organizer.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterModel implements Serializable {

    //TODO: Recheck names - Caesar.

    public String message;

    public int id;
    public String first_name;
    public String last_name;
    public String photo;
    public String gender;
    public String phone_number;
    public int trip_count;
    public int followers_count;

    public String credential_photo;

    public String password;
    public String email;
}
