package com.example.dayout_organizer.models.trip;

import java.util.ArrayList;

public class TripDetailsModel {

    public Data data = new Data();

    public class CustomerTrip{
        public int id;
        public int customer_id;
        public int trip_id;
        public int checkout;
        public int rate;
        public String created_at;
        public String updated_at;
        public User user = new User();
    }

    public class Data{
        public int id;
        public String title;
        public int organizer_id;
        public int trip_status_id;
        public String description;
        public String begin_date;
        public String expire_date;
        public String end_booking;
        public int price;
        public String created_at;
        public String updated_at;
        public ArrayList<Type> types;
        public ArrayList<CustomerTrip> customer_trips = new ArrayList<>();
        public ArrayList<PlaceTrip> place_trips = new ArrayList<>();
        public ArrayList<TripPhoto> trip_photos = new ArrayList<>();
    }

    public class Pivot{
        public int trip_id;
        public int type_id;
    }

    public class PlaceTrip{
        public int id;
        public int place_id;
        public int trip_id;
        public int order;
        public String description;
        public Object deleted_at;
        public String created_at;
        public String updated_at;
    }



    public class TripPhoto{
        public int id;
        public int trip_id;
    }

    public class Type{
        public int id;
        public String name;
        public String created_at;
        public String updated_at;
        public Pivot pivot;
    }

    public class User{
        public int id;
        public String first_name;
        public String last_name;
        public Object email;
        public String phone_number;
        public String photo;
        public String gender;
        public Object mobile_token;
        public Object verified_at;
        public String created_at;
        public String updated_at;
    }
}
