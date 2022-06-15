package com.example.dayout_organizer.models.trip;

import com.example.dayout_organizer.models.passenger.PassengerBookedFor;
import com.example.dayout_organizer.models.profile.ProfileUser;

import java.util.ArrayList;

public class CustomerTripData {
    public int id;
    public int customer_id;
    public int trip_id;
    public int checkout;
    public float rate;
    public String created_at;
    public String updated_at;
    public ProfileUser user = new ProfileUser();
    public ArrayList<PassengerBookedFor> passengers = new ArrayList<>();
}
