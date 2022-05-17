package com.example.dayout_organizer.models.trip.create;

import java.io.Serializable;
import java.util.List;

public class CreateTripPlace implements Serializable {
    public static class Place{
        public int place_id;
        public String place_name;
        public int order;
        public String description;

        public Place(int place_id,String place_name, int order, String description) {
            this.place_id = place_id;
            this.order = order;
            this.place_name = place_name;
            this.description = description;
        }
    }

    public CreateTripPlace(int trip_id, List<Place> places) {
        this.trip_id = trip_id;
        this.places = places;
    }

    public int trip_id;
    public List<Place> places;
}
