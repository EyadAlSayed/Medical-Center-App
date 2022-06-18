package com.example.dayout_organizer.models.notification;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "")
public class NotificationModel implements Serializable {

    @TypeConverters
    public ArrayList<NotificationData> data = new ArrayList<>();
}
