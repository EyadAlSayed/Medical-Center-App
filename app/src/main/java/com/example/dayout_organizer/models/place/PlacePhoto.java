package com.example.dayout_organizer.models.place;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


import static com.example.dayout_organizer.config.AppConstants.POPULAR_PLACE_PHOTO;

@Entity(tableName = POPULAR_PLACE_PHOTO)
public class PlacePhoto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int place_id;
    public String path;
}
