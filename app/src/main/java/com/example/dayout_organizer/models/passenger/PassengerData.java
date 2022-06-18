package com.example.dayout_organizer.models.passenger;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dayout_organizer.models.profile.ProfileUser;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "Passenger_Data_Table")
public class PassengerData implements Serializable {

//    public String name;
//    public int booking_for;
//    public boolean confirmed;
//    public String photo;
//    public boolean checked;


    @PrimaryKey(autoGenerate = true)
    public int id;
    public int customer_id;
    public int trip_id;
    public String rate;
    public String confirmed_at;
    public ProfileUser user;
    public ArrayList<PassengerBookedFor> passengers = new ArrayList<>();

    // for getting all passengers.
    public String passenger_name;

    //for checking passengers.
    public int checkout;

    //for old trips - for reporting
    public PassengerData( ProfileUser user, int customer_id, String passenger_name){
        this.customer_id = customer_id;
        this.passenger_name = passenger_name;
        this.user = user;
    }
}
