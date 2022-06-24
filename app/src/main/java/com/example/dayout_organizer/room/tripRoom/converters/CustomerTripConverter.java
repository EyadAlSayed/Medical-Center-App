package com.example.dayout_organizer.room.tripRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.trip.CustomerTripData;
import com.example.dayout_organizer.models.trip.photo.TripPhotoData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class CustomerTripConverter implements Serializable {

    @TypeConverter
    public String toCustomerTrip(List<CustomerTripData> customerTripData) {
        if (customerTripData == null)
            return null;

        Type type = new TypeToken<List<CustomerTripData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.toJson(customerTripData, type);
    }


    @TypeConverter
    public List<CustomerTripData> fromPollChoice(String customerTripData) {
        if (customerTripData == null)
            return null;

        Type type = new TypeToken<List<CustomerTripData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(customerTripData, type);

    }
}
