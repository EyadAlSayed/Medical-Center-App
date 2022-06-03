package com.example.dayout_organizer.adapter.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.ImageViewer;
import com.example.dayout_organizer.models.PassengerData;
import com.example.dayout_organizer.ui.fragments.trips.PassengersCheckListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;

public class CheckPassengersListAdapter extends RecyclerView.Adapter<CheckPassengersListAdapter.ViewHolder> {

    Context context;
    List<PassengerData> passengers;
    PassengersCheckListFragment fragment;

    public CheckPassengersListAdapter(List<PassengerData> passengers, Context context, PassengersCheckListFragment fragment){
        this.context = context;
        this.passengers = passengers;
        this.fragment = fragment;
    }

    public void refreshList(List<PassengerData> passengers){
        this.passengers = passengers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CheckPassengersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_check_passenger_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckPassengersListAdapter.ViewHolder holder, int position) {
        downloadUserImage(passengers.get(position).photo, holder.passengerPhoto);
        holder.passengerName.setText(passengers.get(position).name);
        holder.passengerCheckBox.setChecked(passengers.get(position).checked);
    }

    @Override
    public int getItemCount() {
        return passengers.size();
    }

    private void downloadUserImage(String url, ImageView imageView) {
        String baseUrl = BASE_URL.substring(0, BASE_URL.length() - 1);
        ImageViewer.downloadCircleImage(context, imageView, R.drawable.profile_place_holder_orange, baseUrl + url);
    }

    @SuppressLint("NonConstantResourceId")
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.check_passenger_item_photo)
        ImageView passengerPhoto;

        @BindView(R.id.check_passenger_item_name)
        TextView passengerName;

        @BindView(R.id.check_passenger_item_checkbox)
        CheckBox passengerCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            passengerCheckBox.setOnClickListener(onCheckChanged);
            itemView.setOnClickListener(this);
        }

        private final View.OnClickListener onCheckChanged = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: check passenger.
                PassengerData data = passengers.get(getAdapterPosition());
                data.checked = passengerCheckBox.isChecked();
                fragment.passengerCheckBoxChanged(passengerCheckBox.isChecked());
            }
        };

        @Override
        public void onClick(View v) {
            //todo: go to passenger profile.
        }
    }
}
