package com.example.dayout_organizer.models.poll;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.profile.ProfileUser;
import com.example.dayout_organizer.room.pollRoom.converters.PollPaginationConverter;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = AppConstants.POLL_PAGE_TABLE)
public class PollPaginationModel implements Serializable {

    @TypeConverters(PollPaginationConverter.class)
    public PollPaginationData data;

    @PrimaryKey(autoGenerate = true)
    int modelId;
}