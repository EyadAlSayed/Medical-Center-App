package com.example.dayout_organizer.models;

import java.io.Serializable;
import java.util.List;

public class Trip implements Serializable {
    public class Photo{
        public String image;
    }

    public class Place{
        public String place_id;
        public String order;
        public String description;
    }

    public class Type{
        public String type_id;
    }

    public String title;
    public String description;
    public String begin_date;
    public String expire_date;
    public String price;
    public List<Photo> photos;
    public List<Place> places;
    public List<Type> types;

}
