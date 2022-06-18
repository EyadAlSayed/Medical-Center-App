package com.example.dayout_organizer.models.passenger;

import java.io.Serializable;

public class PassengerBookedFor implements Serializable {

    public int id;
    public int customer_trip_id;
    public String passenger_name;
    public int checkout;
}
