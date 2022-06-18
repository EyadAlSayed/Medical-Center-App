package com.example.dayout_organizer.models.passenger;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckPassengerModel implements Serializable {

    public int trip_id;
    public ArrayList<Integer> passengers_ids = new ArrayList<>();
}
