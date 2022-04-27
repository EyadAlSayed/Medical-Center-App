package com.example.dayout_organizer.ui.fragments.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.AUTH_FRC;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        loginButton.setOnClickListener(onLoginClicked);
        createAccountTxt.setOnClickListener(onCreateClicked);
        password.addTextChangedListener(onTextChanged);
    }


    private final View.OnClickListener onLoginClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requireActivity().startActivity(new Intent(requireActivity(), MainActivity.class));
            requireActivity().finish();

            if (checkInfo()) {
                //TODO EYAD send login request;
            }
        }
    };

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

    private boolean checkSyrianNumber() {
        String _userName = userName.getText().toString();
        if (_userName.length() < 10) return false;
        if (_userName.contains("+")) return false;
        if (_userName.charAt(0) != '0' && _userName.charAt(1) != '9') return false;
        return true;
    }

}