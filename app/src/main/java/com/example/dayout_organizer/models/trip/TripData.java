package com.example.dayout_organizer.models.trip;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.models.trip.photo.TripPhotoData;
import com.example.dayout_organizer.models.tripType.TripType;
import com.example.dayout_organizer.room.tripRoom.converters.CustomerTripConverter;
import com.example.dayout_organizer.room.tripRoom.converters.PlaceTripConverter;
import com.example.dayout_organizer.room.tripRoom.converters.TripTypeDataConverter;

import com.example.dayout_organizer.room.tripRoom.converters.photo.TripPhotoDataConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppConstants.TRIP_DATA)
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

        @TypeConverters(TripTypeDataConverter.class)
        public List<TripType> types = new ArrayList<>();

        @TypeConverters(PlaceTripConverter.class)
        public List<PlaceTripData> place_trips = new ArrayList<>();

        @TypeConverters(TripPhotoDataConverter.class)
        public List<TripPhotoData> trip_photos = new ArrayList<>();

        @TypeConverters(CustomerTripConverter.class)
        public List<CustomerTripData> customer_trips = new ArrayList<>();

        public boolean isActive = false;
        public boolean isUpcoming = false;
        public String stopsToDetails;
}