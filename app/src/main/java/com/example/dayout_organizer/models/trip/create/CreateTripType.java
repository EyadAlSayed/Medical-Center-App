package com.example.dayout_organizer.models.trip.create;

import java.io.Serializable;
import java.util.List;

public class CreateTripType implements Serializable {
    public String trip_id;
    public List<Type> types;

    public class Type{
        public String type_id;
    }
}
