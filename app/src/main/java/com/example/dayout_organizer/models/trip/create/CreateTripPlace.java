package com.example.dayout_organizer.models.trip.create;

import com.example.dayout_organizer.models.trip.PlaceTrip;

import java.io.Serializable;
import java.util.List;

public class CreateTripPlace implements Serializable {
//    public static class Place{
//        public int place_id;
//        public String place_name;
//        public int order;
//        public String description;
//
//
//    }

    public CreateTripPlace(int trip_id, List<PlaceTrip> places) {
        this.trip_id = trip_id;
        this.places = places;
    }

    public int trip_id;
    public List<PlaceTrip> places;
}
