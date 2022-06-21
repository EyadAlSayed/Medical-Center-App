package com.example.dayout_organizer.models.place;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.room.placeRoom.converters.PlaceDataConverter;

import java.io.Serializable;
import java.util.List;

import static com.example.dayout_organizer.config.AppConstants.PLACE_TABLE;


@Entity(tableName = PLACE_TABLE)
public class PlaceModel implements Serializable {

    @TypeConverters(PlaceDataConverter.class)
    public List<PlaceData> data;

    @PrimaryKey(autoGenerate = true)
    int modelId;

    @Ignore
    int current_page;
    @Ignore
    public String next_page_url;
    @Ignore
    public String prev_page_url;
    @Ignore
    public int total;

}