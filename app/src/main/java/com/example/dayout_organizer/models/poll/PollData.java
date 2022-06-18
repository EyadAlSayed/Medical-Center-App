package com.example.dayout_organizer.models.poll;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "Poll_Data_Table")
public class PollData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public int organizer_id;

    public ArrayList<PollChoice> poll_Poll_choices = new ArrayList<>();

    // for creating poll.
    public ArrayList<PollChoice> pollChoices = new ArrayList<>();
}
