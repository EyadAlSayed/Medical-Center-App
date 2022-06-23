package com.example.dayout_organizer.models.tripType;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.tripRoom.converters.TripTypeDataConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppConstants.TRIP_TYPE_TABLE)
public class TripTypeModel implements Serializable {

    @TypeConverters(TripTypeDataConverter.class)
    public List<TripType> data = new ArrayList<>();

    @PrimaryKey(autoGenerate = true)
    int modelId;
}
