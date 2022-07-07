package com.example.dayout_organizer.models.roadMap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.roadMapRoom.converters.RoadMapConverter;

import java.io.Serializable;

@Entity(tableName = AppConstants.ROAD_MAP_TABLE)
public class RoadMapModel implements Serializable {

    @TypeConverters(RoadMapConverter.class)
    public RoadMapData data;

    @PrimaryKey(autoGenerate = true)
    int modelId;

}
