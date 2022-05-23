package com.example.dayout_organizer.models.trip.create;

import com.example.dayout_organizer.models.trip.PlaceTripData;

import java.io.Serializable;
import java.util.List;

public class CreateTripPlace implements Serializable {

    public CreateTripPlace(int trip_id, List<PlaceTripData> places) {
        this.trip_id = trip_id;
        this.places = places;
    }

    public int trip_id;
    public List<PlaceTripData> places;
}
