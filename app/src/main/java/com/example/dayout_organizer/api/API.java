package com.example.dayout_organizer.api;

import com.example.dayout_organizer.models.EditProfileModel;
import com.example.dayout_organizer.models.LoginModel;
import com.example.dayout_organizer.models.PopularPlace;
import com.example.dayout_organizer.models.ProfileModel;
import com.example.dayout_organizer.models.RegisterModel;
import com.google.gson.JsonObject;

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


    /**
     * Post Request
     */
    @POST("api/user/login")
    Call<LoginModel> login(@Body JsonObject loginReqBody);

    @POST("api/user/promotion/request")
    Call<ResponseBody> promotionRequest(@Body JsonObject jsonObject);

    @POST("api/user/organizer/register")
    Call<RegisterModel> registerOrganizer(@Body RegisterModel profile);

    /**
     * Put Request
     */


    @POST("api/organizer/profile/edit")
    Call<EditProfileModel> editProfile(@Body EditProfileModel model);




    /**
     * Delete Request
     */
}
