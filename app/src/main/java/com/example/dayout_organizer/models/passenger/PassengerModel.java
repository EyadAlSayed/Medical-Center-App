package com.example.dayout_organizer.models.passenger;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.passengersRoom.converters.PassengersConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppConstants.PASSENGERS_TABLE)
public class PassengerModel implements Serializable {

    @TypeConverters(PassengersConverters.class)
    public List<PassengerData> data = new ArrayList<>();

    @PrimaryKey(autoGenerate =  true)
    int modelId;
}
