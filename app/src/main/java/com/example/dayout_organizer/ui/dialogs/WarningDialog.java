package com.example.dayout_organizer.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dayout_organizer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class WarningDialog extends Dialog {

    Context context;

    LoadingDialog loadingDialog;

    @BindView(R.id.warning_dialog_message)
    TextView warningDialogMessage;

    @BindView(R.id.warning_dialog_yes)
    Button warningDialogYes;

    @BindView(R.id.warning_dialog_no)
    Button warningDialogNo;

    public WarningDialog(@NonNull Context context, String message) {
        super(context);
        this.context = context;
        setContentView(R.layout.warning_dialog);
        setCancelable(false);
        ButterKnife.bind(this);
        initViews(message);
    }

    private void initViews(String message) {
        loadingDialog = new LoadingDialog(getContext());
        warningDialogMessage.setText(message);
        warningDialogNo.setOnClickListener(onNoButtonClicked);
    }

    private final View.OnClickListener onYesButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private final View.OnClickListener onNoButtonClicked = view -> dismiss();

    @Override
    public void show() {
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
