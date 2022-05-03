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
        public String deleted_at;
        public String created_at;
        public List<Photo> photos;
        public String updated_at;
        public int type_id;
        public int place_trips_count;
    }

    public class Photo{
        public int id;
        public int place_id;
        public String path;
        public String created_at;
        public String updated_at;
    }



    public boolean success;
    public String message;
    public List<Data> data;


}
