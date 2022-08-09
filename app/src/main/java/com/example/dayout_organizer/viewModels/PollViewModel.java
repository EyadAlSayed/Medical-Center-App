package com.example.dayout_organizer.viewModels;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dayout_organizer.api.ApiClient;
import com.example.dayout_organizer.models.poll.PollData;
import com.example.dayout_organizer.models.poll.PollPaginationModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dayout_organizer.config.AppConstants.getErrorMessage;

public class PollViewModel extends ViewModel {

    private final ApiClient apiClient = new ApiClient();

    private static PollViewModel instance;


    public static PollViewModel getINSTANCE(){
        if(instance == null)
            instance = new PollViewModel();
        return instance;
    }

    public MutableLiveData<Pair<PollPaginationModel, String>> pollsMutableLiveData;
    public MutableLiveData<Pair<Boolean, String>> successfulMutableLiveData;

    public void getPolls(int page){
        pollsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getOrganizerPolls(page).enqueue(new Callback<PollPaginationModel>() {
            @Override
            public void onResponse(Call<PollPaginationModel> call, Response<PollPaginationModel> response) {
                if(response.isSuccessful()){
                    pollsMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        pollsMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PollPaginationModel> call, Throwable t) {
                pollsMutableLiveData.setValue(null);
            }
        });
    }

    public void createPoll(PollData poll){
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createPoll(poll).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    successfulMutableLiveData.setValue(new Pair<>(true, null));
                } else {
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
