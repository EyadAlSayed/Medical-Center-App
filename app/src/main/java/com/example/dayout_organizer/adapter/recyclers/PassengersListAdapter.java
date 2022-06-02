package com.example.dayout_organizer.adapter.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.helpers.view.ImageViewer;
import com.example.dayout_organizer.helpers.view.NoteMessage;
import com.example.dayout_organizer.models.PassengerData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.api.ApiClient.BASE_URL;

public class PassengersListAdapter extends RecyclerView.Adapter<PassengersListAdapter.ViewHolder> {

    List<PassengerData> passengers;
    Context context;
    private boolean isUpcoming;

    public PassengersListAdapter(List<PassengerData> passengers, Context context, boolean isUpcoming) {
        this.passengers = passengers;
        this.context = context;
        this.isUpcoming = isUpcoming;
    }

    public void refreshList(List<PassengerData> passengers) {
        this.passengers = passengers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PassengersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_passenger_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassengersListAdapter.ViewHolder holder, int position) {
        downloadUserImage(passengers.get(position).photo, holder.passengerPhoto);
        holder.passengerName.setText(passengers.get(position).name);

        if (isUpcoming) {
            holder.bookingFor.setText(String.valueOf(passengers.get(position).booking_for));
            if (passengers.get(position).confirmed) {
                holder.confirmButton.setVisibility(View.GONE);
                holder.confirmed.setVisibility(View.VISIBLE);
            }
        } else{
            holder.bookingFor.setVisibility(View.GONE);
            holder.confirmButton.setVisibility(View.GONE);
            holder.bookingForTV.setVisibility(View.GONE);
        }
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

        @BindView(R.id.passenger_item_photo)
        ImageView passengerPhoto;

        @BindView(R.id.passenger_item_name)
        TextView passengerName;

        @BindView(R.id.passenger_item_booking_for)
        TextView bookingFor;

        @BindView(R.id.passneger_item_confirmed)
        TextView confirmed;

        @BindView(R.id.passenger_item_confirm_button)
        Button confirmButton;

        @BindView(R.id.passenger_item_booking_for_textView)
        TextView bookingForTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            confirmButton.setOnClickListener(onConfirmClicked);
        }

        @Override
        public void onClick(View v) {
            //TODO: Go to passenger's profile - Caesar.
        }

        private final View.OnClickListener onConfirmClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassengerData data = passengers.get(getAdapterPosition());
                // TODO: Confirm booking - Caesar.
                confirmButton.setVisibility(View.GONE);
                confirmed.setVisibility(View.VISIBLE);
                NoteMessage.message(context, "Confirmed booking for " + data.name);
            }
        };
    }
}
