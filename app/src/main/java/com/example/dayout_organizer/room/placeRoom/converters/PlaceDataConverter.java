package com.example.dayout_organizer.room.placeRoom.converters;

import androidx.room.TypeConverter;


import com.example.dayout_organizer.models.place.PlaceData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class PlaceDataConverter implements Serializable {

    @TypeConverter
    public String toPlaceObject(PlaceData placeData) {
        if (placeData == null)
            return null;

        Type type = new TypeToken<PlaceData>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(placeData, type);
    }


    @TypeConverter
    public String toPlaceList(List<PlaceData> placeData) {
        if (placeData == null)
            return null;

        Type type = new TypeToken<List<PlaceData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(placeData, type);
    }


    @TypeConverter
    public List<PlaceData> fromPlaceList(String placeData) {
        if (placeData == null)
            return null;

        Type type = new TypeToken<List<PlaceData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(placeData, type);
    }


    @TypeConverter
    public PlaceData fromPlaceObject(String placeData) {
        if (placeData == null)
            return null;

        Type type = new TypeToken<PlaceData>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(placeData, type);
    }
}



