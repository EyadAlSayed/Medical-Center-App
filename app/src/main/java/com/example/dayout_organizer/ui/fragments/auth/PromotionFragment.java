package com.example.dayout_organizer.ui.fragments.auth;

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
import com.example.dayout_organizer.helpers.system.PermissionsHelper;
import com.example.dayout_organizer.helpers.view.ConverterImage;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;


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

    String imageAsString;
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
                AuthViewModel.getINSTANCE().sendPromotionRequest(getJsonObject());
                AuthViewModel.getINSTANCE().successfulMutableLiveData.observe(requireActivity(), successfulObserver);
            }
        }
    };

    private final Observer<Pair<Boolean, String>> successfulObserver = new Observer<Pair<Boolean, String>>() {
        @Override
        public void onChanged(Pair<Boolean, String> booleanStringPair) {
            loadingDialog.dismiss();
            if (booleanStringPair != null) {
                if (booleanStringPair.first != null) {
                    NoteMessage.message(requireContext(), "your request send to review");
                    FN.popStack(requireActivity());
                } else new ErrorDialog(requireContext(), booleanStringPair.second).show();
            } else new ErrorDialog(requireContext(), "Connection Error").show();
        }
    };

    private JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone_number", userName.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());
        jsonObject.addProperty("description", userNotes.getText().toString());
        jsonObject.addProperty("credential_photo", imageAsString);
        return jsonObject;
    }

    private final View.OnClickListener onUploadImageClicked = v -> selectImage();

    private void selectImage() {
        if (PermissionsHelper.getREAD_EXTERNAL_STORAGE(requireActivity()))
            launcher.launch("image/*");
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            imageAsString = ConverterImage.convertUriToBase64(requireContext(), result);
            if (imageAsString != null) adjustVisibilities();
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
            userNameTextlayout.setError("This filed is required");
        } else if (!checkSyrianNumber()) {
            ok = false;
            userNameTextlayout.setErrorEnabled(true);
            userNameTextlayout.setError("Phone number is not correct");
        }


        if (password.getText().toString().isEmpty()) {
            ok = false;
            passwordTextlayout.setErrorEnabled(true);
            passwordTextlayout.setError("This filed is required");
        } else passwordTextlayout.setErrorEnabled(false);


        if (imageAsString == null || imageAsString.isEmpty()) {
            ok = false;
            uploadImageTV.setText("no image has selected");
            NoteMessage.errorMessage(requireContext(), "You must enter Id card photo");
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