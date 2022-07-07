package com.example.dayout_organizer.room.pollRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.poll.PollPaginationData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PollPaginationConverter {

    @TypeConverter
    public String fromProfile(PollPaginationData pollPaginationData) {

        if (pollPaginationData == null)
            return null;

        Type type = new TypeToken<PollPaginationData>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(pollPaginationData, type);
    }


    @TypeConverter
    public PollPaginationData toProfile(String pollChoiceObject) {


        if (pollChoiceObject == null)
            return null;

        Type type = new TypeToken<List<PollPaginationData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(pollChoiceObject, type);

    }
}
