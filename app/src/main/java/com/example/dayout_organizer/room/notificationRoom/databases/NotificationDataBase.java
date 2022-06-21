package com.example.dayout_organizer.room.notificationRoom.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dayout_organizer.models.notification.NotificationData;
import com.example.dayout_organizer.models.notification.NotificationModel;
import com.example.dayout_organizer.room.notificationRoom.INotification;
import com.example.dayout_organizer.room.placeRoom.interfaces.IPlaces;

import static com.example.dayout_organizer.config.AppConstants.NOTIFICATION_DB;
import static com.example.dayout_organizer.config.AppConstants.PLACE_DB;

@Database(
        entities = {NotificationModel.class, NotificationData.class}
        ,version = 1)
abstract  public class NotificationDataBase extends RoomDatabase {

    private static NotificationDataBase instance;

    public abstract INotification iNotification();


    public static  synchronized NotificationDataBase getINSTANCE(Context context){
        if (instance == null){
            instance = Room.
                    databaseBuilder(context.getApplicationContext()
                            , NotificationDataBase.class, NOTIFICATION_DB)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }
}
