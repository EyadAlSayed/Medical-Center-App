package com.example.dayout_organizer.room.tripRoom.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.trip.CustomerTripData;
import com.example.dayout_organizer.models.trip.PlaceTripData;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.trip.TripPaginationData;
import com.example.dayout_organizer.models.trip.TripPaginationModel;
import com.example.dayout_organizer.models.trip.photo.TripPhotoData;
import com.example.dayout_organizer.models.trip.photo.TripPhotoModel;
import com.example.dayout_organizer.models.tripType.TripType;
import com.example.dayout_organizer.models.tripType.TripTypeModel;
import com.example.dayout_organizer.room.tripRoom.interfaces.ITrip;



@Database(entities = {TripPhotoData.class, TripPhotoModel.class, CustomerTripData.class, PlaceTripData.class,
        TripData.class, TripDetailsModel.class, TripPaginationModel.class, TripPaginationData.class, TripType.class, TripTypeModel.class},
        exportSchema = false, version = 1)
abstract public class TripDataBases extends RoomDatabase {


    private static TripDataBases instance;

    public abstract ITrip iTrip();

    public static synchronized TripDataBases getINSTANCE(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TripDataBases.class, AppConstants.TRIP_DB)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
