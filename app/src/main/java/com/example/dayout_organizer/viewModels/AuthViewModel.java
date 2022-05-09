package com.example.dayout_organizer.viewModels;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.dayout_organizer.api.ApiClient;
import com.example.dayout_organizer.models.LoginModel;
import com.example.dayout_organizer.models.RegisterModel;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dayout_organizer.config.AppConstants.getErrorMessage;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";
    private final ApiClient apiClient = new ApiClient();

    private static AuthViewModel instance;

    public static AuthViewModel getINSTANCE() {
        if (instance == null) {
            instance = new AuthViewModel();
        }
        return instance;
    }


    public MutableLiveData<Pair<LoginModel,String>> loginMutableLiveData;

    public MutableLiveData<Pair<Boolean,String>> successfulMutableLiveData;

    public MutableLiveData<Pair<RegisterModel, String>> registerMutableLiveData;


    public void login(JsonObject jsonObject){
        loginMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().login(jsonObject).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()){
                    loginMutableLiveData.setValue(new Pair<>(response.body(),null));
                }
                else {
                    try {
                        loginMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                loginMutableLiveData.setValue(null);
            }
        });
    }

    public void sendPromotionRequest(JsonObject jsonObject){
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().promotionRequest(jsonObject).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    successfulMutableLiveData.setValue(new Pair<>(true,null));
                }
                else {
                    try {
                        successfulMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (Exception e) {
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

    public void registerOrganizer(RegisterModel model){
        registerMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().registerOrganizer(model).enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if(response.isSuccessful()){
                    registerMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        registerMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                registerMutableLiveData.setValue(null);
            }
        });
    }

}
