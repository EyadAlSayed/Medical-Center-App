package com.example.dayout_organizer.models.passenger;

import com.example.dayout_organizer.models.profile.ProfileUser;

import java.util.ArrayList;

public class PassengerData {

//    public String name;
//    public int booking_for;
//    public boolean confirmed;
//    public String photo;
//    public boolean checked;


    public int id;
    public int customer_id;
    public int trip_id;
    public String rate;
    public String created_at;
    public String updated_at;
    public String confirmed_at;
    public ProfileUser user;
    public ArrayList<PassengerBookedFor> passengers = new ArrayList<>();

    // for getting all passengers.
    public String passenger_name;

    //for checking passengers.
    public int checkout;
}
