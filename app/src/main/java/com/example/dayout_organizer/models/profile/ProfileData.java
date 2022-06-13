package com.example.dayout_organizer.models.profile;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.models.room.profileRoom.converters.ProfileUserConverter;

import java.io.Serializable;

import static com.example.dayout_organizer.config.AppConstants.PROFILE_DATA;

@Entity(tableName = PROFILE_DATA)
public class ProfileData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int user_id;
    public String created_at;
    public String updated_at;
    public String bio;
    public float rating;
    public int followers_count;
    public int trips_count;

    @TypeConverters(ProfileUserConverter.class)
    public ProfileUser user = new ProfileUser();
}
