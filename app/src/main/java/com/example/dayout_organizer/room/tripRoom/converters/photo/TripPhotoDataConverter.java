package com.example.dayout_organizer.room.tripRoom.converters.photo;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.poll.PollChoice;
import com.example.dayout_organizer.models.trip.photo.TripPhotoData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class TripPhotoDataConverter implements Serializable {
    @TypeConverter
    public String toPollChoice(List<TripPhotoData> tripPhotoData) {
        if (tripPhotoData == null)
            return null;

        Type type = new TypeToken<List<TripPhotoData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(tripPhotoData, type);
    }


    @TypeConverter
    public List<TripPhotoData> fromPollChoice(String tripPhotoData) {
        if (tripPhotoData == null)
            return null;

        Type type = new TypeToken<List<TripPhotoData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(tripPhotoData, type);

    }
}
