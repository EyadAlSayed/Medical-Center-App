package com.example.dayout_organizer.room.pollRoom.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dayout_organizer.models.passenger.PassengerBookedFor;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.example.dayout_organizer.models.passenger.PassengerModel;
import com.example.dayout_organizer.models.poll.PollChoice;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.models.poll.PollPaginationData;
import com.example.dayout_organizer.models.poll.PollPaginationModel;
import com.example.dayout_organizer.room.notificationRoom.INotification;
import com.example.dayout_organizer.room.notificationRoom.databases.NotificationDataBase;
import com.example.dayout_organizer.room.pollRoom.interfaces.IPoll;

import static com.example.dayout_organizer.config.AppConstants.NOTIFICATION_DB;
import static com.example.dayout_organizer.config.AppConstants.POLL_DB;

@Database(
        entities = {PollPaginationModel.class, PollPaginationData.class, PollData.class, PollChoice.class},exportSchema = false
        ,version = 1)
abstract  public class PollDataBase extends RoomDatabase {
    private static PollDataBase instance;

    public abstract IPoll iPoll();


    public static  synchronized PollDataBase getINSTANCE(Context context){
        if (instance == null){
            instance = Room.
                    databaseBuilder(context.getApplicationContext()
                            , PollDataBase.class, POLL_DB)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }
}
