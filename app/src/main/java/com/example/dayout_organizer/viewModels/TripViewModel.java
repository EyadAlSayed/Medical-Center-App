package com.example.dayout_organizer.viewModels;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dayout_organizer.api.ApiClient;
import com.example.dayout_organizer.models.PassengerData;
import com.example.dayout_organizer.models.PhotoBase64;
import com.example.dayout_organizer.models.trip.SingleTripModel;
import com.example.dayout_organizer.models.trip.TripType;
import com.example.dayout_organizer.models.trip.TripTypeModel;
import com.example.dayout_organizer.models.trip.photo.TripPhotoModel;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.models.trip.create.CreateTripPhoto;
import com.example.dayout_organizer.models.trip.create.CreateTripPlace;
import com.example.dayout_organizer.models.trip.create.CreateTripType;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;
import static com.example.dayout_organizer.config.AppConstants.getErrorMessage;

public class TripViewModel extends ViewModel {

    private static final String TAG = "TripViewModel";

    private final ApiClient apiClient = new ApiClient();

    private static TripViewModel instance;

    private MVP mvp;

    public static final String  TRIP_PHOTOS_URL = BASE_URL + "api/trip/photo/";

    public static TripViewModel getINSTANCE() {
        if (instance == null) {
            instance = new TripViewModel();
        }
        return instance;
    }

    public void setMVPInstance(MVP mvpInstance){
        this.mvp = mvpInstance;
    }

    public MutableLiveData<Pair<SingleTripModel, String>> createTripMutableLiveData;
    public MutableLiveData<Pair<TripTypeModel, String>> tripTypeTripMutableLiveData;


    public MutableLiveData<Pair<TripModel, String>> upcomingTripsMutableLiveData;
    public MutableLiveData<Pair<TripModel, String>> activeTripsMutableLiveData;
    public MutableLiveData<Pair<TripModel, String>> historyTripsMutableLiveData;
    public MutableLiveData<Pair<TripPhotoModel, String>> tripPhotosMutableLiveData;
    public MutableLiveData<Pair<PassengerData, String>> passengersInTripMutableLiveData;
    public MutableLiveData<Pair<TripDetailsModel, String>> tripDetailsMutableLiveData;
    public MutableLiveData<Pair<Boolean,String>> successfulMutableLiveData;

