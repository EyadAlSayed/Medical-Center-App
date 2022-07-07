package com.example.dayout_organizer.room.passengersRoom.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.notification.NotificationData;
import com.example.dayout_organizer.models.notification.NotificationModel;
import com.example.dayout_organizer.models.passenger.PassengerBookedFor;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.example.dayout_organizer.models.passenger.PassengerModel;
import com.example.dayout_organizer.room.notificationRoom.databases.NotificationDataBase;
import com.example.dayout_organizer.room.passengersRoom.interfaces.IPassengers;
import com.example.dayout_organizer.room.placeRoom.interfaces.IPlaces;

import static com.example.dayout_organizer.config.AppConstants.NOTIFICATION_DB;

@Database(
        entities = {PassengerModel.class, PassengerData.class, PassengerBookedFor.class},exportSchema = false
        ,version = 1)
abstract public class PassengersDataBase extends RoomDatabase {

    private static PassengersDataBase instance;

    public abstract IPassengers iPassengers();


    public static  synchronized PassengersDataBase getINSTANCE(Context context){
        if (instance == null){
            instance = Room.
                    databaseBuilder(context.getApplicationContext()
                            , PassengersDataBase.class, AppConstants.PASSENGERS_DB)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }
}
