package com.example.dayout_organizer.viewModels;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dayout_organizer.api.ApiClient;
import com.example.dayout_organizer.models.PhotoBase64;
import com.example.dayout_organizer.models.passenger.CheckPassengerModel;
import com.example.dayout_organizer.models.passenger.PassengerModel;
import com.example.dayout_organizer.models.roadMap.RoadMapModel;
import com.example.dayout_organizer.models.trip.photo.TripPhotoModel;
import com.example.dayout_organizer.models.tripType.TripTypeModel;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.trip.TripPaginationModel;
import com.example.dayout_organizer.models.trip.create.CreateTripPlace;
import com.example.dayout_organizer.models.trip.create.CreateTripType;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.example.dayout_organizer.config.AppConstants.getErrorMessage;

public class TripViewModel extends ViewModel {

    private static final String TAG = "TripViewModel";

    private final ApiClient apiClient = new ApiClient();

    private static TripViewModel instance;

    private MVP mvp;



    public static TripViewModel getINSTANCE() {
        if (instance == null) {
            instance = new TripViewModel();
        }
        return instance;
    }

    public void setMVPInstance(MVP mvpInstance) {
        this.mvp = mvpInstance;
    }

    public MutableLiveData<Pair<TripDetailsModel, String>> createTripMutableLiveData;
    public MutableLiveData<Pair<TripTypeModel, String>> tripTypeTripMutableLiveData;


    public MutableLiveData<Pair<TripPaginationModel, String>> upcomingTripsMutableLiveData;
    public MutableLiveData<Pair<TripPaginationModel, String>> activeTripsMutableLiveData;
    public MutableLiveData<Pair<TripPaginationModel, String>> historyTripsMutableLiveData;
    public MutableLiveData<Pair<TripPhotoModel, String>> tripPhotoPairMutableLiveData;
    public MutableLiveData<Pair<PassengerModel, String>> bookingPassengersInTripMutableLiveData;
    public MutableLiveData<Pair<PassengerModel, String>> allPassengersInTripMutableLiveData;
    public MutableLiveData<Pair<ResponseBody, String>> confirmPassengerBooking;
    public MutableLiveData<Pair<ResponseBody, String>> checkPassengersMutableLiveData;

    public MutableLiveData<Pair<TripDetailsModel, String>> tripDetailsMutableLiveData;
    public MutableLiveData<Pair<RoadMapModel, String>> roadMapMutableLiveData;
    public MutableLiveData<Pair<Boolean, String>> successfulMutableLiveData;