    public void getTripType(){
        tripTypeTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getTripType().enqueue(new Callback<TripTypeModel>() {
            @Override
            public void onResponse(Call<TripTypeModel> call, Response<TripTypeModel> response) {
                if (response.isSuccessful()) {
                    tripTypeTripMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        tripTypeTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripTypeModel> call, Throwable t) {
                tripTypeTripMutableLiveData.setValue(null);
            }
        });
    }

    public void createTrip(JsonObject jsonObject) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTrip(jsonObject).enqueue(new Callback<SingleTripModel>() {
            @Override
            public void onResponse(Call<SingleTripModel> call, Response<SingleTripModel> response) {
                if (response.isSuccessful()) {
                    createTripMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        createTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleTripModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void getUpcomingTrips(){
        upcomingTripsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getUpcomingTrips().enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                if(response.isSuccessful()){
                    upcomingTripsMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        upcomingTripsMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                upcomingTripsMutableLiveData.setValue(null);
            }
        });
    }

    public void getActiveTrips(){
        activeTripsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getActiveTrips().enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                if(response.isSuccessful()){
                    activeTripsMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        activeTripsMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                activeTripsMutableLiveData.setValue(null);
            }
        });
    }

    public void getHistoryTrips(){
        historyTripsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getHistoryTrips().enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                if(response.isSuccessful()){
                    historyTripsMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else{
                    try {
                        historyTripsMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                historyTripsMutableLiveData.setValue(null);
            }
        });
    }


    public void createTripPhoto(CreateTripPhoto createTripPhoto) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTripPhoto(createTripPhoto).enqueue(new Callback<SingleTripModel>() {
            @Override
            public void onResponse(Call<SingleTripModel> call, Response<SingleTripModel> response) {
                if (response.isSuccessful()) {
                    createTripMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        createTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleTripModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }


    public void createTripPLace(CreateTripPlace createTripPlace) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTripPlace(createTripPlace).enqueue(new Callback<SingleTripModel>() {
            @Override
            public void onResponse(Call<SingleTripModel> call, Response<SingleTripModel> response) {
                if (response.isSuccessful()) {
                    createTripMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        createTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleTripModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void createTripType(CreateTripType createTripType) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTripType(createTripType).enqueue(new Callback<SingleTripModel>() {
            @Override
            public void onResponse(Call<SingleTripModel> call, Response<SingleTripModel> response) {
                if (response.isSuccessful()) {
                    createTripMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        createTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleTripModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }


//    public void getTripPhotos(){
//        tripPhotosMutableLiveData = new MutableLiveData<>();
//        apiClient.getAPI().getTripPhotos().enqueue(new Callback<TripPhotoModel>() {
//            @Override
//            public void onResponse(Call<TripPhotoModel> call, Response<TripPhotoModel> response) {
//                if (response.isSuccessful()){
//                    tripPhotosMutableLiveData.setValue(new Pair<>(response.body(),null));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TripPhotoModel> call, Throwable t) {
//                tripPhotosMutableLiveData.setValue(null);
//            }
//        });
//    }


    public void getTripPhotoById(int id){
        apiClient.getAPI().getTripPhotoAsBase64(id).enqueue(new Callback<PhotoBase64>() {
            @Override
            public void onResponse(Call<PhotoBase64> call, Response<PhotoBase64> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mvp.getImageAsBase64(id,response.body().data,null);
                    }
                    else mvp.getImageAsBase64(id,null,"There is no response body");
                }
                else {
                    try {
                        mvp.getImageAsBase64(id,null,getErrorMessage(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PhotoBase64> call, Throwable t) {
                mvp.getImageAsBase64(id,null,null);
            }
        });
    }

    public void getTripDetails(int id){
        tripDetailsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getTripDetails(id).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
                if(response.isSuccessful()){
                    tripDetailsMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        tripDetailsMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripDetailsModel> call, Throwable t) {
                tripDetailsMutableLiveData.setValue(null);
            }
        });
    }

    public void getPassengersInTrip(){
        passengersInTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getPassengersInTrip().enqueue(new Callback<PassengerData>() {
            @Override
            public void onResponse(Call<PassengerData> call, Response<PassengerData> response) {
                if (response.isSuccessful()) {
                    passengersInTripMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        passengersInTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PassengerData> call, Throwable t) {
                passengersInTripMutableLiveData.setValue(null);
            }
        });
    }



    public void editTrip(JsonObject editTrip){
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTrip(editTrip).enqueue(new Callback<SingleTripModel>() {
            @Override
            public void onResponse(Call<SingleTripModel> call, Response<SingleTripModel> response) {
                if (response.isSuccessful()){
                    createTripMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
                else {
                    try {
                        createTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleTripModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void editTripPhotos(CreateTripPhoto createTripPhoto){
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTripPhotos(createTripPhoto).enqueue(new Callback<SingleTripModel>() {
            @Override
            public void onResponse(Call<SingleTripModel> call, Response<SingleTripModel> response) {
                if (response.isSuccessful()){
                    createTripMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
            }

            @Override
            public void onFailure(Call<SingleTripModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void editTripTypes(int id, CreateTripType createTripType){
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTripTypes(id,createTripType).enqueue(new Callback<SingleTripModel>() {
            @Override
            public void onResponse(Call<SingleTripModel> call, Response<SingleTripModel> response) {
                if (response.isSuccessful()){
                    createTripMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
                else {
                    try {
                        createTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleTripModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void editTripPlaces(CreateTripPlace createTripPlace){
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTripPlaces(createTripPlace).enqueue(new Callback<SingleTripModel>() {
            @Override
            public void onResponse(Call<SingleTripModel> call, Response<SingleTripModel> response) {
                if (response.isSuccessful()){
                    createTripMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
                else {
                    try {
                        createTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleTripModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void beginTrip(int tripId){
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().beginTrip(tripId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    successfulMutableLiveData.setValue(new Pair<>(true,null));
                }
                else {
                    try {
                        successfulMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                successfulMutableLiveData.setValue(null);
            }
        });
    }

    public void endTrip(int tripId){
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().endTrip(tripId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    successfulMutableLiveData.setValue(new Pair<>(true,null));
                }
                else {
                    try {
                        successfulMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                successfulMutableLiveData.setValue(null);
            }
        });
    }



}
