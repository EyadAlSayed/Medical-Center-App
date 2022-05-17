package com.example.dayout_organizer.api;

import com.example.dayout_organizer.models.place.Place;
import com.example.dayout_organizer.models.trip.Trip;
import com.example.dayout_organizer.models.authModels.LoginModel;
import com.example.dayout_organizer.models.authModels.RegisterModel;
import com.example.dayout_organizer.models.place.PopularPlace;
import com.example.dayout_organizer.models.profile.EditProfileModel;
import com.example.dayout_organizer.models.profile.ProfileModel;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.models.trip.TripType;
import com.example.dayout_organizer.models.trip.Type;
import com.example.dayout_organizer.models.trip.create.CreateTripPhoto;
import com.example.dayout_organizer.models.trip.create.CreateTripPlace;
import com.example.dayout_organizer.models.trip.create.CreateTripType;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface API {


    /**
     * Get Request
     */

    @GET("api/place/popular/{id}")
    Call<PopularPlace> getPopularPlace(@Path("id") int id);

    @GET("api/organizer/profile/{id}")
    Call<ProfileModel> getOrganizerProfile(@Path("id") int id);


    @GET("api/place")
    Call<Place> getPlaces();

    @GET("api/trip/types")
    Call<Type> getTripType();


    @GET("api/trip/upcoming")
    Call<TripModel> getUpcomingTrips();

    @GET("api/trip/active")
    Call<TripModel> getActiveTrips();

    @GET("api/trip/history")
    Call<TripModel> getHistoryTrips();



    /**
     * Post Request
     */
    @POST("api/user/login")
    Call<LoginModel> login(@Body JsonObject loginReqBody);

    @POST("api/user/promotion/request")
    Call<ResponseBody> promotionRequest(@Body JsonObject jsonObject);

    @POST("api/user/organizer/register")
    Call<RegisterModel> registerOrganizer(@Body RegisterModel profile);

    @POST("api/trip/create")
    Call<Trip> createTrip(@Body JsonObject createTrip);

    @POST("api/trip/create/add/photos")
    Call<Trip> createTripPhoto(@Body CreateTripPhoto createTripPhoto);

    @POST("api/trip/create/add/places")
    Call<Trip> createTripPlace(@Body CreateTripPlace createTripPlace);

    @POST("api/trip/create/add/types")
    Call<Trip> createTripType(@Body CreateTripType createTripType);

    /**
     * Put Request
     */


    @POST("api/organizer/profile/edit")
    Call<EditProfileModel> editProfile(@Body EditProfileModel model);




    /**
     * Delete Request
     */
}
