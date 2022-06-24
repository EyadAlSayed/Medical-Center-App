package com.example.dayout_organizer.room.passengersRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.notification.NotificationData;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class PassengersConverters implements Serializable {
    @TypeConverter
    public String fromNotificationToJson(List<PassengerData> passengerData) {

        if (passengerData == null)
            return null;

        Type type = new TypeToken<List<PassengerData>>() {}.getType();
        Gson gson = new Gson();

        return gson.toJson(passengerData, type);
    }


    @TypeConverter
    public List<PassengerData> fromJsonToPhotoList(String passengersDataObject) {


        if (passengersDataObject == null)
            return null;

        Type type = new TypeToken<List<PassengerData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(passengersDataObject, type);

    }
}
