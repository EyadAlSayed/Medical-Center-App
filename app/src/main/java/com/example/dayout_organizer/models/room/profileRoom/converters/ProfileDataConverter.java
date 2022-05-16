package com.example.dayout_organizer.models.room.profileRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.profile.ProfileData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class ProfileDataConverter implements Serializable {
    @TypeConverter
    public String fromProfile(ProfileData profileData) {

        if (profileData == null)
            return null;

        Type type = new TypeToken<ProfileData>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(profileData, type);
    }


    @TypeConverter
    public ProfileData toProfile(String data) {


        if (data == null)
            return null;

        Type type = new TypeToken<List<ProfileData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(data, type);

    }
}
