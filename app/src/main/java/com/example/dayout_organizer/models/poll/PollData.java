package com.example.dayout_organizer.models.poll;

import java.util.ArrayList;

public class PollData {

    public int id;
    public String title;
    public String description;
    public int organizer_id;

    public ArrayList<PollChoice> poll_Poll_choices = new ArrayList<>();

    // for creating poll.
    public ArrayList<PollChoice> pollChoices = new ArrayList<>();
}
