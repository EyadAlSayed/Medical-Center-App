package com.example.dayout_organizer.models.trip;

import java.io.Serializable;

public class PlaceTrip implements Serializable {

    public int id;
    public int place_id;
    public int trip_id;
    public int order;
    public String place_name;
    public String description;

    public PlaceTrip(int place_id,String place_name, int order, String description) {
        this.place_id = place_id;
        this.order = order;
        this.place_name = place_name;
        this.description = description;
    }
}
