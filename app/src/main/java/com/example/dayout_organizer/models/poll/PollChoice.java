package com.example.dayout_organizer.models.poll;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.profile.ProfileUser;
import com.example.dayout_organizer.room.profileRoom.converters.ProfileUserConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppConstants.POLL_CHOICE)
public class PollChoice implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String value;
    public int poll_id;

    @TypeConverters(ProfileUserConverter.class)
    public List<ProfileUser> users = new ArrayList<>();


    public PollChoice(String value){
        this.value = value;
    }
}