package com.example.dayout_organizer.viewModels;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.dayout_organizer.api.ApiClient;
import com.example.dayout_organizer.models.place.PlacePaginationModel;
import com.example.dayout_organizer.models.place.PlaceModel;
import com.example.dayout_organizer.models.place.PlaceDetailsModel;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dayout_organizer.config.AppConstants.getErrorMessage;
import static com.example.dayout_organizer.config.AppSharedPreferences.GET_USER_ID;

public class PlaceViewModel extends ViewModel {


    private static final String TAG = "PlaceViewModel";

  //  public static final String  PLACE_PHOTO_URL = BASE_URL + "api/place/photo/";

    private final ApiClient apiClient = new ApiClient();

    private static PlaceViewModel instance;

    public static PlaceViewModel getINSTANCE() {
        if (instance == null) {
            instance = new PlaceViewModel();
        }
        return instance;
    }

   public MutableLiveData<Pair<PlaceModel,String>> popularPlaceMutableLiveData;
    public MutableLiveData<Pair<PlaceDetailsModel, String>> placeDetailsMutableLiveData;
   public MutableLiveData<Pair<PlacePaginationModel,String>> placeMutableLiveData;

    public MutableLiveData<Pair<Boolean,String>> successfulMutableLiveData;

    public void getPopularPlace(){
        popularPlaceMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getPopularPlace(GET_USER_ID()).enqueue(new Callback<PlaceModel>() {
            @Override
            public void onResponse(Call<PlaceModel> call, Response<PlaceModel> response) {
                if (response.isSuccessful()){
                    popularPlaceMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
                else {
                    try {
                        popularPlaceMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceModel> call, Throwable t) {
                popularPlaceMutableLiveData.setValue(null);
            }
        });
    }

    public void getPlaces(){
        placeMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getPlaces().enqueue(new Callback<PlacePaginationModel>() {
            @Override
            public void onResponse(Call<PlacePaginationModel> call, Response<PlacePaginationModel> response) {
                if (response.isSuccessful()){
                    placeMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
                else {
                    try {
                        placeMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlacePaginationModel> call, Throwable t) {
                placeMutableLiveData.setValue(null);
            }
        });
    }

    public void getPlaceDetails(int placeId){
        placeDetailsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getPlaceDetails(placeId).enqueue(new Callback<PlaceDetailsModel>() {
            @Override
            public void onResponse(Call<PlaceDetailsModel> call, Response<PlaceDetailsModel> response) {
                if (response.isSuccessful()){
                    placeDetailsMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
                else {
                    try {
                        placeDetailsMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceDetailsModel> call, Throwable t) {
                placeDetailsMutableLiveData.setValue(null);
            }
        });
    }


}
