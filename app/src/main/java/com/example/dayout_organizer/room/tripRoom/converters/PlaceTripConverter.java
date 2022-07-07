package com.example.dayout_organizer.room.tripRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.trip.PlaceTripData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class PlaceTripConverter implements Serializable {
    @TypeConverter
    public String toPlaceTripData(List<PlaceTripData> placeTripData) {
        if (placeTripData == null)
            return null;

        Type type = new TypeToken<List<PlaceTripData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(placeTripData, type);
    }


    @TypeConverter
    public List<PlaceTripData> fromPlaceTripData(String placeTripObject) {
        if (placeTripObject == null)
            return null;

        Type type = new TypeToken<List<PlaceTripData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(placeTripObject, type);

    }
}
