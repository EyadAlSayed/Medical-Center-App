package com.example.dayout_organizer.models;

import java.io.Serializable;
import java.util.List;

public class TripPhotos implements Serializable {

    public class Data{
        public int id;
        public int trip_id;
        public String path;
    }

    public class Root{
        public boolean success;
        public String message;
        public List<Data> data;
    }
}
