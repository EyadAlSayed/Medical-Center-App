package com.example.dayout_organizer.models.trip;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.tripRoom.converters.TripDataConverter;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = AppConstants.TRIP_TABLE)
public class TripModel {

    @TypeConverters(TripDataConverter.class)
    public List<TripData> data = new ArrayList<>();

    @PrimaryKey(autoGenerate = true)
    int modelId;

}
