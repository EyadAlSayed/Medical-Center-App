package com.example.dayout_organizer.viewModels;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.dayout_organizer.api.ApiClient;

import com.example.dayout_organizer.models.NotificationModel;
import com.example.dayout_organizer.models.profile.EditProfileModel;
import com.example.dayout_organizer.models.profile.ProfileModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;
import static com.example.dayout_organizer.config.AppConstants.getErrorMessage;

public class UserViewModel {

    private final String TAG = "UserViewModel";

    private static UserViewModel instance;
    private final ApiClient apiClient = new ApiClient();

    public static final String  USER_PHOTO_URL =  BASE_URL + "api/user/profile/id/photo";


    public MutableLiveData<Pair<ProfileModel, String>> profileMutableLiveData;

    public MutableLiveData<Pair<EditProfileModel, String>> editProfileMutableLiveData;

    public MutableLiveData<Pair<NotificationModel, String>> notificationsMutableLiveData;

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

    public void editProfile(EditProfileModel model){
        editProfileMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editProfile(model).enqueue(new Callback<EditProfileModel>() {
            @Override
            public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {
                if(response.isSuccessful()){
                    editProfileMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        editProfileMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<EditProfileModel> call, Throwable t) {
                editProfileMutableLiveData.setValue(null);
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
}
