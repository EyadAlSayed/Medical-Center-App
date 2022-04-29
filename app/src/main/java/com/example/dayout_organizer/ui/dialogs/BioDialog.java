package com.example.dayout_organizer.ui.dialogs;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

public class BioDialog extends AppCompatDialogFragment {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bio_dialog_text_field)
    EditText bioDialogTextField;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bio_dialog_cancel_button)
    Button bioDialogCancelButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bio_dialog_save_button)
    Button bioDialogSaveButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.bio_dialog, null);
        ButterKnife.bind(this, view);
        initViews();

        builder.setView(view);

        return builder.create();
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
