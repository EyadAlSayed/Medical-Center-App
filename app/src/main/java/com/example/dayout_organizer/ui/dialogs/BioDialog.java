package com.example.dayout_organizer.ui.dialogs;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.dayout_organizer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class BioDialog extends AppCompatDialogFragment {


    @BindView(R.id.bio_dialog_text_field)
    EditText bioDialogTextField;

    @BindView(R.id.bio_dialog_cancel_button)
    Button bioDialogCancelButton;

    @BindView(R.id.bio_dialog_save_button)
    Button bioDialogSaveButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.bio_dialog, null);
        ButterKnife.bind(this, view);
        initViews();
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    private final View.OnClickListener onSaveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO Send text back - Caesar.
            dismiss();
        }
    };

    private final View.OnClickListener onCancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };

    private void initViews() {
        bioDialogSaveButton.setOnClickListener(onSaveButtonClicked);
        bioDialogCancelButton.setOnClickListener(onCancelButtonClicked);
    }
}
