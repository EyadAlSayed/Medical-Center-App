package com.example.dayout_organizer.models.poll;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.room.pollRoom.converters.PollChoiceConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppConstants.POLL_DATA)
public class PollData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public int organizer_id;


    // for creating poll.
    @TypeConverters(PollChoiceConverter.class)
    public List<PollChoice> choices = new ArrayList<>();

    public String expire_date;
}
