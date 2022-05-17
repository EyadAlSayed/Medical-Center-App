package com.example.dayout_organizer.models.trip.create;

import java.io.Serializable;
import java.util.List;

public class CreateTripType implements Serializable {
    public int trip_id;
    public List<Type> types;

    public CreateTripType(int trip_id, List<Type> types) {
        this.trip_id = trip_id;
        this.types = types;
    }

    public static class Type{
        public int type_id;

        public Type(int id) {
            this.type_id = id;
        }
    }
}
