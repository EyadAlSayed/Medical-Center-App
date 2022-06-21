package com.example.dayout_organizer.room.passengersRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.passenger.PassengerBookedFor;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class PassengersBookedForConverter implements Serializable {
    @TypeConverter
    public String fromNotificationToJson(List<PassengerBookedFor> passengersBookedForData) {

        if (passengersBookedForData == null)
            return null;

        Type type = new TypeToken<List<PassengerBookedFor>>() {}.getType();
        Gson gson = new Gson();

        return gson.toJson(passengersBookedForData, type);
    }


    @TypeConverter
    public List<PassengerBookedFor> fromJsonToPhotoList(String passengersBookedForObject) {


        if (passengersBookedForObject == null)
            return null;

        Type type = new TypeToken<List<PassengerBookedFor>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(passengersBookedForObject, type);

    }
}
