package com.example.dayout_organizer.room.pollRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.poll.PollChoice;
import com.example.dayout_organizer.models.profile.ProfileData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class PollChoiceConverter implements Serializable {
    @TypeConverter
    public String fromProfile(PollChoice pollChoice) {

        if (pollChoice == null)
            return null;

        Type type = new TypeToken<PollChoice>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(pollChoice, type);
    }


    @TypeConverter
    public PollChoice toProfile(String pollChoiceObject) {


        if (pollChoiceObject == null)
            return null;

        Type type = new TypeToken<List<PollChoice>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(pollChoiceObject, type);

    }
}
