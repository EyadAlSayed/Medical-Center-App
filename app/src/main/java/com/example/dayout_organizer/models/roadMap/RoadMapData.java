package com.example.dayout_organizer.models.roadMap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.trip.PlaceTripData;
import com.example.dayout_organizer.room.placeRoom.converters.PlaceDataConverter;
import com.example.dayout_organizer.room.tripRoom.converters.PlaceTripConverter;

import java.io.Serializable;
import java.util.List;

@Entity (tableName = AppConstants.ROAD_MAP_DATA)
public class RoadMapData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public int organizer_id;
    public int trip_status_id;
    public String description;
    public String begin_date;
    public String expire_date;
    public String end_booking;
    public int price;

    @TypeConverters(PlaceTripConverter.class)
    public List<PlaceTripData> place_trips;
}
