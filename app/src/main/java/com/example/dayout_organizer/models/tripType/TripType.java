package com.example.dayout_organizer.models.tripType;

import java.io.Serializable;

public class TripType implements Serializable {

    public int id;
    public String name;

    public TripType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
