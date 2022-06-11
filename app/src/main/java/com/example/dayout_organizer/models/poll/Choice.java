package com.example.dayout_organizer.models.poll;

import com.example.dayout_organizer.models.profile.ProfileUser;

import java.util.ArrayList;

public class Choice {

    public int id;
    public String value;
    public int poll_id;
    public String created_at;
    public String updated_at;
    public ArrayList<ProfileUser> users = new ArrayList<>();

    public Choice(String value){
        this.value = value;
    }
}