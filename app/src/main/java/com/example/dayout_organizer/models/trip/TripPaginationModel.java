package com.example.dayout_organizer.models.trip;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.tripRoom.converters.TripPaginationDataConverter;


@Entity(tableName = AppConstants.TRIP_TABLE)
public class TripPaginationModel {

    @TypeConverters(TripPaginationDataConverter.class)
    public TripPaginationData data = new TripPaginationData();

    @PrimaryKey(autoGenerate = true)
    int modelId;

}
