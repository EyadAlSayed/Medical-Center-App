package com.example.dayout_organizer.models.notification;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dayout_organizer.config.AppConstants;

import java.io.Serializable;

@Entity(tableName = AppConstants.NOTIFICATION_DATA)
public class NotificationData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String body;
    public int user_id;
}
