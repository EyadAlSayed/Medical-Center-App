package com.example.dayout_organizer.models.passenger;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dayout_organizer.config.AppConstants;

import java.io.Serializable;

@Entity(tableName = AppConstants.PASSENGERS_BOOKED_FOR)
public class PassengerBookedFor implements Serializable {

    @PrimaryKey(autoGenerate =  true)
    public int id;
    public int customer_trip_id;
    public String passenger_name;
    public int checkout;
}
