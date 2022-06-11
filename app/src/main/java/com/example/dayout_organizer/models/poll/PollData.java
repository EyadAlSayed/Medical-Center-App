package com.example.dayout_organizer.models.poll;

import java.util.ArrayList;

public class PollData {

    public int id;
    public String title;
    public String description;
    public int organizer_id;
    public Object deleted_at;
    public String created_at;
    public String updated_at;
    public ArrayList<Choice> poll_choices = new ArrayList<>();

    // for creating poll.
    public ArrayList<Choice> choices = new ArrayList<>();
}
