package com.example.dayout_organizer.models.trip;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dayout_organizer.models.trip.photo.TripPhotoData;
import com.example.dayout_organizer.models.tripType.TripType;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "Trip_Data_Table")
public class TripData implements Serializable {


        @PrimaryKey(autoGenerate = true)
        public int id;
        public String title;
        public int organizer_id;
        public int trip_status_id;
        public String description;
        public String begin_date;
        public String expire_date;
        public String end_booking;
        public int price;
        public int customer_trips_count;
        public ArrayList<TripType> types = new ArrayList<>();
        public ArrayList<PlaceTripData> place_trips = new ArrayList<>();
        public ArrayList<TripPhotoData> trip_photos = new ArrayList<>();
        public ArrayList<CustomerTripData> customer_trips = new ArrayList<>();

        public boolean isActive = false;
        public boolean isUpcoming = false;
        public String stopsToDetails;
}