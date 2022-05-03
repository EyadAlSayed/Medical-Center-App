package com.example.dayout_organizer.api;

import com.example.dayout_organizer.models.LoginModel;
import com.example.dayout_organizer.models.PopularPlace;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {


    /**
     * Get Request
     */

    @GET("api/place/popular")
    Call<PopularPlace> getPopularPlace();


    /**
     * Post Request
     */
    @POST("api/user/login")
    Call<LoginModel> login(@Body JsonObject loginReqBody);

    @POST("api/user/promotion/request")
    Call<ResponseBody> promotionRequest(@Body JsonObject jsonObject);
    /**
     * Put Request
     */




    /**
     * Delete Request
     */
}
