package com.example.dayout_organizer.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.PassengersListAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.passenger.PassengerData;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.viewModels.TripViewModel;

import java.util.List;

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

    // attributes needed for dealing with passengers list.
    PassengersListAdapter adapter;
    List<PassengerData> list;
    int position;
    boolean deletingPassenger = false;
    TextView totalTxt;

    boolean discardingPoll = false;

    public WarningDialog(@NonNull Context context, String message, boolean discardingPoll) {
        super(context);
        this.context = context;
        this.discardingPoll = discardingPoll;
        setContentView(R.layout.warning_dialog);
        setCancelable(false);
        ButterKnife.bind(this);
        initViews(message);
    }

    public WarningDialog(Context context, String message, PassengersListAdapter adapter, List<PassengerData> list, int position, TextView totalTxt){
        super(context);
        this.context = context;
        this.adapter = adapter;
        this.list = list;
        this.position = position;
        this.deletingPassenger = true;
        this.totalTxt = totalTxt;
        setContentView(R.layout.warning_dialog);
        setCancelable(false);
        ButterKnife.bind(this);
        initViews(message);
    }

    private void initViews(String message) {
        loadingDialog = new LoadingDialog(getContext());
        warningDialogMessage.setText(message);
        warningDialogNo.setOnClickListener(onNoButtonClicked);
        warningDialogYes.setOnClickListener(onYesButtonClicked);
    }

    private void setTotalText(){
        int newTotal = Integer.parseInt(totalTxt.getText().toString()) - list.get(position).passengers.size();
        totalTxt.setText(String.valueOf(newTotal));
    }

    private final View.OnClickListener onYesButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(deletingPassenger){
                setTotalText();
                cancelPassengerBooking(list.get(position).id);
                list.remove(position);
                adapter.notifyItemRemoved(position);
            } else if(discardingPoll){
                FN.popStack((MainActivity) context);
            }
            dismiss();
        }
    };

    private final View.OnClickListener onNoButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(deletingPassenger){
                PassengerData item = list.get(position);
                list.remove(position);
                list.add(position, item);
                adapter.notifyDataSetChanged();
            }
            dismiss();
        }
    };

    private void cancelPassengerBooking(int id){
        TripViewModel.getINSTANCE().cancelPassengerBooking(id);
        TripViewModel.getINSTANCE().cancelPassengerBookingMutableLiveData.observe((MainActivity) context, cancelBookingObserver);
    }

    private final Observer<Pair<Boolean, String>> cancelBookingObserver = new Observer<Pair<Boolean, String>>() {
        @Override
        public void onChanged(Pair<Boolean, String> booleanStringPair) {
            if(booleanStringPair != null){
                if(booleanStringPair.first != null){
                    NoteMessage.message((MainActivity) context, "Booking Canceled");
                }else
                    new ErrorDialog((MainActivity) context, booleanStringPair.second).show();
            } else
                new ErrorDialog((MainActivity) context, "Error Connection").show();
        }
    };

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
