package com.example.dayout_organizer.models.trip;

import com.example.dayout_organizer.models.profile.ProfileUser;

public class CustomerTrip {
    public int id;
    public int customer_id;
    public int trip_id;
    public int checkout;
    public int rate;
    public String created_at;
    public String updated_at;
    public ProfileUser user = new ProfileUser();
}
