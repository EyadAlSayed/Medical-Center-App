package com.example.dayout_organizer.api;

import com.example.dayout_organizer.models.PhotoBase64;
import com.example.dayout_organizer.models.notification.NotificationModel;
import com.example.dayout_organizer.models.passenger.CheckPassengerModel;
import com.example.dayout_organizer.models.passenger.PassengerModel;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.models.profile.ProfileUser;
import com.example.dayout_organizer.models.place.PlaceDetailsModel;
import com.example.dayout_organizer.models.roadMap.RoadMapModel;
import com.example.dayout_organizer.models.poll.PollPaginationModel;
import com.example.dayout_organizer.models.place.PlacePaginationModel;
import com.example.dayout_organizer.models.authModels.LoginModel;
import com.example.dayout_organizer.models.place.PlaceModel;

import com.example.dayout_organizer.models.profile.ProfileModel;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.trip.TripPaginationModel;
import com.example.dayout_organizer.models.tripType.TripTypeModel;
import com.example.dayout_organizer.models.trip.create.CreateTripPhoto;
import com.example.dayout_organizer.models.trip.create.CreateTripPlace;
import com.example.dayout_organizer.models.trip.create.CreateTripType;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Call<PlaceModel> getPopularPlace(@Path("id") int id);

    @GET("api/organizer/profile/{id}")
    Call<ProfileModel> getOrganizerProfile(@Path("id") int id);

    @GET("api/place")
    Call<PlacePaginationModel> getPlaces(@Query("page") int page);

    @GET("api/trip/types")
    Call<TripTypeModel> getTripType();

    @GET("api/trip/{id}/details/organizer")
    Call<TripDetailsModel> getTripDetails(@Path("id") int id);

    @GET("api/notifications")
    Call<NotificationModel> getNotifications();

    @GET("api/trip/photo/{id}/base64")
    Call<PhotoBase64> getTripPhotoAsBase64(@Path("id") int id);

    @GET("api/bookings/trip/{id}")
    Call<PassengerModel> getBookingPassengersInTrip(@Path("id") int tripId);

    @GET("api/bookings/trip/{id}/passengers")
    Call<PassengerModel> getAllPassengersInTrip(@Path("id") int tripId);

    @GET("api/polls/organizer")
    Call<PollPaginationModel> getOrganizerPolls(@Query("page")int page);

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
    Call<ProfileUser> registerOrganizer(@Body ProfileUser profile);

    @POST("api/trip/create")
    Call<TripDetailsModel> createTrip(@Body JsonObject createTrip);

    @POST("api/trip/create/add/photos")
    Call<TripDetailsModel> createTripPhoto(@Body CreateTripPhoto createTripPhoto);

    @POST("api/trip/create/add/places")
    Call<TripDetailsModel> createTripPlace(@Body CreateTripPlace createTripPlace);

    @POST("api/trip/create/add/types")
    Call<TripDetailsModel> createTripType(@Body CreateTripType createTripType);

    @POST("api/organizer/profile/edit")
    Call<ResponseBody> editProfile(@Body JsonObject model);

    @POST("api/polls/create")
    Call<PollData> createPoll(@Body PollData poll);

    @POST("api/trip/checkout")
    Call<ResponseBody> checkPassengers(@Body CheckPassengerModel model);

    @POST("api/user/report")
    Call<ResponseBody> reportUser(@Body JsonObject object);

    @POST("api/place/suggest")
    Call<ResponseBody> suggestPlace(@Body JsonObject place);

    @POST("api/trip/active/organizer")
    Call<TripPaginationModel> getActiveTrips(@Body JsonObject filterModel, @Query("page") int page);

    @POST("api/trip/upcoming/organizer")
    Call<TripPaginationModel> getUpcomingTrips(@Body JsonObject filterModel, @Query("page") int page);

    @POST("api/trip/history/organizer")
    Call<TripPaginationModel> getHistoryTrips(@Body JsonObject filterModel, @Query("page") int page);


    /**
     * Put Request
     */

    @PUT("api/trip/edit")
    Call<TripDetailsModel> editTrip(@Body JsonObject createTrip);

    @PUT("api/trip/edit/photos")
    Call<TripDetailsModel> editTripPhotos(@Body CreateTripPhoto createTripPhoto);

    @PUT("api/trip/edit/places")
    Call<TripDetailsModel> editTripPlaces(@Body CreateTripPlace createTripPlace);

    @PUT("api/trip/edit/types/{id}")
    Call<TripDetailsModel> editTripTypes(@Path("id") int id,@Body CreateTripType createTripType);

    @PUT("api/trip/{id}/begin")
    Call<ResponseBody> beginTrip(@Path("id") int id);

    @PUT("api/trip/{id}/end")
    Call<ResponseBody> endTrip(@Path("id") int id);

    @PUT("api/trip/place-status/update/{tripId}/{placeId}")
    Call<ResponseBody> visitPlace(@Path("tripId") int tripId,@Path("placeId") int placeId);

    @PUT("api/bookings/customer/{customer_id}/trip/{trip_id}/confirm")
    Call<ResponseBody> confirmPassengerBooking(@Path("customer_id") int customerId, @Path("trip_id") int tripId);

    @PUT("api/bookings/{id}/organizer/cancel")
    Call<ResponseBody> cancelPassengerBooking(@Path("id") int id);


    /**
     * Delete Request
     */

    @DELETE("api/trip/{id}/delete")
    Call<ResponseBody> deleteTrip(@Path("id") int tripId);
}
