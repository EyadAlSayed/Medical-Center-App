package com.example.dayout_organizer.models.notification;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.notificationRoom.converters.NotificationConverter;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = AppConstants.NOTIFICATION_TABLE)
public class NotificationModel implements Serializable {

    @TypeConverters(NotificationConverter.class)
    public ArrayList<NotificationData> data = new ArrayList<>();

    @PrimaryKey(autoGenerate =  true)
    int modelId;
}
