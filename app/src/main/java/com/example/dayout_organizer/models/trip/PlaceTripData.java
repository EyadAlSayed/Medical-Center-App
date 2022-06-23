package com.example.dayout_organizer.models.trip;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.place.PlaceData;
import com.example.dayout_organizer.room.placeRoom.converters.PlaceDataConverter;

import java.io.Serializable;


@Entity(tableName = AppConstants.PLACE_TRIP_DATA)
public class PlaceTripData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int place_id;
    public int trip_id;
    public int order;
    public String description;
    public int status;
    @TypeConverters(PlaceDataConverter.class)
    public PlaceData place = new PlaceData();

    public PlaceTripData() {
    }

    public PlaceTripData(int place_id, String place_name, int order, String description) {
        this.place_id = place_id;
        this.order = order;
        this.place.name = place_name;
        this.description = description;
    }
}
