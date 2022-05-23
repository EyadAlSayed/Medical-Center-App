package com.example.dayout_organizer.models.trip.create;

import java.io.Serializable;
import java.util.List;

public class CreateTripPhoto implements Serializable {
    public static class Photo{
        public int trip_id;
        public String image;
        public Photo(String image) {
            this.image = image;
        }

        public Photo(int trip_id, String image) {
            this.trip_id = trip_id;
            this.image = image;
        }
    }

    public int trip_id;
    public List<Photo> photos;

    public CreateTripPhoto(int trip_id, List<Photo> photos) {
        this.trip_id = trip_id;
        this.photos = photos;
    }
}
