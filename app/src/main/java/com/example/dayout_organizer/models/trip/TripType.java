package com.example.dayout_organizer.models.trip;

import java.io.Serializable;

public class TripType implements Serializable {
    public class Pivot{
        public int trip_id;
        public int type_id;
    }

    public int id;
    public String name;
    public Pivot pivot;
}
