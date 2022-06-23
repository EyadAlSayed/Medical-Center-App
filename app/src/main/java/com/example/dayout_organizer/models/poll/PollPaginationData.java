package com.example.dayout_organizer.models.poll;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.pollRoom.converters.PollConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppConstants.POLL_PAGE_DATA)
public class PollPaginationData implements Serializable {

    int current_page;

    @TypeConverters(PollConverter.class)
    public List<PollData> data;

    public String next_page_url;
    public String prev_page_url;
    public int total;

    @PrimaryKey(autoGenerate = true)
    int modelId;
}
