package com.example.dayout_organizer.api;

import com.example.dayout_organizer.models.NotificationData;
import com.example.dayout_organizer.models.PhotoBase64;
import com.example.dayout_organizer.models.trip.TripType;
import com.example.dayout_organizer.models.trip.photo.TripPhotoModel;
import com.example.dayout_organizer.models.place.Place;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.authModels.LoginModel;
import com.example.dayout_organizer.models.authModels.RegisterModel;
import com.example.dayout_organizer.models.place.PopularPlace;
import com.example.dayout_organizer.models.profile.EditProfileModel;
import com.example.dayout_organizer.models.profile.ProfileModel;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.trip.TripModel;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<List<TripType>> getTripType();


    @GET("api/trip/upcoming/organizer")
    Call<TripModel> getUpcomingTrips();

    @GET("api/trip/active/organizer")
    Call<TripModel> getActiveTrips();

    @GET("api/trip/history/organizer")
    Call<TripModel> getHistoryTrips();

    @GET("api/trip/{id}/details")
    Call<TripDetailsModel> getTripDetails(@Path("id") int id);

    @GET("api/notifications")
    Call<NotificationData> getNotifications();

//    @GET("api/trip/{id}/photos")
//    Call<TripPhotoModel> getTripPhotos();

    @GET("api/trip/photo/{id}/base64")
    Call<PhotoBase64> getTripPhotoAsBase64(@Path("id") int id);


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
    Call<TripData> createTrip(@Body JsonObject createTrip);

    @POST("api/trip/create/add/photos")
    Call<TripData> createTripPhoto(@Body CreateTripPhoto createTripPhoto);

    @POST("api/trip/create/add/places")
    Call<TripData> createTripPlace(@Body CreateTripPlace createTripPlace);

    @POST("api/trip/create/add/types")
    Call<TripData> createTripType(@Body CreateTripType createTripType);

    @POST("api/organizer/profile/edit")
    Call<EditProfileModel> editProfile(@Body EditProfileModel model);

    /**
     * Put Request
     */

    @PUT("api/trip/edit")
    Call<TripData> editTrip(@Body JsonObject createTrip);

    @PUT("api/trip/edit/photos")
    Call<TripData> editTripPhotos(@Body CreateTripPhoto createTripPhoto);


    @PUT("api/trip/edit/places")
    Call<TripData> editTripPlaces(@Body CreateTripPlace createTripPlace);

    @PUT("api/trip/edit/types")
    Call<TripData> editTripTypes(@Body CreateTripType createTripType);


    /**
     * Delete Request
     */
}
