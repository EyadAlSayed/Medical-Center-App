package com.example.dayout_organizer.room.popularPlaceRoom.Interfaces;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.dayout_organizer.models.place.PlaceData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static com.example.dayout_organizer.config.AppConstants.PLACE_DATA;


@Dao
public interface IPopularPlaces {


    @Insert
    Completable insertPopularPlace(PlaceData popularPlaceDB);


    @Query("select * from "+ PLACE_DATA)
    Single<List<PlaceData>>getPopularPlace();
}
