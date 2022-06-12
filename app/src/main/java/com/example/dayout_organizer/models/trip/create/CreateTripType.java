package com.example.dayout_organizer.models.trip.create;

import com.example.dayout_organizer.models.tripType.TripType;

import java.io.Serializable;
import java.util.List;

public class CreateTripType implements Serializable {
    public int trip_id;
    public List<TripType> types;

    public CreateTripType(int trip_id, List<TripType> types) {
        this.trip_id = trip_id;
        this.types = types;
    }

}
