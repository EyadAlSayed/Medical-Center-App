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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.NoteMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class ReportDialog extends Dialog {

    Context context;
    String username;
    LoadingDialog loadingDialog;

    @BindView(R.id.report_dialog_user_name)
    TextView userName;

    @BindView(R.id.report_dialog_purpose)
    EditText purpose;

    @BindView(R.id.report_dialog_cancel_button)
    Button cancelButton;

    @BindView(R.id.report_dialog_report_button)
    Button reportButton;

    public ReportDialog(@NonNull Context context, String username) {
        super(context);
        this.context = context;
        this.username = username;
        setContentView(R.layout.report_dialog);
        setCancelable(false);
        ButterKnife.bind(this);
        initViews();
    }

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

    private void initViews() {
        loadingDialog = new LoadingDialog(getContext());
        cancelButton.setOnClickListener(onCancelClicked);
        reportButton.setOnClickListener(onReportClicked);
    }

    private final View.OnClickListener onCancelClicked = v -> dismiss();

    private final View.OnClickListener onReportClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //report
            NoteMessage.message(getContext(), username + " was reported.");
            dismiss();
        }
    };
}
