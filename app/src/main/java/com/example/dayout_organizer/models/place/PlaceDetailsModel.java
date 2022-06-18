package com.example.dayout_organizer.models.place;



import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.models.place.PlaceData;

import java.io.Serializable;

@Entity(tableName = "Place_Details_Table")
public class PlaceDetailsModel implements Serializable {

    @TypeConverters
    public PlaceData data;
}
