package com.example.dayout_organizer.models;

public class PassengerData {

    public String name;
    public int booking_for;
    public boolean confirmed;
    public String photo;

    public PassengerData(String name, int booking_for, boolean confirmed) {
        this.name = name;
        this.booking_for = booking_for;
        this.confirmed = confirmed;
    }
}
