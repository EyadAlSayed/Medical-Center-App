package com.example.dayout_organizer.ui.fragments.auth.forgetPassword;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.ui.dialogs.notify.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.notify.LoadingDialog;
import com.example.dayout_organizer.ui.fragments.auth.LoginFragment;
import com.example.dayout_organizer.viewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.AUTH_FRC;
import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;


public class ChangePasswordFragment extends Fragment {


    @BindView(R.id.verification_code)
    TextInputEditText verificationCode;
    @BindView(R.id.verification_code_layout)
    TextInputLayout verificationCodeLayout;
    @BindView(R.id.new_password)
    TextInputEditText newPassword;
    @BindView(R.id.new_password_textlayout)
    TextInputLayout newPasswordTextlayout;
    @BindView(R.id.new_password_confirmation)
    TextInputEditText newPasswordConfirmation;
    @BindView(R.id.new_password_confirmation_textlayout)
    TextInputLayout newPasswordConfirmationTextlayout;
    @BindView(R.id.send_btn)
    Button sendButton;

    View view;
    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView(){
        loadingDialog = new LoadingDialog(requireContext());
        sendButton.setOnClickListener(onSendClicked);
    }

    private final View.OnClickListener onSendClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkInfo()){
                loadingDialog.show();
                AuthViewModel.getINSTANCE().resetPassword(getJsonObject());
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
                    NoteMessage.showSnackBar(requireActivity(),"You password have been changed");
                    FN.replaceFadeFragment(AUTH_FRC,requireActivity(),new LoginFragment());
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
        jsonObject.addProperty("confirm_code",verificationCode.getText().toString());
        jsonObject.addProperty("new_password",newPassword.getText().toString());
        return jsonObject;
    }

    private  boolean checkInfo(){
        boolean ok = true;
        if (verificationCode.getText().toString().isEmpty()){
            verificationCodeLayout.setErrorEnabled(true);
            verificationCodeLayout.setError("This Field can not be empty");
            ok =false;
        }
        if (newPassword.getText().toString().isEmpty()){
            newPasswordTextlayout.setErrorEnabled(true);
            newPasswordTextlayout.setError("This Field can not be empty");
            ok = false;
        }
        if (newPasswordConfirmation.getText().toString().isEmpty()){
            newPasswordConfirmationTextlayout.setErrorEnabled(true);
            newPasswordConfirmationTextlayout.setError("This Field can not be empty");
            ok = false;
        }
        if (!newPasswordConfirmation.getText().toString().equals(newPassword.getText().toString())){
            newPasswordConfirmationTextlayout.setErrorEnabled(true);
            newPasswordTextlayout.setErrorEnabled(true);
            newPasswordConfirmationTextlayout.setError("This Field is not the same as password");
            newPasswordTextlayout.setError("This Field is not the same as confirm password");
            ok =false;
        }
        return  ok;
    }
}
