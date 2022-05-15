package com.example.dayout_organizer.models.room.popularPlaceRoom.Interfaces;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.dayout_organizer.models.popualrPlace.PopularPlaceData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static com.example.dayout_organizer.config.AppConstants.POPULAR_PLACE_DATA;


@Dao
public interface IPopularPlaces {


    @Insert
    Completable insertPopularPlace(PopularPlaceData popularPlaceDB);


    @Query("select * from "+ POPULAR_PLACE_DATA)
    Single<List<PopularPlaceData>>getPopularPlace();
}
