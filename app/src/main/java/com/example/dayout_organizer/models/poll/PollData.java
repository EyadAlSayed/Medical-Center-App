package com.example.dayout_organizer.models.poll;

import java.util.ArrayList;
import java.util.List;

public class PollData {

    public String description;
    public List<VoteData> choices = new ArrayList<>();

    public PollData(String description){
        this.description = description;
    }
}
