package com.example.dayout_organizer.models.place;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import com.example.dayout_organizer.room.placeRoom.converters.PhotoConverter;

import java.io.Serializable;
import java.util.List;


import static com.example.dayout_organizer.config.AppConstants.PLACE_DATA;

@Entity(tableName = PLACE_DATA)
public class PlaceData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String address;
    public String summary;
    public String description;
    public boolean status;
    public int type_id;
    public int favorites_count;
    public int place_trips_count;

    @TypeConverters(PhotoConverter.class)
    public List<PlacePhoto> photos;

}
