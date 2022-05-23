package com.example.dayout_organizer.viewModels;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dayout_organizer.api.ApiClient;
import com.example.dayout_organizer.models.TripPhotos;
import com.example.dayout_organizer.models.trip.Trip;
import com.example.dayout_organizer.models.trip.TripModel;
import com.example.dayout_organizer.models.trip.TripType;
import com.example.dayout_organizer.models.trip.Type;
import com.example.dayout_organizer.models.trip.create.CreateTripPhoto;
import com.example.dayout_organizer.models.trip.create.CreateTripPlace;
import com.example.dayout_organizer.models.trip.create.CreateTripType;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;
import static com.example.dayout_organizer.config.AppConstants.getErrorMessage;

public class TripViewModel extends ViewModel {

    private static final String TAG = "TripViewModel";

    private final ApiClient apiClient = new ApiClient();

    private static TripViewModel instance;

    public static final String  TRIP_PHOTOS_URL = BASE_URL + "api/trip/photo/";



    public static TripViewModel getINSTANCE() {
        if (instance == null) {
            instance = new TripViewModel();
        }
        return instance;
    }

    public MutableLiveData<Pair<Trip, String>> createTripMutableLiveData;
    public MutableLiveData<Pair<Type, String>> tripTypeTripMutableLiveData;


    public MutableLiveData<Pair<TripModel, String>> upcomingTripsMutableLiveData;
    public MutableLiveData<Pair<TripModel, String>> activeTripsMutableLiveData;
    public MutableLiveData<Pair<TripModel, String>> historyTripsMutableLiveData;
    public MutableLiveData<Pair<TripPhotos, String>> tripPhotosMutableLiveData;



    public void getTripType(){
        tripTypeTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getTripType().enqueue(new Callback<Type>() {
            @Override
            public void onResponse(Call<Type> call, Response<Type> response) {
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
            public void onFailure(Call<Type> call, Throwable t) {
                tripTypeTripMutableLiveData.setValue(null);
            }
        });
    }

    public void createTrip(JsonObject jsonObject) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTrip(jsonObject).enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
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
            public void onFailure(Call<Trip> call, Throwable t) {
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
        apiClient.getAPI().createTripPhoto(createTripPhoto).enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
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
            public void onFailure(Call<Trip> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }


    public void createTripPLace(CreateTripPlace createTripPlace) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTripPlace(createTripPlace).enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
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
            public void onFailure(Call<Trip> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void createTripType(CreateTripType createTripType) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTripType(createTripType).enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
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
            public void onFailure(Call<Trip> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }


    public void getTripPhotos(){
        tripPhotosMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getTripPhotos().enqueue(new Callback<TripPhotos>() {
            @Override
            public void onResponse(Call<TripPhotos> call, Response<TripPhotos> response) {
                if (response.isSuccessful()){
                    tripPhotosMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
            }

            @Override
            public void onFailure(Call<TripPhotos> call, Throwable t) {
                tripPhotosMutableLiveData.setValue(null);
            }
        });
    }

    public void editTripPhotos(CreateTripPhoto createTripPhoto){
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTripPhotos(createTripPhoto).enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                if (response.isSuccessful()){
                    createTripMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void editTripTypes(int tripId,CreateTripType createTripType){
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTripTypes(tripId,createTripType).enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                if (response.isSuccessful()){
                    createTripMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void editTripPlaces(CreateTripPlace createTripPlace){
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTripPlaces(createTripPlace).enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                if (response.isSuccessful()){
                    createTripMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }



}