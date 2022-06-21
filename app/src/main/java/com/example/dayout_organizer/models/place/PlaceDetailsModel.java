package com.example.dayout_organizer.models.place;



import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.models.place.PlaceData;

import java.io.Serializable;

import static com.example.dayout_organizer.config.AppConstants.PLACE_DETAILS_TABLE;

@Entity(tableName = PLACE_DETAILS_TABLE)
public class PlaceDetailsModel implements Serializable {

    @TypeConverters
    public PlaceData data;

    @PrimaryKey(autoGenerate =  true)
    int modelId;
}
