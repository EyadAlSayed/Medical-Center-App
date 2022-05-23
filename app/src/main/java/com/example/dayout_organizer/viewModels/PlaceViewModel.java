package com.example.dayout_organizer.viewModels;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.dayout_organizer.api.ApiClient;
import com.example.dayout_organizer.models.place.Place;
import com.example.dayout_organizer.models.place.PopularPlace;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;
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

   public MutableLiveData<Pair<PopularPlace,String>> popularPlaceMutableLiveData;
   public MutableLiveData<Pair<Place,String>> placeMutableLiveData;

    public MutableLiveData<Pair<Boolean,String>> successfulMutableLiveData;

    public void getPopularPlace(){
        popularPlaceMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getPopularPlace(GET_USER_ID()).enqueue(new Callback<PopularPlace>() {
            @Override
            public void onResponse(Call<PopularPlace> call, Response<PopularPlace> response) {
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
            public void onFailure(Call<PopularPlace> call, Throwable t) {
                popularPlaceMutableLiveData.setValue(null);
            }
        });
    }

    public void getPlaces(){
        placeMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getPlaces().enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
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
            public void onFailure(Call<Place> call, Throwable t) {
                placeMutableLiveData.setValue(null);
            }
        });
    }


}
