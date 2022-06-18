package com.example.dayout_organizer.models.roadMap;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.io.Serializable;

@Entity(tableName =  "Road_Map_Table")
public class RoadMapModel implements Serializable {

    @TypeConverters
    public RoadMapData data;

}
