package com.example.dayout_organizer.viewModels;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.dayout_organizer.api.ApiClient;
import com.example.dayout_organizer.models.PopularPlace;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceViewModel extends ViewModel {


    private static final String TAG = "PlaceViewModel";
    private final ApiClient apiClient = new ApiClient();

    private static PlaceViewModel instance;

    public static PlaceViewModel getINSTANCE() {
        if (instance == null) {
            instance = new PlaceViewModel();
        }
        return instance;
    }

   public MutableLiveData<Pair<PopularPlace,String>> popularMutableLiveData;

    public MutableLiveData<Pair<Boolean,String>> successfulMutableLiveData;

    public void getPopularPlace(){
        popularMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getPopularPlace().enqueue(new Callback<PopularPlace>() {
            @Override
            public void onResponse(Call<PopularPlace> call, Response<PopularPlace> response) {
                if (response.isSuccessful()){
                    popularMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
                else {
                    popularMutableLiveData.setValue(new Pair<>(null,response.message()));
                }
            }

            @Override
            public void onFailure(Call<PopularPlace> call, Throwable t) {
                popularMutableLiveData.setValue(null);
            }
        });
    }


}
