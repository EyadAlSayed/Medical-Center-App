package com.example.dayout_organizer.models.trip;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.passenger.PassengerBookedFor;
import com.example.dayout_organizer.models.profile.ProfileUser;
import com.example.dayout_organizer.room.passengersRoom.converters.PassengersBookedForConverter;
import com.example.dayout_organizer.room.profileRoom.converters.ProfileUserConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppConstants.CUSTOMER_TRIP_DATA)
public class CustomerTripData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int customer_id;
    public int trip_id;
    public int checkout;
    public float rate;

    @TypeConverters(ProfileUserConverter.class)
    public ProfileUser user = new ProfileUser();

    @TypeConverters(PassengersBookedForConverter.class)
    public List<PassengerBookedFor> passengers = new ArrayList<>();
}
