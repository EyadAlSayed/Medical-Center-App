package com.example.dayout_organizer.models.tripType;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Trip_Type_Table")
public class TripTypeModel implements Serializable {

    @TypeConverters
    public List<TripType> data = new ArrayList<>();
}
