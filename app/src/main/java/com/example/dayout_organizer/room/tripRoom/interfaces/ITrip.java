package com.example.dayout_organizer.room.tripRoom.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dayout_organizer.models.profile.ProfileData;
import com.example.dayout_organizer.models.trip.TripData;

import io.reactivex.Completable;
import io.reactivex.Single;

import static com.example.dayout_organizer.config.AppConstants.PROFILE_DATA;
import static com.example.dayout_organizer.config.AppConstants.TRIP_DATA;

@Dao
public interface ITrip {

    @Insert
    Completable insertTripData(TripData tripData);

    @Query("select * from " + TRIP_DATA)
    Single<TripData> getTripData();
}
