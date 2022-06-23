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
    public String toPollChoice(List<PollChoice> pollChoices) {
        if (pollChoices == null)
            return null;

        Type type = new TypeToken<List<PollChoice>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(pollChoices, type);
    }


    @TypeConverter
    public List<PollChoice> fromPollChoice(String pollChoices) {
        if (pollChoices == null)
            return null;

        Type type = new TypeToken<List<PollChoice>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(pollChoices, type);

    }
}
