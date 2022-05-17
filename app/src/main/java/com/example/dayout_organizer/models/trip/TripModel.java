package com.example.dayout_organizer.models.trip;

import java.util.List;

public class TripModel {

    public String title;
    public String description;
    public int passengers_count;
    public List<String> stops;
    public List<String> photos;
    public boolean isActive;
    public String state;
    public String date;

    public TripModel(String title, String description, int passengers_count, List<String> stops, List<String> photos, boolean isActive, String state, String date) {
        this.title = title;
        this.description = description;
        this.passengers_count = passengers_count;
        this.stops = stops;
        this.photos = photos;
        this.isActive = isActive;
        this.state = state;
        this.date = date;
    }
}
