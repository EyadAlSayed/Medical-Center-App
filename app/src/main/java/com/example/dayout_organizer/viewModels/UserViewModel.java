package com.example.dayout_organizer.viewModels;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.dayout_organizer.api.ApiClient;

import com.example.dayout_organizer.models.notification.NotificationData;

import com.example.dayout_organizer.models.notification.NotificationModel;
import com.example.dayout_organizer.models.profile.ProfileModel;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dayout_organizer.config.AppConstants.getErrorMessage;

public class UserViewModel {

    private final String TAG = "UserViewModel";

    private static UserViewModel instance;
    private final ApiClient apiClient = new ApiClient();

    public MutableLiveData<Pair<ProfileModel, String>> profileMutableLiveData;

    public MutableLiveData<Pair<NotificationModel, String>> notificationsMutableLiveData;

    public MutableLiveData<Pair<Boolean,String>> successfulPairMutableLiveData;

    public MutableLiveData<Pair<Boolean, String>> reportMutableLiveData;

    public static UserViewModel getINSTANCE(){
        if(instance == null)
            instance = new UserViewModel();
        return instance;
    }

    public void getOrganizerProfile(int organizerId){
        profileMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getOrganizerProfile(organizerId).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.isSuccessful()){
                    profileMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        profileMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                profileMutableLiveData.setValue(null);
            }
        });
    }

    public void editProfile(JsonObject model){
        successfulPairMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editProfile(model).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    successfulPairMutableLiveData.setValue(new Pair<>(true, null));
                } else {
                    try {
                        successfulPairMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                successfulPairMutableLiveData.setValue(null);
            }
        });
    }

    public void getNotifications(){
        notificationsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getNotifications().enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if(response.isSuccessful()){
                    notificationsMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        notificationsMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                notificationsMutableLiveData.setValue(null);
            }
        });
    }

    public void reportUser(JsonObject object){
        reportMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().reportUser(object).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    reportMutableLiveData.setValue(new Pair<>(true, null));
                } else {
                    try {
                        reportMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                reportMutableLiveData.setValue(null);
            }
        });
    }
}
