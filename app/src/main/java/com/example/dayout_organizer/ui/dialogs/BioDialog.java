package com.example.dayout_organizer.ui.dialogs;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.models.EditProfileModel;
import com.example.dayout_organizer.models.ProfileModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.fragments.profile.ProfileFragment;
import com.example.dayout_organizer.viewModels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class BioDialog extends Dialog {


    @BindView(R.id.bio_dialog_text_field)
    EditText bioDialogTextField;

    @BindView(R.id.bio_dialog_cancel_button)
    Button bioDialogCancelButton;

    @BindView(R.id.bio_dialog_save_button)
    Button bioDialogSaveButton;

    ProfileModel.Data data;

    LoadingDialog loadingDialog;

    Context context;



    public BioDialog(@NonNull Context context, ProfileModel.Data data) {
        super(context);
        this.context = context;
        this.data = data;
        setContentView(R.layout.bio_dialog);
        setCancelable(false);
        ButterKnife.bind(this);
        initViews();
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        Dialog dialog = new Dialog(requireActivity());
//
//        LayoutInflater inflater = requireActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.bio_dialog, null);
//        ButterKnife.bind(this, view);
//        initViews();
//        dialog.setContentView(view);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        return dialog;
//    }

    @Override
    public void show(){
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(wlp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // match width dialog
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.show();
    }

    public String getBioData(){
        return bioDialogTextField.getText().toString();
    }

    private void initViews() {
        bioDialogSaveButton.setOnClickListener(onSaveButtonClicked);
        bioDialogCancelButton.setOnClickListener(onCancelButtonClicked);
        loadingDialog = new LoadingDialog(getContext());
    }

    private EditProfileModel getData(){
        EditProfileModel model = new EditProfileModel();

        model.first_name = data.user.first_name;
        model.last_name = data.user.last_name;
        model.phone_number = data.user.phone_number;
        model.email = data.user.email;
        model.photo = data.user.photo;
        model.gender = data.user.gender;
        model.bio = bioDialogTextField.getText().toString();

        return model;
    }

    private final View.OnClickListener onSaveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loadingDialog.show();
            UserViewModel.getINSTANCE().editProfile(getData());
            UserViewModel.getINSTANCE().editProfileMutableLiveData.observe((MainActivity) context, bioObserver);
        }
    };

    private final Observer<Pair<EditProfileModel, String>> bioObserver = new Observer<Pair<EditProfileModel, String>>() {
        @Override
        public void onChanged(Pair<EditProfileModel, String> editProfileModelStringPair) {
            loadingDialog.dismiss();
            if(editProfileModelStringPair != null){
                if(editProfileModelStringPair.first != null){
                    dismiss();
                } else
                    new ErrorDialog(getContext(), editProfileModelStringPair.second).show();
            } else
                new ErrorDialog(getContext(), "Error Connection").show();
        }
    };

    private final View.OnClickListener onCancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };
}