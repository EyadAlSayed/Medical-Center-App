package com.example.dayout_organizer.models;

import java.io.Serializable;
import java.util.List;

public class PopularPlace implements Serializable {
    public class Data{
        public int id;
        public String name;
        public String address;
        public String summary;
        public String description;
        public int type_id;
        public int place_trips_count;
        public int favorites_count;
        public List<Photo> photos;
    }

    public class Photo{
        public int id;
        public int place_id;
    }
    public boolean success;
    public String message;
    public List<Data> data;


}
