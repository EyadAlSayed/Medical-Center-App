package com.example.dayout_organizer.models.tripType;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Trip_Type_Data_Table")
public class TripType implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;

    public TripType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
