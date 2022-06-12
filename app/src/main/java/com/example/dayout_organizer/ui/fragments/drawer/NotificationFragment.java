package com.example.dayout_organizer.ui.fragments.drawer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.adapter.recyclers.NotificationsAdapter;
import com.example.dayout_organizer.helpers.view.FN;
import com.example.dayout_organizer.models.notification.NotificationData;
import com.example.dayout_organizer.models.notification.NotificationModel;
import com.example.dayout_organizer.ui.activities.MainActivity;
import com.example.dayout_organizer.ui.dialogs.ErrorDialog;
import com.example.dayout_organizer.ui.dialogs.LoadingDialog;
import com.example.dayout_organizer.viewModels.UserViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class NotificationFragment extends Fragment {

    View view;

    @BindView(R.id.notifications_back_arrow)
    ImageButton notificationsBackArrow;

    @BindView(R.id.notifications_recycler_view)
    RecyclerView notificationsRecyclerView;

    @BindView(R.id.no_notifications_TV)
    TextView noNotifications;


    NotificationsAdapter adapter;

    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getDataFromApi();
        return view;
    }

    @Override
    public void onStart() {
        ((MainActivity)requireActivity()).hideBottomBar();
        super.onStart();
    }

    private void initViews(){
        loadingDialog = new LoadingDialog(requireContext());
        initRecycler();
        notificationsBackArrow.setOnClickListener(onBackClicked);
    }

    private void initRecycler(){
        notificationsRecyclerView.setHasFixedSize(true);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new NotificationsAdapter(new ArrayList<>(), requireContext());
        notificationsRecyclerView.setAdapter(adapter);
    }

    private void getDataFromApi(){
        loadingDialog.show();
        UserViewModel.getINSTANCE().getNotifications();
        UserViewModel.getINSTANCE().notificationsMutableLiveData.observe(requireActivity(), notificationsObserver);
    }

    private final Observer<Pair<NotificationModel, String>> notificationsObserver = new Observer<Pair<NotificationModel, String>>() {
        @Override
        public void onChanged(Pair<NotificationModel, String> notificationModelStringPair) {
            loadingDialog.dismiss();
            if(notificationModelStringPair != null){
                if(notificationModelStringPair.first != null){
                    if(!notificationModelStringPair.first.data.isEmpty()) {
                        noNotifications.setVisibility(View.GONE);
                        notificationsRecyclerView.setVisibility(View.VISIBLE);
                        adapter.refreshList(notificationModelStringPair.first.data);
                    } else{
                        noNotifications.setVisibility(View.VISIBLE);
                        notificationsRecyclerView.setVisibility(View.GONE);
                    }
                } else
                    new ErrorDialog(requireContext(), notificationModelStringPair.second).show();
            } else
                new ErrorDialog(requireContext(), "Error Connection");
        }
    };

    private final View.OnClickListener onBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FN.popTopStack(requireActivity());
        }
    };
}
