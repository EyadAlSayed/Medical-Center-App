package com.example.dayout_organizer.room.tripRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.place.PlaceData;
import com.example.dayout_organizer.models.trip.TripData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class TripDataConverter implements Serializable {

    @TypeConverter
    public String toTripDataList(List<TripData> tripData) {
        if (tripData == null)
            return null;

        Type type = new TypeToken<List<TripData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(tripData, type);
    }

    @TypeConverter
    public String toTripDataObject(TripData tripData) {
        if (tripData == null)
            return null;

        Type type = new TypeToken<TripData>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(tripData, type);
    }



    @TypeConverter
    public List<TripData> fromTripDataList(String placeData) {
        if (placeData == null)
            return null;

        Type type = new TypeToken<List<TripData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(placeData, type);
    }

    @TypeConverter
    public TripData fromTripDataObject(String placeData) {
        if (placeData == null)
            return null;

        Type type = new TypeToken<TripData>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(placeData, type);
    }
}
