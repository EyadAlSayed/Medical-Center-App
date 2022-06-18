package com.example.dayout_organizer.models.poll;

import com.example.dayout_organizer.models.profile.ProfileUser;

import java.io.Serializable;
import java.util.ArrayList;

public class PollChoice implements Serializable {

    public int id;
    public String value;
    public int poll_id;

    public ArrayList<ProfileUser> users = new ArrayList<>();

    public PollChoice(String value){
        this.value = value;
    }
}