package com.example.dayout_organizer.ui.fragments.auth;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.viewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@SuppressLint("NonConstantResourceId")
public class PromotionFragment extends Fragment {


    View view;

    @BindView(R.id.user_name)
    TextInputEditText userName;

    @BindView(R.id.user_name_textlayout)
    TextInputLayout userNameTextlayout;

    @BindView(R.id.password)
    TextInputEditText password;

    @BindView(R.id.password_textlayout)
    TextInputLayout passwordTextlayout;

    @BindView(R.id.promotion_upload_image)
    ImageButton promotionImage;

    @BindView(R.id.send_btn)
    Button sendButton;

    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    @BindView(R.id.uploadImage_TV)
    TextView uploadImageTV;

    @BindView(R.id.user_notes)
    EditText userNotes;

    Uri credentialPhoto;
    LoadingDialog loadingDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_promotion, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        loadingDialog = new LoadingDialog(requireContext());
        sendButton.setOnClickListener(onSendClicked);
        promotionImage.setOnClickListener(onUploadImageClicked);

    }

    private final View.OnClickListener onSendClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()) {
                loadingDialog.show();
                AuthViewModel.getINSTANCE().sendPromotionRequest(getRequestBody(
                        userName.getText().toString()),
                        getRequestBody(password.getText().toString()),
                        getRequestBody(userNotes.getText().toString()),
                        getPhotoAsRequestBody());
                AuthViewModel.getINSTANCE().successfulMutableLiveData.observe(requireActivity(), successfulObserver);
            }
        }
    };

    private RequestBody getRequestBody(String body) {
        return HttpRequestConverter.createStringAsRequestBody("multipart/form-data", body);
    }

    private final Observer<Pair<Boolean, String>> successfulObserver = new Observer<Pair<Boolean, String>>() {
        @Override
        public void onChanged(Pair<Boolean, String> booleanStringPair) {
            loadingDialog.dismiss();
            if (booleanStringPair != null) {
                if (booleanStringPair.first != null) {
                    NoteMessage.message(requireContext(), getResources().getString(R.string.request_sent));
                    FN.popStack(requireActivity());
                } else new ErrorDialog(requireContext(), booleanStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), getResources().getString(R.string.error_connection)).show();
        }
    };

    private MultipartBody.Part getPhotoAsRequestBody(){
        String path = ConverterImage.createImageFilePath(requireActivity(),credentialPhoto);
        File file = new File(path);
        RequestBody photoBody = HttpRequestConverter.createFileAsRequestBody("multipart/form-data",file);
        return HttpRequestConverter.createFormDataFile("credential_photo",file.getName(),photoBody);
    }
    private final View.OnClickListener onUploadImageClicked = v -> selectImage();

    private void selectImage() {
        if (PermissionsHelper.getREAD_EXTERNAL_STORAGE(requireActivity()))
            launcher.launch("image/*");
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            credentialPhoto = result;
        }
    });

    private void adjustVisibilities() {
        linearLayout.animate().setDuration(450).alpha(0);
        new Handler(Looper.getMainLooper()).postDelayed(() -> linearLayout.setVisibility(View.GONE), 500);
    }

    private boolean checkInfo() {

        boolean ok = true;
        if (userName.getText().toString().isEmpty()) {
            ok = false;
            userNameTextlayout.setErrorEnabled(true);
            userNameTextlayout.setError(getResources().getString(R.string.required));
        } else if (!checkSyrianNumber()) {
            ok = false;
            userNameTextlayout.setErrorEnabled(true);
            userNameTextlayout.setError(getResources().getString(R.string.not_a_phone_number));
        }


        if (password.getText().toString().isEmpty()) {
            ok = false;
            passwordTextlayout.setErrorEnabled(true);
            passwordTextlayout.setError(getResources().getString(R.string.required));
        } else passwordTextlayout.setErrorEnabled(false);


        if (credentialPhoto == null) {
            ok = false;
            uploadImageTV.setText(getResources().getString(R.string.no_image));
            NoteMessage.errorMessage(requireContext(), getResources().getString(R.string.id_photo_missing));
        }

        return ok;
    }

    private boolean checkSyrianNumber() {
        String _userName = userName.getText().toString();
        if (_userName.length() < 10) return false;
        if (_userName.contains("+")) return false;
        if (_userName.charAt(0) != '0' && _userName.charAt(1) != '9') return false;
        return true;
    }

}