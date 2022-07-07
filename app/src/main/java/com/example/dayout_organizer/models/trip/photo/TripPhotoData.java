package com.example.dayout_organizer.models.trip.photo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dayout_organizer.config.AppConstants;

import java.io.Serializable;

@Entity(tableName = AppConstants.TRIP_PHOTO_DATA)
public class TripPhotoData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int trip_id;
    public String path;
}
