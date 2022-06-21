package com.example.dayout_organizer.room.placeRoom.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dayout_organizer.models.place.PlaceData;
import com.example.dayout_organizer.models.place.PlaceModel;
import com.example.dayout_organizer.models.place.PlacePhoto;
import com.example.dayout_organizer.room.placeRoom.interfaces.IPlaces;

import static com.example.dayout_organizer.config.AppConstants.PLACE_DB;


@Database(
        entities = {PlaceModel.class, PlaceData.class, PlacePhoto.class},exportSchema = false
        ,version = 1)
abstract public class PlaceDataBase extends RoomDatabase {


    private static PlaceDataBase instance;

    public abstract IPlaces iPlaces();


    public static  synchronized PlaceDataBase getINSTANCE(Context context){
        if (instance == null){
            instance = Room.
                    databaseBuilder(context.getApplicationContext()
                            , PlaceDataBase.class, PLACE_DB)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }
}
