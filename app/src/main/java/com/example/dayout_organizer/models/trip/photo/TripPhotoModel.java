package com.example.dayout_organizer.models.trip.photo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.tripRoom.converters.photo.TripPhotoDataConverter;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = AppConstants.TRIP_PHOTO_TABLE)
public class TripPhotoModel implements Serializable {

    public boolean success;
    public String message;

    @TypeConverters(TripPhotoDataConverter.class)
    public List<TripPhotoData> data;

    @PrimaryKey(autoGenerate = true)
    int modelId;

}
