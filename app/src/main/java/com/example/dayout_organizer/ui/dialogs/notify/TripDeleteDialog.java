package com.example.dayout_organizer.ui.dialogs.notify;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.myTrips.ActiveTripAdapter;
import com.example.dayout_organizer.adapter.recyclers.myTrips.OldTripAdapter;
import com.example.dayout_organizer.adapter.recyclers.myTrips.UpComingTripAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.fragments.home.HomeFragment;
import com.example.dayout_organizer.viewModels.TripViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;


public class TripDeleteDialog extends Dialog {

    // this dialog done by EYAD

    @BindView(R.id.warning_dialog_message)
    TextView warningDialogMessage;
    @BindView(R.id.warning_dialog_yes)
    Button warningDialogYes;
    @BindView(R.id.warning_dialog_no)
    Button warningDialogNo;

    Context context;
    LoadingDialog loadingDialog;
   public int tripId;




    public TripDeleteDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        this.tripId = tripId;
        setContentView(R.layout.warning_dialog);
        setCancelable(false);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        loadingDialog = new LoadingDialog(context);
        warningDialogMessage.setText(context.getResources().getString(R.string.deleting_trip));
        warningDialogNo.setOnClickListener(v -> dismiss());
       // warningDialogYes.setOnClickListener(onYesClicked);
    }


    public void setWarningDialogYes(View.OnClickListener onYesClicked) {
        this.warningDialogYes.setOnClickListener(onYesClicked);
    }

    private final View.OnClickListener onYesClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadingDialog.show();
            deleteTrip(tripId);
        }
    };

    private void deleteTrip(int tripId) {
        TripViewModel.getINSTANCE().deleteTrip(tripId);
        TripViewModel.getINSTANCE().successfulMutableLiveData.observe((MainActivity) context, deleteTripObserver);
    }

    private final Observer<Pair<Boolean, String>> deleteTripObserver = new Observer<Pair<Boolean, String>>() {
        @Override
        public void onChanged(Pair<Boolean, String> booleanStringPair) {
            loadingDialog.dismiss();
            if (booleanStringPair != null) {
                if (booleanStringPair.first != null) {
                    NoteMessage.showSnackBar((MainActivity) context, context.getResources().getString(R.string.successfully_deleted));
                    dismiss();
                }
            } else
                NoteMessage.showSnackBar((MainActivity) context, context.getString(R.string.cant_be_deleted));
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
