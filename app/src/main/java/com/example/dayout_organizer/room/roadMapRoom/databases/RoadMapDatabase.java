package com.example.dayout_organizer.room.roadMapRoom.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.poll.PollChoice;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.models.poll.PollPaginationData;
import com.example.dayout_organizer.models.poll.PollPaginationModel;
import com.example.dayout_organizer.models.roadMap.RoadMapData;
import com.example.dayout_organizer.models.roadMap.RoadMapModel;
import com.example.dayout_organizer.models.trip.PlaceTripData;
import com.example.dayout_organizer.room.profileRoom.databases.ProfileDatabase;
import com.example.dayout_organizer.room.profileRoom.interfaces.IProfileModel;
import com.example.dayout_organizer.room.roadMapRoom.interfaces.IRoadMap;

import static com.example.dayout_organizer.config.AppConstants.PROFILE_DB;

@Database(
        entities = {RoadMapModel.class, RoadMapData.class, PlaceTripData.class},exportSchema = false
        ,version = 1)
abstract  public class RoadMapDatabase extends RoomDatabase {


    private static RoadMapDatabase instance;

    public abstract IRoadMap iRoadMap();

    public static synchronized RoadMapDatabase getINSTANCE(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), RoadMapDatabase.class, AppConstants.ROAD_MAP_DB)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
