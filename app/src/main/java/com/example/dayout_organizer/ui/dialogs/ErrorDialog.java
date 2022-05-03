package com.example.dayout_organizer.ui.dialogs;

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
public class ErrorDialog extends Dialog {


    String errorMessage;
    @BindView(R.id.error_txt)
    TextView errorTxt;
    @BindView(R.id.done_btn)
    Button doneButton;

    public ErrorDialog(@NonNull Context context, String errorMessage) {
        super(context);
        setContentView(R.layout.error_dialog);
        ButterKnife.bind(this);
        setCancelable(false);
        ButterKnife.bind(this);
        initView(errorMessage);
    }

    private void initView(String errorMessage){
        this.errorMessage = errorMessage;
        doneButton.setOnClickListener(v -> cancel());
    }

    @Override
    public void show() {

        errorTxt.setText(errorMessage);

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
