package com.example.dayout_organizer.models.trip.photo;

import java.io.Serializable;
import java.util.List;

public class TripPhotoModel implements Serializable {

    public boolean success;
    public String message;
    public List<TripPhotoData> data;

}
