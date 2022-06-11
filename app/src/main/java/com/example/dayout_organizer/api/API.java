package com.example.dayout_organizer.api;

import com.example.dayout_organizer.models.NotificationData;
import com.example.dayout_organizer.models.PhotoBase64;
import com.example.dayout_organizer.models.passenger.CheckPassengerModel;
import com.example.dayout_organizer.models.passenger.PassengerModel;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.models.poll.VoteData;
import com.example.dayout_organizer.models.trip.PlaceDetailsModel;
import com.example.dayout_organizer.models.trip.RoadMapModel;
import com.example.dayout_organizer.models.poll.PollModel;
import com.example.dayout_organizer.models.trip.SingleTripModel;
import com.example.dayout_organizer.models.place.Place;
import com.example.dayout_organizer.models.authModels.LoginModel;
import com.example.dayout_organizer.models.authModels.RegisterModel;
import com.example.dayout_organizer.models.place.PopularPlaceModel;

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
    Call<PopularPlaceModel> getPopularPlace(@Path("id") int id);

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

    @GET("api/bookings/trip/{id}")
    Call<PassengerModel> getBookingPassengersInTrip(@Path("id") int tripId);

    @GET("api/bookings/trip/{id}/passengers")
    Call<PassengerModel> getAllPassengersInTrip(@Path("id") int tripId);

    @GET("api/polls/organizer")
    Call<PollModel> getOrganizerPolls();

    @GET("api/place/details/{id}")
    Call<PlaceDetailsModel> getPlaceDetails(@Path("id") int id);

    @GET("api/trip/road-map/{id}")
    Call<RoadMapModel> getRoadMap(@Path("id") int tripId);


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

    @POST("api/polls/create")
    Call<PollData> createPoll(@Body PollData poll);

    @POST("api/trip/checkout")
    Call<ResponseBody> checkPassengers(@Body CheckPassengerModel model);


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

    @PUT("api/trip/{id}/begin")
    Call<ResponseBody> beginTrip(@Path("id") int id);

    @PUT("api/trip/{id}/end")
    Call<ResponseBody> endTrip(@Path("id") int id);

    @PUT("api/trip/place-status/update/{tripId}/{placeId}")
    Call<ResponseBody> visitPlace(@Path("tripId") int tripId,@Path("placeId") int placeId);

    @PUT("api/bookings/{customer_id}/{trip_id}/confirm")
    Call<ResponseBody> confirmPassengerBooking(@Path("customer_id") int customerId, @Path("trip_id") int tripId);


    /**
     * Delete Request
     */
}
