package com.example.dayout_organizer.ui.fragments.auth;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.system.PermissionsHelper;
import com.example.dayout_organizer.helpers.view.ConverterImage;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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


    String imageAsString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_promotion, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        sendButton.setOnClickListener(onSendClicked);
        promotionImage.setOnClickListener(onUploadImageClicked);

    }

    private final View.OnClickListener onSendClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()) {
// TODO Send promotion request - EYAD
            }
        }
    };

    private final View.OnClickListener onUploadImageClicked = v -> selectImage();

    private void selectImage() {
        if (PermissionsHelper.getREAD_EXTERNAL_STORAGE(requireActivity()))
            launcher.launch("image/*");
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            imageAsString = ConverterImage.convertUriToBase64(requireContext(), result);
            if (imageAsString != null)
                adjustVisibilities();
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