package com.example.dayout_organizer.room.tripRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.tripType.TripType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class TripTypeDataConverter implements Serializable {
    @TypeConverter
    public String toTripDataList(List<TripType> tripTypes) {
        if (tripTypes == null)
            return null;

        Type type = new TypeToken<List<TripType>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(tripTypes, type);
    }

    @TypeConverter
    public String toTripDataObject(TripType tripType) {
        if (tripType == null)
            return null;

        Type type = new TypeToken<TripType>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(tripType, type);
    }



    @TypeConverter
    public List<TripType> fromTripDataList(String tripType) {
        if (tripType == null)
            return null;

        Type type = new TypeToken<List<TripType>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(tripType, type);
    }

    @TypeConverter
    public TripType fromTripDataObject(String tripType) {
        if (tripType == null)
            return null;

        Type type = new TypeToken<TripType>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(tripType, type);
    }

}
