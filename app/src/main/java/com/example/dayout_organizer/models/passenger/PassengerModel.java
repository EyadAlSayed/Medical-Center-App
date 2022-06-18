package com.example.dayout_organizer.models.passenger;

import androidx.room.Entity;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "Passenger_Table")
public class PassengerModel implements Serializable {

    public ArrayList<PassengerData> data = new ArrayList<>();
}
