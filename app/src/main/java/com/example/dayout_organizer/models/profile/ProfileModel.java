package com.example.dayout_organizer.models.profile;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.models.room.profileRoom.converters.ProfileDataConverter;

import java.io.Serializable;

import static com.example.dayout_organizer.config.AppConstants.PROFILE_TABLE;

@Entity(tableName = PROFILE_TABLE)
public class ProfileModel implements Serializable {

    public boolean success;
    public String message;

    @TypeConverters(ProfileDataConverter.class)
    public ProfileData data = new ProfileData();

    @PrimaryKey(autoGenerate = true)
    public int modelId;

}
