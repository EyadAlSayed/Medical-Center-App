package com.example.dayout_organizer.models.trip;

import androidx.appcompat.widget.AppCompatButton;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.tripRoom.converters.TripDataConverter;

@Entity(tableName = AppConstants.TRIP_DETAILS_TABLE)
public class TripDetailsModel {

    @TypeConverters(TripDataConverter.class)
    public TripData data = new TripData();

    @PrimaryKey(autoGenerate = true)
    int modelId;

}
