package com.example.dayout_organizer.models.trip;

import com.example.dayout_organizer.models.trip.photo.TripPhotoData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TripData implements Serializable {

        public String title;
        public int organizer_id;
        public String description;
        public String begin_date;
        public String expire_date;
        public String end_booking;
        public int price;
        public int trip_status_id;
        public int id;
        public boolean  isActive = false;
        public String stopsToDetails;
        public List<TripPhotoData> trip_photos;
        public ArrayList<CustomerTripData> customer_trips = new ArrayList<>();
        public List<PlaceTripData> place_trips;
        public List<TripType> types;


}
