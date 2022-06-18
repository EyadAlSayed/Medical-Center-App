package com.example.dayout_organizer.models.profile;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.example.dayout_organizer.config.AppConstants.PROFILE_USER;

@Entity(tableName = PROFILE_USER)
public class ProfileUser implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String first_name;
    public String last_name;
    public String photo;
    public String gender;
    public String phone_number;
    public String email;
    public String confirm_at;
    public String created_at;
    public String updated_at;

    //for registering.
    public String credential_photo;
    public String password;
}
