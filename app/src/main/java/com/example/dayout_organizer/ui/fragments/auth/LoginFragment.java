package com.example.dayout_organizer.ui.fragments.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.config.AppSharedPreferences;
import com.example.dayout_organizer.helpers.view.FN;

import com.example.dayout_organizer.models.authModels.LoginModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.AUTH_FRC;
import static com.example.dayout_organizer.config.AppSharedPreferences.CACHE_AUTH_DATA;

@SuppressLint("NonConstantResourceId")
public class LoginFragment extends Fragment {


    View view;

    @BindView(R.id.create_account_txt)
    TextView createAccountTxt;
    @BindView(R.id.user_name)
    TextInputEditText userName;
    @BindView(R.id.user_name_textlayout)
    TextInputLayout userNameTextlayout;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.password_textlayout)
    TextInputLayout passwordTextlayout;
    @BindView(R.id.remember_me_switch)
    Switch rememberMeSwitch;
    @BindView(R.id.login_btn)
    Button loginButton;

    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        loadingDialog = new LoadingDialog(requireContext());
        loginButton.setOnClickListener(onLoginClicked);
        createAccountTxt.setOnClickListener(onCreateClicked);
        password.addTextChangedListener(onTextChanged);
    }


    private final View.OnClickListener onLoginClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()) {
                loadingDialog.show();
                AuthViewModel.getINSTANCE().login(getLoginInfo());
                AuthViewModel.getINSTANCE().loginMutableLiveData.observe(requireActivity(), loginObserver);
            }
        }
    };


    private final Observer<Pair<LoginModel, String>> loginObserver = new Observer<Pair<LoginModel, String>>() {
        @Override
        public void onChanged(Pair<LoginModel, String> loginModelStringPair) {
            loadingDialog.dismiss();
            if (loginModelStringPair != null) {
                if (loginModelStringPair.first != null) {
                    if (checkUserRole(loginModelStringPair.first.data.role)) {
                        new ErrorDialog(requireContext(), "You do not have the enough right\nto login to this app").show();
                    } else {
                        cacheData(loginModelStringPair.first.data.id, loginModelStringPair.first.data.token);
                        openMainActivity();
                    }
                } else new ErrorDialog(requireContext(), loginModelStringPair.second).show();
            } else new ErrorDialog(requireContext(), "Error connection").show();
        }
    };

    private JsonObject getLoginInfo() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone_number", userName.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());
        return jsonObject;
    }

    private void cacheData(int id, String token) {
        AppSharedPreferences.CACHE_REMEMBER_ME(rememberMeSwitch.isChecked());
        AppSharedPreferences.CACHE_AUTH_DATA(id, token);
    }

    private void openMainActivity() {
        requireActivity().startActivity(new Intent(requireActivity(), MainActivity.class));
        requireActivity().finish();
    }

    private final View.OnClickListener onCreateClicked = v -> FN.addFixedNameFadeFragment(AUTH_FRC, requireActivity(), new SignUpFragment());

    private final TextWatcher onTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            passwordTextlayout.setErrorEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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

    private boolean checkUserRole(List<LoginModel.Role> roles) {
        for (LoginModel.Role role : roles) {
            if (role.id == 1 || role.id == 3) {
                // 1 mean admin
                // 2 mean organizer
                return false;
            }
        }
        return true;
    }

    private boolean checkSyrianNumber() {
        String _userName = userName.getText().toString();
        if (_userName.length() < 10) return false;
        if (_userName.contains("+")) return false;
        if (_userName.charAt(0) != '0' && _userName.charAt(1) != '9') return false;
        return true;
    }

}