package com.example.dayout_organizer.room.passengersRoom.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.notification.NotificationData;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.example.dayout_organizer.models.place.PlaceData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static com.example.dayout_organizer.config.AppConstants.PASSENGERS_DATA;


@Dao
public interface IPassengers {

    @Insert
    Completable insertPassengers(PassengerData passengerData);


    @Query("select * from "+ PASSENGERS_DATA)
    Single<List<PassengerData>> getPassengers();
}
