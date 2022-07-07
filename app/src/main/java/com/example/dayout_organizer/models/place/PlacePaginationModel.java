package com.example.dayout_organizer.models.place;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.io.Serializable;

@Entity(tableName = "Place_Pagination_Model")
public class PlacePaginationModel implements Serializable {

    @TypeConverters
    public PlaceModel data;
}