package com.example.dayout_organizer.ui.dialogs.notify;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dayout_organizer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class SuccessDialog extends Dialog {

    String successMessage;

    @BindView(R.id.success_message)
    TextView successMessageTV;

    @BindView(R.id.success_ok_button)
    Button okButton;

    public SuccessDialog(@NonNull Context context, String successMessage) {
        super(context);
        setContentView(R.layout.success_dialog_layout);
        setCancelable(false);
        ButterKnife.bind(this);
        initViews(successMessage);
    }

    private void initViews(String successMessage){
        this.successMessage = successMessage;
        okButton.setOnClickListener(v -> cancel());
    }

    @Override
    public void show(){
        successMessageTV.setText(successMessage);

        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(wlp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // match width dialog
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.show();
    }
}
