package com.example.dayout_organizer.room.pollRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.poll.PollData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class PollConverter implements Serializable {

    @TypeConverter
    public String fromPoll(List<PollData> pollData) {
        if (pollData == null)
            return null;

        Type type = new TypeToken<List<PollData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(pollData, type);
    }


    @TypeConverter
    public List<PollData> toPoll(String popularObject) {
        if (popularObject == null)
            return null;

        Type type = new TypeToken<List<PollData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(popularObject, type);

    }
}
