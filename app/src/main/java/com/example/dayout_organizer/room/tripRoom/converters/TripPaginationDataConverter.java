package com.example.dayout_organizer.room.tripRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.trip.TripPaginationData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class TripPaginationDataConverter implements Serializable {

    @TypeConverter
    public String fromTrip(TripPaginationData tripData) {

        if (tripData == null)
            return null;

        Type type = new TypeToken<TripPaginationData>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(tripData, type);
    }


    @TypeConverter
    public TripPaginationData toTrip(String data) {


        if (data == null)
            return null;

        Type type = new TypeToken<List<TripPaginationData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(data, type);

    }
}
