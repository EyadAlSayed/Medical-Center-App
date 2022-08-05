package com.example.dayout_organizer.ui.fragments.trips.editTrip;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.system.HttpRequestConverter;
import com.example.dayout_organizer.helpers.system.PermissionsHelper;
import com.example.dayout_organizer.helpers.view.ConverterImage;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.ImageViewer;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.trip.TripData;
import com.example.dayout_organizer.models.trip.TripDetailsModel;
import com.example.dayout_organizer.models.trip.photo.TripPhotoModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.fragments.home.HomeFragment;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;
import static com.example.dayout_organizer.helpers.view.ImageViewer.IMAGE_BASE_URL;

@SuppressLint("NonConstantResourceId")
public class EditImageTripFragment extends Fragment {


    View view;

    @BindView(R.id.select_image_btn)
    Button selectImageButton;
    @BindView(R.id.select_img)
    ImageView selectImg;
    @BindView(R.id.previous_btn)
    Button previousButton;
    @BindView(R.id.next_btn)
    Button nextButton;
    @BindView(R.id.create_btn)
    Button createButton;
    @BindView(R.id.cancel_button)
    ImageButton cancelButton;


    TripPhotoModel tripPhotoModel;

    int urlIdx, uriIdx;
    LoadingDialog loadingDialog;
    ArrayList<Integer> deletedIds;
    List<Uri> uris;

    TripData tripData;


