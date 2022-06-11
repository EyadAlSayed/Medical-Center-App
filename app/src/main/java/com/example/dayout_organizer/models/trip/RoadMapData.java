package com.example.dayout_organizer.models.trip;

import java.io.Serializable;
import java.util.List;

public class RoadMapData implements Serializable {
    public int id;
    public String title;
    public int organizer_id;
    public int trip_status_id;
    public String description;
    public String begin_date;
    public String expire_date;
    public String end_booking;
    public int price;
    public List<PlaceTripData> place_trips;
}
