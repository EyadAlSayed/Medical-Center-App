package com.example.dayout_organizer.models.room.popularPlaceRoom.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dayout_organizer.models.popualrPlace.PopularPlace;
import com.example.dayout_organizer.models.popualrPlace.PopularPlaceData;
import com.example.dayout_organizer.models.popualrPlace.PopularPlacePhoto;
import com.example.dayout_organizer.models.room.popularPlaceRoom.Interfaces.IPopularPlaces;

import static com.example.dayout_organizer.config.AppConstants.POPULAR_PLACE_DB;


@Database(
        entities = {PopularPlace.class, PopularPlaceData.class, PopularPlacePhoto.class}
        ,version = 1)
abstract public class PopularPlaceDataBase extends RoomDatabase {


    private static PopularPlaceDataBase instance;

    public abstract IPopularPlaces iPopularPlaces();


    public static  synchronized PopularPlaceDataBase getINSTANCE(Context context){
        if (instance == null){
            instance = Room.
                    databaseBuilder(context.getApplicationContext()
                            ,PopularPlaceDataBase.class, POPULAR_PLACE_DB)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }
}