    public EditImageTripFragment(TripData tripData) {
        this.tripData = tripData;
        this.urlIdx = 0;
        deletedIds = new ArrayList<>();
        uris = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_image_trip, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDataFromApi();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity) requireActivity()).hideBottomBar();
        super.onStart();
    }

    private void getDataFromApi() {
        loadingDialog.show();
        TripViewModel.getINSTANCE().getTripPhotos(tripData.id);
        TripViewModel.getINSTANCE().tripPhotoPairMutableLiveData.observe(requireActivity(), tripPhotosObserver);
    }

    private final Observer<Pair<TripPhotoModel, String>> tripPhotosObserver = new Observer<Pair<TripPhotoModel, String>>() {
        @Override
        public void onChanged(Pair<TripPhotoModel, String> tripPhotoModelStringPair) {

            if (tripPhotoModelStringPair != null) {
                if (tripPhotoModelStringPair.first != null) {
                    loadingDialog.dismiss();
                    tripPhotoModel = tripPhotoModelStringPair.first;

                    ImageViewer.downloadImage(requireContext(), selectImg, R.drawable.ic_app_logo, IMAGE_BASE_URL + tripPhotoModel.data.get(0).path);
                } else {
                    reOrderTripPhotos(1500);
                }
            } else reOrderTripPhotos(2000);
        }
    };

    private void reOrderTripPhotos(int timeToReOrder) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> TripViewModel.getINSTANCE().getTripPhotos(tripData.id), timeToReOrder);
    }

    private void initView() {

        //   imageBase64 = new ArrayList<>();
        // downloadHandler = new Handler(Looper.getMainLooper());
        loadingDialog = new LoadingDialog(requireContext());

        //createTripPhoto = new CreateTripPhoto(tripData.id, imageBase64);
        selectImageButton.setOnClickListener(onSelectImageClicked);
        previousButton.setOnClickListener(onPreviousClicked);
        nextButton.setOnClickListener(onNextClicked);
        createButton.setOnClickListener(onCreateClicked);
        cancelButton.setOnClickListener(onCancelClicked);
    }

    private final View.OnClickListener onSelectImageClicked = v -> pickImage();

    private final View.OnClickListener onCancelClicked = v -> {



        if (tripPhotoModel.data.size() > 0 && urlIdx >= 0 && urlIdx < tripPhotoModel.data.size()) {

            if (tripPhotoModel.data.size() == 1) selectImg.setImageURI(Uri.EMPTY);
            else if (urlIdx == 0)
                ImageViewer.downloadImage(requireContext(), selectImg, R.drawable.ic_app_logo, IMAGE_BASE_URL + tripPhotoModel.data.get(urlIdx + 1).path);
            else
                ImageViewer.downloadImage(requireContext(), selectImg, R.drawable.ic_app_logo, IMAGE_BASE_URL + tripPhotoModel.data.get(urlIdx - 1).path);

            deletedIds.add(tripPhotoModel.data.get(urlIdx).id);
            tripPhotoModel.data.remove(urlIdx);
        }
    };

    private final View.OnClickListener onPreviousClicked = v -> {
        if (urlIdx > 0) {

            ImageViewer.downloadImage(requireContext(), selectImg, R.drawable.ic_app_logo, IMAGE_BASE_URL + tripPhotoModel.data.get(--urlIdx).path);
        } else if (uriIdx > 0) {
            selectImg.setImageURI(uris.get(--uriIdx));
        }

    };

    private final View.OnClickListener onNextClicked = v -> {

        if (urlIdx < tripPhotoModel.data.size() - 1) {

            ImageViewer.downloadImage(requireContext(), selectImg, R.drawable.ic_app_logo, IMAGE_BASE_URL + tripPhotoModel.data.get(++urlIdx).path);
        } else if (uriIdx < uris.size() - 1) {
            selectImg.setImageURI(uris.get(++uriIdx));
        }
    };

    private final View.OnClickListener onCreateClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()) {
                loadingDialog.show();
                TripViewModel.getINSTANCE().editTripPhotos(getMethodNameRequestBody(), getIdRequestBody(), getPhotos(), getDeletedIds());
                TripViewModel.getINSTANCE().createTripMutableLiveData.observe(requireActivity(), tripObserver);
            }
        }
    };

    private final Observer<Pair<TripDetailsModel, String>> tripObserver = new Observer<Pair<TripDetailsModel, String>>() {
        @Override
        public void onChanged(Pair<TripDetailsModel, String> tripStringPair) {
            loadingDialog.dismiss();
            if (tripStringPair != null) {
                if (tripStringPair.first != null) {
                    NoteMessage.showSnackBar(requireActivity(), getResources().getString(R.string.edit_successfully));
                    FN.popAllStack(requireActivity());
                    FN.addFixedNameFadeFragment(MAIN_FRC, requireActivity(), new HomeFragment());
                } else {
                    new ErrorDialog(requireContext(), tripStringPair.second).show();
                }
            } else {
                new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
            }
        }
    };

    private void pickImage() {
        if (PermissionsHelper.getREAD_EXTERNAL_STORAGE(requireActivity()))
            launcher.launch("image/*");
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            uris.add(result);
            uriIdx = uris.size() - 1;
            selectImg.setImageURI(result);
        }
    });

    public RequestBody getMethodNameRequestBody() {
        return HttpRequestConverter.createStringAsRequestBody("multipart/form-data", "PUT");
    }

    public RequestBody getIdRequestBody() {
        Log.e("TripViewModel", "getIdRequestBody: " + tripData.id);
        return HttpRequestConverter.createStringAsRequestBody("multipart/form-data", String.valueOf(tripData.id));
    }

    private MultipartBody.Part[] getPhotos() {
        try {
            MultipartBody.Part[] photos = new MultipartBody.Part[uris.size()];

            for (int idx = 0; idx < photos.length; idx++) {
                String path = ConverterImage.createImageFilePath(requireActivity(), uris.get(idx));
                File file = new File(path);
                RequestBody photoBody = HttpRequestConverter.createFileAsRequestBody("multipart/form-data", file);
                photos[idx] = HttpRequestConverter.createFormDataFile("photos[]", file.getName(), photoBody);
            }
            return photos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MultipartBody.Part[] getDeletedIds() {

        MultipartBody.Part[] ids = new MultipartBody.Part[deletedIds.size()];
        for (int i = 0; i < deletedIds.size(); i++) {
            ids[i] = HttpRequestConverter.createFormDataAttribute("deleted_photo_ids[]", String.valueOf(deletedIds.get(i)));
        }
        return ids;


    }

    private boolean checkInfo() {
        if (tripPhotoModel.data.size() > 0 || uris.size() > 0) {
            return true;
        } else {
            NoteMessage.showSnackBar(requireActivity(), getResources().getString(R.string.select_photo_first));
            return false;
        }
    }
}