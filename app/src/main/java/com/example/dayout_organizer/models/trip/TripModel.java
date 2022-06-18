package com.example.dayout_organizer.models.trip;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName =  "Trip_Table")
public class TripModel {

    @TypeConverters
    public List<TripData> data = new ArrayList<>();

}
