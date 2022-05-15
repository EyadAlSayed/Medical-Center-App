package com.example.dayout_organizer.models.profile;

import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.models.room.profileRoom.converters.ProfileDataConverter;

import java.io.Serializable;

public class ProfileModel implements Serializable {

    public boolean success;
    public String message;

    @TypeConverters(ProfileDataConverter.class)
    public ProfileData data = new ProfileData();

    @PrimaryKey(autoGenerate = true)
    public int modelId;

}
