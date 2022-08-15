package com.example.dayout_organizer.ui.fragments.auth.forgetPassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import android.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.viewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.AUTH_FRC;
import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;


public class ForgetPasswordFragment extends Fragment {


    @BindView(R.id.phone_number)
    TextInputEditText phoneNumber;
    @BindView(R.id.phone_number_textlayout)
    TextInputLayout phoneNumberTextlayout;
    @BindView(R.id.confirm_button)
    Button confirmButton;

    View view;
    LoadingDialog loadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        ButterKnife.bind(this,view);
        initView();
        return  view;
    }

    private void initView(){
        loadingDialog = new LoadingDialog(requireContext());
        confirmButton.setOnClickListener(onConfirmClicked);
    }

    private final View.OnClickListener onConfirmClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(checkInfo()){
                loadingDialog.show();
                AuthViewModel.getINSTANCE().checkPhoneNumberExist(getJsonObject());
                AuthViewModel.getINSTANCE().successfulMutableLiveData.observe(requireActivity(),successfulObserver);
            }
        }
    };
    private final Observer<Pair<Boolean,String>> successfulObserver = new Observer<Pair<Boolean, String>>() {
        @Override
        public void onChanged(Pair<Boolean, String> booleanStringPair) {
            loadingDialog.dismiss();
            if (booleanStringPair != null){
                if (booleanStringPair.first != null){
                    FN.addFixedNameFadeFragment(AUTH_FRC,requireActivity(),new ChangePasswordFragment());
                }
                else{
                    new ErrorDialog(requireContext(),booleanStringPair.second).show();
                }
            }
            else new ErrorDialog(requireContext(),getString(R.string.error_connection)).show();
        }
    };

    private JsonObject getJsonObject(){
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("phone_number",phoneNumber.getText().toString());
        return jsonObject;
    }

    private boolean checkInfo(){
       boolean ok = true;

       if (phoneNumber.getText().toString().isEmpty()){
           phoneNumberTextlayout.setErrorEnabled(true);
           phoneNumberTextlayout.setError("This Field can not be empty");
           ok = false;
       }
       else if(!checkSyrianNumber())  {
           phoneNumberTextlayout.setErrorEnabled(true);
           phoneNumberTextlayout.setError("You have to enter syrian number");
           ok = false;
       }

       return ok;
    }

    private boolean checkSyrianNumber() {
        String _phoneNumber = phoneNumber.getText().toString();
        if (_phoneNumber.length() < 10) return false;
        if (_phoneNumber.contains("+")) return false;
        if (_phoneNumber.charAt(0) != '0' && _phoneNumber.charAt(1) != '9') return false;
        return true;
    }
}

