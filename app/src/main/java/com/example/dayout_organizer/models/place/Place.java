package com.example.dayout_organizer.models.place;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Place implements Serializable {

    public class Data{
        public String first_page_url;
        public int from;
        public int last_page;
        public String last_page_url;
        public List<Link> links;
        public String next_page_url;
        public String path;
        public int per_page;
        public String prev_page_url;
        public int to;
        public int total;
        public int id;
        public String name;
        public String address;
        public String summary;
        public String description;
        public int type_id;
        public List<Photo> photos;
    }

    public class Link{
        public String url;
        public String label;
        public boolean active;
    }

    public class Photo{
        public int id;
        public int place_id;
    }

    public class Data1{
        int current_page;
        public List<Data> data;
    }

    public boolean success;
    public String message;
    public Data1 data;
}
