package com.example.dayout_organizer.models.roadMap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dayout_organizer.models.trip.PlaceTripData;

import java.io.Serializable;
import java.util.List;

@Entity (tableName = "Road_Map_Data_Table")
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
    public List<PlaceTripData> place_trips;
}
