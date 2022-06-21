package com.example.dayout_organizer.room.notificationRoom.converters;

import androidx.room.TypeConverter;

import com.example.dayout_organizer.models.notification.NotificationData;
import com.example.dayout_organizer.models.place.PlacePhoto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class NotificationConverter implements Serializable {
    @TypeConverter
    public String fromNotificationToJson(List<NotificationData> notificationData) {

        if (notificationData == null)
            return null;

        Type type = new TypeToken<List<NotificationData>>() {}.getType();
        Gson gson = new Gson();

        return gson.toJson(notificationData, type);
    }


    @TypeConverter
    public List<NotificationData> fromJsonToPhotoList(String notificationObject) {


        if (notificationObject == null)
            return null;

        Type type = new TypeToken<List<NotificationData>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(notificationObject, type);

    }
}
