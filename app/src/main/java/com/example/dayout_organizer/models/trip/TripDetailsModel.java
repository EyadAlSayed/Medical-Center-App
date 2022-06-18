package com.example.dayout_organizer.models.trip;

import androidx.room.Entity;

@Entity(tableName =  "Trip_Details_Table")
public class TripDetailsModel {

    public TripData data = new TripData();

}
