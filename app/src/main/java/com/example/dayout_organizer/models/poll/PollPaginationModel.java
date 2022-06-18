package com.example.dayout_organizer.models.poll;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.models.profile.ProfileUser;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "Poll_Pagination_Table")
public class PollPaginationModel implements Serializable {

    @TypeConverters
    public PollPaginationData data;
}