package com.example.dayout_organizer.models.poll;

import com.example.dayout_organizer.models.profile.ProfileUser;

import java.util.ArrayList;

public class PollModel {

    public Data data;

    public class Data {
        public ArrayList<PollData> data = new ArrayList<>();
        public String first_page_url;
        public int from;
        public int last_page;
        public String last_page_url;
        public ArrayList<Link> links = new ArrayList<>();
    }
    public class Link {
        public String url;
        public String label;
        public boolean active;
    }

}