    public void getUpcomingTrips(JsonObject filterModel, int page) {
        upcomingTripsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getUpcomingTrips(filterModel, page).enqueue(new Callback<TripPaginationModel>() {
            @Override
            public void onResponse(Call<TripPaginationModel> call, Response<TripPaginationModel> response) {
                if (response.isSuccessful()) {
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
            public void onFailure(Call<TripPaginationModel> call, Throwable t) {
                upcomingTripsMutableLiveData.setValue(null);
            }
        });
    }

    public void getActiveTrips(JsonObject filterModel, int page) {
        activeTripsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getActiveTrips(filterModel, page).enqueue(new Callback<TripPaginationModel>() {
            @Override
            public void onResponse(Call<TripPaginationModel> call, Response<TripPaginationModel> response) {
                if (response.isSuccessful()) {
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
            public void onFailure(Call<TripPaginationModel> call, Throwable t) {
                activeTripsMutableLiveData.setValue(null);
            }
        });
    }

    public void getHistoryTrips(JsonObject filterModel, int page) {
        historyTripsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getHistoryTrips(filterModel, page).enqueue(new Callback<TripPaginationModel>() {
            @Override
            public void onResponse(Call<TripPaginationModel> call, Response<TripPaginationModel> response) {
                if (response.isSuccessful()) {
                    historyTripsMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        historyTripsMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripPaginationModel> call, Throwable t) {
                historyTripsMutableLiveData.setValue(null);
            }
        });
    }

    public void getTripDetails(int id) {
        tripDetailsMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getTripDetails(id).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
                if (response.isSuccessful()) {
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

    public void getRoadMap(int tripId) {
        roadMapMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getRoadMap(tripId).enqueue(new Callback<RoadMapModel>() {
            @Override
            public void onResponse(Call<RoadMapModel> call, Response<RoadMapModel> response) {
                if (response.isSuccessful()) {
                    roadMapMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        roadMapMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RoadMapModel> call, Throwable t) {
                roadMapMutableLiveData.setValue(null);
            }
        });
    }

    public void getTripPhotoById(int id) {
        apiClient.getAPI().getTripPhotoAsBase64(id).enqueue(new Callback<PhotoBase64>() {
            @Override
            public void onResponse(Call<PhotoBase64> call, Response<PhotoBase64> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mvp.getImageAsBase64(id, response.body().data, null);
                    } else mvp.getImageAsBase64(id, null, "There is no response body");
                } else {
                    try {
                        mvp.getImageAsBase64(id, null, getErrorMessage(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PhotoBase64> call, Throwable t) {
                mvp.getImageAsBase64(id, null, null);
            }
        });
    }

    public void getTripType() {
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

    public void getPassengersInTrip(int tripId) {
        bookingPassengersInTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getBookingPassengersInTrip(tripId).enqueue(new Callback<PassengerModel>() {
            @Override
            public void onResponse(Call<PassengerModel> call, Response<PassengerModel> response) {
                if (response.isSuccessful()) {
                    bookingPassengersInTripMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        bookingPassengersInTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PassengerModel> call, Throwable t) {
                bookingPassengersInTripMutableLiveData.setValue(null);
            }
        });
    }

    public void getAllPassengersInTrip(int tripId) {
        allPassengersInTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getAllPassengersInTrip(tripId).enqueue(new Callback<PassengerModel>() {
            @Override
            public void onResponse(Call<PassengerModel> call, Response<PassengerModel> response) {
                if (response.isSuccessful()) {
                    allPassengersInTripMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        allPassengersInTripMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PassengerModel> call, Throwable t) {
                allPassengersInTripMutableLiveData.setValue(null);
            }
        });
    }

    public void getTripPhotos(int tripId) {
        tripPhotoPairMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().getTripPhotos(tripId).enqueue(new Callback<TripPhotoModel>() {
            @Override
            public void onResponse(Call<TripPhotoModel> call, Response<TripPhotoModel> response) {

                if (response.isSuccessful()) {
                    tripPhotoPairMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        tripPhotoPairMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripPhotoModel> call, Throwable t) {
                tripPhotoPairMutableLiveData.setValue(null);
            }
        });
    }

    public void createTrip(JsonObject jsonObject) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTrip(jsonObject).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
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
            public void onFailure(Call<TripDetailsModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void createTripPhoto(RequestBody tripId, MultipartBody.Part[] photos) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTripPhoto(tripId, photos).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
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
            public void onFailure(Call<TripDetailsModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void createTripPLace(CreateTripPlace createTripPlace) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTripPlace(createTripPlace).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
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
            public void onFailure(Call<TripDetailsModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void createTripType(CreateTripType createTripType) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().createTripType(createTripType).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
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
            public void onFailure(Call<TripDetailsModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void beginTrip(int tripId) {
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().beginTrip(tripId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
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

    public void endTrip(int tripId) {
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().endTrip(tripId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
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

    public void visitPlace(int tripId, int placeId) {
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().visitPlace(tripId, placeId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
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

    public void editTrip(JsonObject editTrip) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTrip(editTrip).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
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
            public void onFailure(Call<TripDetailsModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void editTripPhotos(RequestBody methodName, RequestBody tripId, MultipartBody.Part[] photos, MultipartBody.Part[] deletedImageIds) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTripPhotos(tripId,methodName,photos,deletedImageIds).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
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
            public void onFailure(Call<TripDetailsModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void editTripTypes(int id, CreateTripType createTripType) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTripTypes(id, createTripType).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
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
            public void onFailure(Call<TripDetailsModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void editTripPlaces(CreateTripPlace createTripPlace) {
        createTripMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().editTripPlaces(createTripPlace).enqueue(new Callback<TripDetailsModel>() {
            @Override
            public void onResponse(Call<TripDetailsModel> call, Response<TripDetailsModel> response) {
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
            public void onFailure(Call<TripDetailsModel> call, Throwable t) {
                createTripMutableLiveData.setValue(null);
            }
        });
    }

    public void confirmPassengerBooking(int customerId, int tripId) {
        confirmPassengerBooking = new MutableLiveData<>();
        apiClient.getAPI().confirmPassengerBooking(customerId, tripId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    confirmPassengerBooking.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        confirmPassengerBooking.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                confirmPassengerBooking.setValue(null);
            }
        });
    }

    public void checkPassengers(CheckPassengerModel model) {
        checkPassengersMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().checkPassengers(model).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    checkPassengersMutableLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        checkPassengersMutableLiveData.setValue(new Pair<>(null, getErrorMessage(response.errorBody().string())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                checkPassengersMutableLiveData.setValue(null);
            }
        });
    }

    public void cancelPassengerBooking(int id) {
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().cancelPassengerBooking(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
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

    public void deleteTrip(int id) {
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().deleteTrip(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
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

    public void deletePolls(int id) {
        successfulMutableLiveData = new MutableLiveData<>();
        apiClient.getAPI().deletePolls(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
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
