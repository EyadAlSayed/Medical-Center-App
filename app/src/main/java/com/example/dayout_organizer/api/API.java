package com.example.dayout_organizer.api;

import com.example.dayout_organizer.models.NotificationData;
import com.example.dayout_organizer.models.PassengerData;
import com.example.dayout_organizer.models.PhotoBase64;
import com.example.dayout_organizer.models.trip.SingleTripModel;
import com.example.dayout_organizer.models.place.Place;
import com.example.dayout_organizer.models.authModels.LoginModel;
import com.example.dayout_organizer.models.authModels.RegisterModel;
import com.example.dayout_organizer.models.place.PopularPlace;

import com.example.dayout_organizer.models.profile.ProfileModel;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.models.trip.TripTypeModel;
import com.example.dayout_organizer.models.trip.create.CreateTripPhoto;
import com.example.dayout_organizer.models.trip.create.CreateTripPlace;
import com.example.dayout_organizer.models.trip.create.CreateTripType;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    Call<TripTypeModel> getTripType();

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

    @GET("api/trip/photo/{id}/base64")
    Call<PhotoBase64> getTripPhotoAsBase64(@Path("id") int id);

    @GET(/*link*/)
    Call<PassengerData> getPassengersInTrip();


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
    Call<SingleTripModel> createTrip(@Body JsonObject createTrip);

    @POST("api/trip/create/add/photos")
    Call<SingleTripModel> createTripPhoto(@Body CreateTripPhoto createTripPhoto);

    @POST("api/trip/create/add/places")
    Call<SingleTripModel> createTripPlace(@Body CreateTripPlace createTripPlace);

    @POST("api/trip/create/add/types")
    Call<SingleTripModel> createTripType(@Body CreateTripType createTripType);

    @POST("api/organizer/profile/edit")
    Call<ResponseBody> editProfile(@Body JsonObject model);

    /* Todo - Caesar
    @POST(link)
    Call<ResponseBody> confirmPassengerReservation(@Body JsonObject model);

    @POST(link)
    Call<ResponseBody> checkPassenger(@Body JsonObject model);
     */

    /**
     * Put Request
     */

    @PUT("api/trip/edit")
    Call<SingleTripModel> editTrip(@Body JsonObject createTrip);

    @PUT("api/trip/edit/photos")
    Call<SingleTripModel> editTripPhotos(@Body CreateTripPhoto createTripPhoto);


    @PUT("api/trip/edit/places")
    Call<SingleTripModel> editTripPlaces(@Body CreateTripPlace createTripPlace);

    @PUT("api/trip/edit/types/{id}")
    Call<SingleTripModel> editTripTypes(@Path("id") int id,@Body CreateTripType createTripType);


    /**
     * Delete Request
     */
}
