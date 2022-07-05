package com.example.dayout_organizer.models.trip;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.room.tripRoom.converters.TripDataConverter;

import java.util.ArrayList;

import static com.example.dayout_organizer.config.AppConstants.TRIP_PAGE_DATA;

@Entity(tableName = TRIP_PAGE_DATA)
public class TripPaginationData {

    public int current_page;
    @TypeConverters(TripDataConverter.class)
    public ArrayList<TripData> data = new ArrayList<>();
    public String next_page_url;
    public String prev_page_url;
    public int total;

    @PrimaryKey(autoGenerate = true)
    int id;
}
