package com.example.dayout_organizer.models.trip;

import java.io.Serializable;
import java.util.List;

public class Trip implements Serializable {
    public class Data{
        public String title;
        public int organizer_id;
        public String description;
        public String begin_date;
        public String expire_date;
        public String end_booking;
        public String price;
        public int trip_status_id;
        public int id;
        public List<TripPhoto> trip_photos;
        public List<PlaceTrip> place_trips;
    }

    public boolean success;
    public String message;
    public Data data;


}
