package com.example.dayout_organizer.models.tripType;

import androidx.appcompat.widget.AppCompatButton;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dayout_organizer.config.AppConstants;

import java.io.Serializable;

@Entity(tableName = AppConstants.TRIP_TYPE_DATA)
public class TripType implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;

    public TripType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
